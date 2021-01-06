package utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

public class Utils {
    private static Random random = new Random();
    //  public static String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static String base = "abcdefghijklmnopqrstuvwxyz";

    public static int getRandomInt(int bound) {
        return random.nextInt(bound);
    }

    public static void countTime(Runnable r) {
        Instant start = Instant.now();
        r.run();
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis() + " ms");
    }

    //length表示生成字符串的长度
    public static String getRandomString(long length) {
        StringBuffer sb = new StringBuffer();
        if (length <= 0) return sb.toString();
        Random random = new Random();
        for (long i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    public static int strHash(String str) {
        int sum = 0;
        for (char c : str.toCharArray()) {
            sum += base.indexOf(c) + 1;
        }

        return sum;
    }

    public static void main(String[] args) {
        System.out.println(Utils.getRandomString(10));
    }
}
