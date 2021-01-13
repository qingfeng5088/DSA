package exercise.LinkedList.stackAndQueque;

import java.util.HashMap;
import java.util.Map;

public class Exercise04 {
    Map<Integer,Integer> map = new HashMap<>();
    public int climbStairs(int n) {
        if (map.containsKey(n)) return map.get(n);

        if (n == 1) return 1;
        if (n == 2) return 2;
        if (n == 3) return 3;

        int ret = climbStairs(n - 1) + climbStairs(n - 2);
        map.put(n,ret);
        return  ret;
    }

    public static void main(String[] args) {
        Exercise04 ex = new Exercise04();
        System.out.println(ex.climbStairs(40));
    }
}
