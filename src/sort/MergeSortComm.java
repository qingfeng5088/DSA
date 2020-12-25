package sort;

import utils.Utils;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 归并排序 通用类
 */
public class MergeSortComm {

    public static <E> void mergeSort(E[] a, int p, int r, Comparator<E> cpr) {
        if (p >= r) {
            return;
        }

        int q = (p + r) / 2;
        mergeSort(a, p, q, cpr);
        mergeSort(a, q + 1, r, cpr);
        merge(a, p, q, q + 1, r, cpr);

    }

    private static <E> void merge(E[] a, int p, int q, int i, int r, Comparator<E> cpr) {
        int start = p;
        int maxlen = (q - p + 1) + (r - i + 1);

        E t[] = (E[]) new Object[maxlen];
        int k = 0;
        while (p <= q && i <= r) {
            if (cpr.compare(a[p], a[i]) > 0) {
                t[k++] = a[i];
                i++;
            } else {
                t[k++] = a[p];
                p++;
            }
        }

        if (p <= q) {
            for (int x = p; x <= q; x++) {
                t[k++] = a[x];
            }
        } else {
            for (int x = i; x <= r; x++) {
                t[k++] = a[x];
            }
        }

        for (int i1 = 0; i1 < t.length; i1++) {
            a[start++] = t[i1];
        }
    }

    public static void main(String[] args) {
        //  int[] a = {11, 8, 3, 9, 7, 1, 2, 5, 4, 15, -6, 58, 23};

        int count = 1000;
        Integer[] a = new Integer[count];

        for (int i = 0; i < count; i++) {
            a[i] = Utils.getRandomInt(count);
        }

       // mergeSort(a, 0, a.length - 1, (x, y) -> x - y);
        mergeSort(a, 0, a.length - 1, Integer::compareTo);

        System.out.println(Arrays.toString(a));
    }
}
