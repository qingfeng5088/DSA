package SearchEngines;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler {
    private final String linksFile = "D:\\SearchEngines\\links.bin";
    private final String docIdFile = "D:\\SearchEngines\\doc_Id.bin";
    private final String docFileBase = "D:\\SearchEngines\\doc_raw";
    private String docFile = docFileBase + "0.bin";
    private final String bloomFile = "D:\\SearchEngines\\bloom_filter.bin";

    private static String[] seeds;
    private AtomicInteger docId = new AtomicInteger(0); // 网页编号
    private AtomicInteger docFileId = new AtomicInteger(0); // 网页文件编号
    private AtomicInteger termId = new AtomicInteger(0);//单词编号
    private final String regex = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
    private FileWriter linksOut = null;
    private FileWriter docOut = null;

    // 创建布隆过滤器，设置存储的数据类型，预期数据量，误判率 (必须大于0，小于1)
    private final int insertions = 10000000;
    private final double fpp = 0.008;
    private BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), insertions, fpp);

    private final Timer timer = new Timer();

    private final String[] exceptHttpfile = {".jpg", ".JPG", ".jpeg", ".css", ".ico", ".js", ".gif", ".png", ".swf"};

    private void getPagecontents(URL url) {
        try {
            URLConnection connection = url.openConnection();
            StringBuilder sb = new StringBuilder();

            try (var in = new Scanner(connection.getInputStream())) {
                while (in.hasNextLine()) {
                    sb.append(in.nextLine());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            String doc = sb.toString().replaceAll("[\\t\\n\\r]", "");

            if (doc.isEmpty() || doc.isBlank()) return;

            docId.incrementAndGet();

            // 记录网页链接及其编号的对应文件： doc_id.bin
            writeTodocIdFile(docId + "\t" + url + "\r\n");

            //获取该网页的其他URL地址
            getAndWriteUrls(doc);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeTodocIdFile(String str) {
        try (var out = new FileWriter(docIdFile, StandardCharsets.UTF_8, true)) {
            out.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void getAndWriteUrls(String doc) {
        //编译正则字符串
        Pattern p = Pattern.compile(regex);
        //利用正则去匹配
        Matcher matcher = p.matcher(doc);
        //如果找到了我们正则里要的东西
        while (matcher.find()) {
            //保存到sb中，"\r\n"表示找到一个放一行，就是换行
            String httpstr = matcher.group();
            String finalHttpstr = httpstr;
            if (Arrays.stream(exceptHttpfile).anyMatch(x -> finalHttpstr.contains(x))) continue;
            if (httpstr.contains("?")) {
                httpstr = httpstr.substring(0, httpstr.indexOf("?"));
            }

            //布隆过滤器判断是否已经存在这个网站
            if (!bloomFilter.mightContain(httpstr)) {
                System.out.println("---向links.bin写入网址:" + httpstr);
                writeToFile(linksOut, httpstr + "\r\n");

                setBloomFilter(httpstr);
            }
        }

        // 整个网站内容写入文件
        System.out.println("---向doc_raw写入网站内容,编号:" + docId);
        writeDocToFile(docOut, docId + "\t" + doc + "\r\n");
    }

    private void setBloomFilter(String httpstr) {
        bloomFilter.put(httpstr);
    }

    private void writeDocToFile(FileWriter out, String str) {
        try {
            long size = Files.size(Path.of(docFile));
            //当文件doc_raw.bin的大小大于100M时创建下一个编号的文件
            if (size > 100 * 1024 * 1024) {
                docFileId.incrementAndGet();
                docFile = docFileBase + docFileId + ".bin";
                try (var fw = new FileWriter(docFile, StandardCharsets.UTF_8, true)) {
                    out = fw;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        writeToFile(out, str);
    }

    private void writeToFile(FileWriter out, String str) {
        try {
            out.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void autoSaveBloomFilter() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("----------开始写入bloomFilter.bin-----");
                writeBloomFilterToFile();
                System.out.println("----------写入结束-----");

            }
        }, 1000 * 60 * 30, 1000 * 60 * 30);
    }

    private void writeBloomFilterToFile() {
        try (var fos = new FileOutputStream(bloomFile);
             var out = new BufferedOutputStream(fos);) {
            bloomFilter.writeTo(out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getBloomFilterFromFile() {
        if (!Files.exists(Path.of(bloomFile))) {
            return;
        }

        try (var fileInputStream = new FileInputStream(bloomFile);
             var in = new BufferedInputStream(fileInputStream)) {
            bloomFilter = BloomFilter.readFrom(in, Funnels.stringFunnel(Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doLinks() {
        try {
            var lines = Files.readAllLines(Path.of(linksFile), StandardCharsets.UTF_8);
            var docLines = Files.readAllLines(Path.of(docIdFile), StandardCharsets.UTF_8);

            lines.stream().filter(x -> !docLines.stream().anyMatch(y -> y.indexOf(x) > 0)).forEach(x -> {
                try {
                    getPagecontents(new URL(x));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        seeds = new String[]{"https://mil.news.sina.com.cn/china/2021-01-09/doc-iiznezxt1432207.shtml", "https://www.sina.com.cn/", "https://wenku.baidu.com/", "https://www.sohu.com/", "https://www.ifeng.com/"};
        Crawler crawler = new Crawler();

        //先从文件中获取布隆过滤器
        crawler.getBloomFilterFromFile();

        try (var linksOut = new FileWriter(crawler.linksFile, StandardCharsets.UTF_8, true);
             var docOut = new FileWriter(crawler.docFile, StandardCharsets.UTF_8, true)) {
            crawler.linksOut = linksOut;
            crawler.docOut = docOut;

            for (String seed : seeds) {
                crawler.getPagecontents(new URL(seed));
            }

            crawler.doLinks();

            crawler.autoSaveBloomFilter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
