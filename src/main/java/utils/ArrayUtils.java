package utils;

public class ArrayUtils {
    public static long ModifyCount = 0;
    public static <T> void swap(T[] x, int a, int b) {
        T t = x[a];
        x[a] = x[b];
        x[b] = t;
        ModifyCount++;
    }
}
