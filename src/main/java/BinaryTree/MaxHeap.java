package BinaryTree;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 大顶堆
 * <p>
 * 借助优先队列（小顶堆）实现大顶堆
 * 把比较器逆序就可以了
 *
 * @param <E>
 */
public class MaxHeap<E extends Comparable<E>> extends PriorityQueue<E> {
    public MaxHeap() {
        super(Comparator.reverseOrder());
    }

    public MaxHeap(int initialCapacity) {
        super(initialCapacity, Comparator.reverseOrder());
    }

    public static void main(String[] args) {
        MaxHeap<String> maxHeap = new MaxHeap<String>();
        // PriorityQueue<String> maxHeap = new PriorityQueue<String>();
        maxHeap.offer("qyt07");
        maxHeap.offer("qyt02");
        maxHeap.offer("qyt05");
        maxHeap.offer("qyt06");
        maxHeap.offer("qyt12");
        maxHeap.offer("qyt15");
        maxHeap.offer("qyt23");
        maxHeap.offer("qyt01");

        System.out.println("----------------输出整个大顶堆----------");
        while (!maxHeap.isEmpty()) {
            System.out.println(maxHeap.poll() + " ");
        }
    }
}
