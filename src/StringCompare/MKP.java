package StringCompare;

import java.util.Arrays;

public class MKP {
    public static int kmp(char[] a, char[] b) {
        int n = a.length;
        int m = b.length;
        int[] next = getNexts(b);
        int j = 0;
        for (int i = 0; i < n; ++i) {
            while (j > 0 && a[i] != b[j]) { // 一直找到a[i]和b[j]
                j = next[j - 1] + 1;
            }
            if (a[i] == b[j]) {
                ++j;
            }
            if (j == m) { // 找到匹配模式串的了
                return i - m + 1;
            }
        }
        return -1;
    }

    public static int qiukmp(char[] a, char[] b) {
        int n = a.length;
        int m = b.length;
        char first = b[0];
        int[] next = getNexts(b);
        int j = 0;
        for (int i = 0; i < n; ++i) {
            while (j > 0 && a[i] != b[j]) { // 一直找到a[i]和b[j]
                j = next[j - 1] + 1;
            }
            if (a[i] == b[j]) {
                ++j;
            }
            if (j == 0) {
                if (a[i] != first) {
                    while (++i <= (n-m +1) && a[i] != first) ;
                    i--;
                    continue;
                }
            }

            if (j == m) { // 找到匹配模式串的了
                return i - m + 1;
            }
        }
        return -1;
    }

    private static int[] getNexts(char[] b) {
        int m = b.length;
        int[] next = new int[m];
        next[0] = -1;
        int k = -1;
        for (int i = 1; i < m; ++i) {
            while (k != -1 && b[k + 1] != b[i]) {
                k = next[k];
            }
            if (b[k + 1] == b[i]) {
                ++k;
            }
            next[i] = k;
        }
        return next;
    }

    public static void main(String[] args) {
        char[] a = "acadabababhmnababacdxyz".toCharArray();
        char[] b = "ababacd".toCharArray();
        System.out.println(Arrays.toString(getNexts(b)));
        System.out.println("--------------mkp算法计算结果---------");
        System.out.println(kmp(a, b));
    }
}
