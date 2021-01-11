package SearchEngines;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.io.*;
import java.nio.charset.Charset;

public class Test {

    public static void main(String[] args) {
        try (var fos = new FileOutputStream("D:\\bloom_filter.bin");
             var out = new BufferedOutputStream(fos)) {
            int insertions = 10000000;
            double fpp = 0.008;
            BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), insertions, fpp);

            bloomFilter.put("http://www.sina.com.cn/mid/pic/index.d.html");
            bloomFilter.put("http://keyword.sina.com.cn/searchword.php");
            bloomFilter.put("https://sina.cn/");

            bloomFilter.writeTo(out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
