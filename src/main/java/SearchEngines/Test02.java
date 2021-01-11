package SearchEngines;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;

public class Test02 {
    public static void main(String[] args) {
        try (var fileInputStream = new FileInputStream("D:\\bloom_filter.bin");
             var in = new BufferedInputStream(fileInputStream)) {
            BloomFilter<String> bloomFilter = BloomFilter.readFrom(in, Funnels.stringFunnel(Charset.defaultCharset()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
