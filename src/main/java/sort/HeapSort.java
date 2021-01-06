package sort;

import utils.ArrayUtils;

import java.util.Arrays;

public class HeapSort {
    public static <T extends Comparable<T>> void sort(T[] a) {
        buildHeap(a);

        int k = a.length - 1;

        while (k > 0) {
            ArrayUtils.swap(a, 0, k);
            heapify(a, k, 0);
            k--;
        }
    }

    public static <T extends Comparable<T>> void buildHeap(T[] a) {
        for (int i = (a.length - 1) / 2; i >= 0; i--) {
            heapify(a, a.length, i);

            if (i == 0) break;
        }
    }

    private static <T extends Comparable<T>> void heapify(T[] a, int n, int i) {
        while (true) {
            int max = i;
            int left = 2 * i + 1;
            int right = left + 1;

            if (left < n && a[i].compareTo(a[left]) < 0) max = left;
            if (right < n && a[max].compareTo(a[right]) < 0) max = right;

            if (max == i) break;//本身i就是最大值的情况
            ArrayUtils.swap(a, i, max);
            i = max;
        }
    }

    public static void main(String[] args) {
        Integer[] a = {21, 13, 16, 8, 12, 7, 6, 4, 1, 9, 78, 3, 5, 2};
        HeapSort.sort(a);
        System.out.println(Arrays.toString(a));
        System.out.println("----------交换数据次数:"+ArrayUtils.ModifyCount);


        ArrayUtils.ModifyCount = 0;
        String[] b = {"aa", "cc", "bb", "ee", "dd1", "hh", "ff"};
        HeapSort.sort(b);
        System.out.println(Arrays.toString(b));
        System.out.println("----------交换数据次数:"+ArrayUtils.ModifyCount);

    }
}
