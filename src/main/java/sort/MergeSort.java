package sort;

import utils.Utils;

import java.util.Arrays;

public class MergeSort {

    public static void mergeSort(int[] a, int p, int r) {
        if (p >= r) {
            return;
        }

        int q = (p + r) / 2;
        mergeSort(a, p, q);
        mergeSort(a, q + 1, r);
        merge(a, p, q, q + 1, r);

    }

    private static void merge(int[] a, int p, int q, int i, int r) {
        int start = p;
        int maxlen = (q - p + 1) + (r - i + 1);

        int t[] = new int[maxlen];
        int k = 0;
        while (p <= q && i <= r) {
            if (a[p] > a[i]) {
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

        int count = 100000;
        int[] a = new int[count];

        for (int i = 0; i < count; i++) {
            a[i] = Utils.getRandomInt(count);
        }


        mergeSort(a, 0, a.length - 1);

        System.out.println(Arrays.toString(a));
    }
}
