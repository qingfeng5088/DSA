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
}
