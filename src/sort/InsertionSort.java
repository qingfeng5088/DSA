package sort;

import utils.Utils;

import java.util.Arrays;

public class InsertionSort {

    public static void insertionSort2() {
        int n = a.length;
        if (n <= 1) return;
        for (int i = 1; i < n; ++i) {
            int value = a[i];
            int j = i - 1;
// 查找插入的位置
            for (; j >= 0; --j) {
                if (a[j] > value) {
                    a[j + 1] = a[j]; // 数据移动
                } else {
                    break;
                }
            }
            a[j + 1] = value; // 插入数据
        }
    }

    public static void insertionSort() {
        int len = a.length;
        if (len <= 1) {
            return;
        }

        int i = 0;
        int j = i + 1;

        int c;
        for (; j < len; j++) {
            c = a[j];
            int mid = j / 2;

            for (int m = 0; m < 10; m++) {
                i = c >= a[mid] ? mid : 0;

                if (i == 0) {
                    mid = mid / 2;
                } else {
                  //  mid = mid + (mid-1) / 2;
                }
            }


            for (; i < j; i++) {
                if (c < a[i]) {
                    break;
                }
            }

            int k = j;
            for (; i < k; k--) {
                a[k] = a[k - 1];
            }
            a[i] = c;
        }
    }

    static int count = 100000;
    static int[] a = new int[count];

    public static void main(String[] args) {
        for (int i = 0; i < count; i++) {
            a[i] = Utils.getRandomInt(count);
        }


        Utils.countTime(InsertionSort::insertionSort2);

        System.out.println(Arrays.toString(a));
    }
}
