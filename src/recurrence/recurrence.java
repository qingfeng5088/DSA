package recurrence;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class recurrence {

    static Map<Integer, Long> map = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("---------------测试1---------------");
        Instant start = Instant.now();
        System.out.println(f2(38));
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis() + " ms");

        System.out.println("---------------测试2---------------");
        start = Instant.now();
        System.out.println(f(5146));
        end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis() + " ms");

        System.out.println("---------------测试3---------------");
        start = Instant.now();
        System.out.println(f3(5146));
        end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis() + " ms");


    }

    static long f3(int n) {
        if (n == 1) return 1;
        if (n == 2) return 2;
        long ret = 0;
        long pre = 2;
        long prepre = 1;
        for (int i = 3; i <= n; ++i) {
            ret = pre + prepre;
            prepre = pre;
            pre = ret;
        }
        return ret;
    }


    static int f2(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }

        int v1 = f2(n - 1);
        int v2 = f2(n - 2);

        return v1 + v2;
    }


    static long f(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }

        if (map.containsKey(n)) {
            return map.get(n);
        }

        long v1 = f(n - 1);
        long v2 = f(n - 2);

        map.put(n, v1 + v2);

        return v1 + v2;
    }

}
