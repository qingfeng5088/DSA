package sort;

import utils.Utils;

import java.util.Arrays;
import java.util.Comparator;

public class RadixSort {

    public static void radixSort(String[] a) {
        if (a.length == 0) {
            return;
        }

        String str = a[0];
        for (int i = 1; i <= str.length(); i++) {
            int finalI = i;

            Comparator<String> cpr = Comparator.comparing(x -> x.substring(x.length() - finalI, x.length() - finalI + 1));
            MergeSortComm.mergeSort(a, 0, a.length - 1, cpr);
        }
    }

    public static void main(String[] args) {
        String[] a = new String[100000];

        for (int i = 0; i < a.length; i++) {
            a[i] = Utils.getRandomString(5);
        }

        radixSort(a);
        System.out.println(Arrays.toString(a));
    }
}
