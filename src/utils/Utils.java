package utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

public class Utils {
    private static Random random = new Random();

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
    public static String getRandomString(int length) {
        StringBuffer sb = new StringBuffer();
        if(length <= 0 ) return sb.toString();
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
