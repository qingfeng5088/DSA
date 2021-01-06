package BinaryTree.app;

import BinaryTree.MaxHeap;
import utils.Utils;

import java.util.PriorityQueue;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 动态求 x% 响应时间，如 50% 就是求 中位数
 * 99% 就是求 99% 相应时间
 */
public class Take100XValue {
    static int x = 80; //%x的值

    //大顶堆
    private static MaxHeap<Integer> maxHeap = new MaxHeap<Integer>();

    //小顶堆
    private static PriorityQueue<Integer> minHeap = new PriorityQueue<>();

    private static Vector<Integer> list = new Vector<>();
    private static LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        // 初始化循环数组
        for (int i = 0; i < 100; i++) {
            list.add(Utils.getRandomInt(100));
        }
        list.sort(Integer::compareTo);
        System.out.println(list);
        for (int i = 0; i < 100; i++) {
            if (i < x) {
                // 构建大顶堆
                maxHeap.offer(list.get(i));
            } else {
                // 构建小顶堆
                minHeap.offer(list.get(i));
            }
        }

        System.out.println("----------------当前的" + x + "% 的取值为:" + maxHeap.peek());

        Thread produce = new Thread(() -> {
            while (true) {
                try {
                    int n = Utils.getRandomInt(1000);
                    queue.put(n);
                    list.add(n);
                    Thread.sleep(300);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "Produce");
        produce.start();


        Thread consumer = new Thread(() -> {
            while (true) {
                try {
                    int n = queue.take();
                    int maxTop = maxHeap.peek();
                    int minTop = minHeap.peek();

                    if (n <= maxTop) {
                        maxHeap.offer(n);
                    } else if (n >= minTop) {
                        minHeap.offer(n);
                    }

                    // 调整大小堆的大小，使其符合x%的比例
                    int m = list.size() * x / 100;
                    int maxSize = maxHeap.size();
                    int forCount = Math.abs(m - maxSize);
                    boolean b = m < maxSize;
                    for (int i = 0; i < forCount; i++) {
                        if (b) {
                            minHeap.offer(maxHeap.poll());
                        } else {
                            maxHeap.offer(minHeap.poll());
                        }
                    }

                    System.out.println("========================动态调整信息=====================");
                    System.out.println("--------------------数组大小:" + list.size() + "--------");
                    System.out.println("--------------------小堆大小个数和:" + (maxHeap.size() + minHeap.size()));

                    System.out.println("--------------------大堆大小:" + maxHeap.size());
                    System.out.println("--------------------小堆大小:" + minHeap.size());
                    list.sort(Integer::compareTo);
                    System.out.println("--------------------数组信息:" + list);
                    System.out.println("--------------------数组的m:" + m);
                    System.out.println("--------------------当前大堆的" + x + "% 的取值为:" + maxHeap.peek());
                    System.out.println("--------------------数组的x%值:" + list.get(m) + "--" + list.get(m - 1) + "--" + list.get(m + 1));

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "consumer");

        consumer.run();


    }


}
