package sort;

import utils.ArrayUtils;
import utils.Utils;

public class PerformanceTest {
    static int count = 1_000_0000;
    static Integer[] a = new Integer[count];
    static Integer[] b = new Integer[count];

    public static void main(String[] args) {
        for (int i = 0; i < a.length; i++) {
            int v = Utils.getRandomInt(count * 10);
            a[i] = v;
            b[i] = v;
        }


        System.out.println("-------------------------------------");
        ArrayUtils.ModifyCount = 0;
        System.out.println("堆排序" + count + "条数据的耗时:");
        Utils.countTime(PerformanceTest::HeapSort);
        System.out.println("数据交换次数:" + ArrayUtils.ModifyCount);

        System.out.println("-------------------------------------");
        ArrayUtils.ModifyCount = 0;
        System.out.println("快速排序" + count + "条数据的耗时:");
        Utils.countTime(PerformanceTest::QuickSort);
        System.out.println("数据交换次数:" + ArrayUtils.ModifyCount);

    }

    static  void QuickSort(){
        QuickSort.sort(a);
    }

    static  void HeapSort(){
        HeapSort.sort(b);
    }
}
