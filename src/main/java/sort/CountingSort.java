package sort;

import utils.Utils;

import java.util.Arrays;

/**
 * 计数排序
 */
public class CountingSort {

    public static void countingSort(int[] a) {
        int max = getMax(a);
        int[] c = new int[max + 1];//生成0～max（max+1）个数组
        // 给c数组赋值，下标对应的数字表示该数字在整个数组中的个数
        for (int i = 0; i < a.length; i++) {
            c[a[i]]++;
        }

        //对c数组顺序求和
        for (int i = 1; i < c.length; i++) {
            c[i] += c[i - 1];
        }

        //准备一个临时数组，存放排序的数据
        int r[] = new int[a.length];

        // 倒序计算每个元素对应的位置
        for (int i = a.length - 1; i >= 0; i--) {
            r[c[a[i]] - 1] = a[i];
            c[a[i]]--;
        }

        // 将结果拷贝给a数组
        for (int i = 0; i < r.length; i++) {
            a[i] = r[i];
        }
    }

    private static int getMax(int[] a) {
        if (a.length <= 0) {
            return 0;
        }
        int max = a[0];
        for (int i = 1; i < a.length; i++) {
            if (a[i] > max) {
                max = a[i];
            }
        }

        return max;
    }

    public static void main(String[] args) {
        //int[] a = {2, 5, 3, 0, 2, 3, 0, 3};
        int count = 100000;
        int[] a = new int[count];

        for (int i = 0; i < count; i++) {
            a[i] = Utils.getRandomInt(100);
        }


        countingSort(a);

        System.out.println(Arrays.toString(a));
    }
}
