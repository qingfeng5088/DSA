package SearchEngines;

import Trie.ChineseDictionary;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 分析网页的内容
 * <p>
 * 网页爬取下来之后，我们需要对网页进行离线分析。分析阶段主要包括两个步骤，
 * 第一个是抽取网页文本信息
 * 第二个是分词并创建临时索引。
 */
public class Analyze {
    private final String linksFile = "D:\\SearchEngines\\links.bin";
    private final String docIdFile = "D:\\SearchEngines\\doc_id.bin";
    private final String docFileBase = "D:\\SearchEngines\\doc_raw";
    private final String docFile = docFileBase + "0.bin";
    private final String bloomFile = "D:\\SearchEngines\\bloom_filter.bin";
    private final String tmpIndexFile = "D:\\SearchEngines\\tmp_Index.bin";
    private final String indexFile = "D:\\SearchEngines\\index.bin";
    private final String termIdFile = "D:\\SearchEngines\\term_id.bin";

    private List<String> regexs;
    private final String exceptRegex = "<%s\\b[^<]*(?:(?!<\\/%s>)<[^<]*)*<\\/%s>";
    private final String[] exceptKeyWords = {"style", "script", "option", "head", "title", "a", "li", "ul", "noScript", "i", "ins", "li", "iframe", "h2", "h3"};

    private AtomicInteger termId = new AtomicInteger(0);//单词编号
    private FileWriter tmpIndexOut = null;
    private FileWriter tmpIdOut = null;
    private FileWriter indexOut = null;

    private ChineseDictionary dic;

    public Analyze() {
        dic = new ChineseDictionary();
    }

    private void setRegexs() {
        regexs = new ArrayList<>();
        regexs.add("<!--(.*?)-->");
        regexs.add("<img.*?>");
        regexs.add("<input.*?>");

        for (String exceptKeyWord : exceptKeyWords) {
            String regex = String.format(exceptRegex, exceptKeyWord, exceptKeyWord, exceptKeyWord);
            regexs.add(regex);
        }
    }

    private void analyzeWeb() {
        Map<String, Integer> wordMap = new HashMap<>();
        setRegexs();
        try (Scanner scanner = new Scanner(new File(docFile), StandardCharsets.UTF_8)) {
            while (scanner.hasNextLine()) {
                String[] doc = scanner.nextLine().split("\t");
                if (doc.length < 2) continue;
                String docId = doc[0];
                String webContent = doc[1];

                if (webContent.length() > 181000) continue;

                System.out.println("---------处理当前第" + docId + "个文件");

                for (String regex : regexs) {
                    webContent = webContent.replaceAll(regex, "");
                }
                String webChineseContent = regxChinese(webContent);
                Set<String> words = SeparateWords(webChineseContent);

                for (String word : words) {
                    if (wordMap.containsKey(word)) {
                        writeToFile(tmpIndexOut, wordMap.get(word) + "\t" + docId + "\r\n");
                    } else {
                        termId.incrementAndGet();
                        wordMap.put(word, termId.intValue());
                        writeToFile(tmpIndexOut, termId + "\t" + docId + "\r\n");
                    }

                    //保持单词到 term_id.bin
                    writeToFile(tmpIdOut, wordMap.get(word) + "\t" + word + "\r\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<Integer, String> invertedIndexMap = new HashMap<>();

    private void CreatInvertedIndex() {
        try (Scanner scanner = new Scanner(new File(tmpIndexFile), StandardCharsets.UTF_8)) {
            while (scanner.hasNextLine()) {
                String[] ids = scanner.nextLine().split("\t");
                if (ids.length < 2) continue;
                invertedIndexMap.merge(Integer.valueOf(ids[0]), ids[1], (x, y) -> x + "," + y);
            }

            invertedIndexMap.forEach((x, y) -> {
                //保持单词到 index.bin
                writeToFile(indexOut, x + "\t" + y + "\r\n");
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeToFile(FileWriter out, String str) {
        try {
            out.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Set<String> SeparateWords(String webChineseContent) {
        Set<String> words = new HashSet<>();
        for (int i = 0; i < webChineseContent.length(); ) {
            int j = i + 1;
            for (; j <= webChineseContent.length(); j++) {
                if (!dic.dictonary.contains(webChineseContent.substring(i, j))) {
                    boolean isRealEnd = true;
                    for (int k = 1; k <= 15; k++) {
                        if ((j + k) > webChineseContent.length()) break;
                        if (dic.dictonary.contains(webChineseContent.substring(i, j + k))) {
                            isRealEnd = false;
                            break;
                        }
                    }
                    if (isRealEnd) break;
                }
            }
            words.add(webChineseContent.substring(i, j - 1));
            if (j == i + 1) {
                i = j;
            } else {
                i = j;
                i--;
            }
        }

        return words;
    }

    private boolean isCharExist(String webChineseContent, List<List<Character>> dicList, int j, int startIndex) {
        boolean isFind = false;

        for (List<Character> characters : dicList) {
            if (characters.size() < startIndex + 1) continue;
            if (characters.get(startIndex) == webChineseContent.charAt(j)) {
                isFind = true;
                break;
            }

        }
        return isFind;
    }


    public String regxChinese(String source) {
        System.out.println("----------------处理的字符串长度:" + source.length());
        // 将上面要匹配的字符串转换成小写
        source = source.toLowerCase();
        // 匹配的字符串的正则表达式
        String reg_charset = "[\\u4e00-\\u9fa5]*";
        Pattern p = Pattern.compile(reg_charset);
        Matcher m = p.matcher(source);

        StringBuilder sb = new StringBuilder();

        while (m.find()) {
            sb.append(m.group(0));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        Analyze analyze = new Analyze();

        try (var tmpIndexOut = new FileWriter(analyze.tmpIndexFile, StandardCharsets.UTF_8, true);
             var temIdOut = new FileWriter(analyze.termIdFile, StandardCharsets.UTF_8, true);
             var indexOut = new FileWriter(analyze.indexFile, StandardCharsets.UTF_8, true)) {
            analyze.tmpIndexOut = tmpIndexOut;
            analyze.tmpIdOut = temIdOut;
            analyze.indexOut = indexOut;

            analyze.analyzeWeb();
            analyze.CreatInvertedIndex();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
