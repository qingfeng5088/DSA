package BinaryTree;

import utils.ArrayUtils;

import java.util.Arrays;

public class Heap<T extends Comparable<T>> {
    private int count;// 堆中已经存储的数据个数
    private T[] a; // 数组
    private int n;// 堆可以存储的最大数据个数
    private int modifyCount; //堆排序交换的次数

    public Heap(int capacity) {
        this.n = capacity;
        a = (T[]) new Comparable[capacity];
        count = 0;
    }

    public void insert(T data) {
        if (count == n) return;//堆满了
        a[count] = data;
        count++;


        int fatherIndex = (count - 1) / 2;
        int i = count - 1;
        while (fatherIndex >= 0 && data.compareTo(a[fatherIndex]) > 0) {
            ArrayUtils.swap(a, i, fatherIndex);

            if (fatherIndex == 0) break;
            i = fatherIndex;
            fatherIndex = (fatherIndex - 1) / 2;
        }
    }

    public T removeMax() {
        if (count == 0) return null;
        T ret = a[0];
        a[0] = a[count - 1];
        count--;

        heapify(count);
        return ret;
    }

    private void heapify(int n) {
        int i = 0;
        int left = 1;
        int right = 2;
        while (right < n) {
            int max = a[left].compareTo(a[right]) < 0 ? right : left;
            if (a[i].compareTo(a[max]) >= 0) break;
            ArrayUtils.swap(a, i, max);
            i = max;
            left = 2 * i + 1;
            right = left + 1;

        }
    }


    public String toString() {
        return Arrays.toString(Arrays.copyOf(a, count));
    }


    public static void main(String[] args) {
        Heap<Integer> heap = new Heap<Integer>(100);

        //  Integer a[] = {8, 3, 5, 2, 1, 0};

        heap.insert(8);
        heap.insert(3);
        heap.insert(4);
        heap.insert(2);
        heap.insert(1);
        heap.insert(5);
        heap.insert(13);
        heap.insert(12);
        heap.insert(16);
        heap.insert(9);
        heap.insert(7);
        heap.insert(21);
        heap.insert(6);

        System.out.println("原始堆:");
        System.out.println(heap);
        int count = heap.count;
        for (int i = 0; i < count; i++) {
            System.out.println("取堆中最大元素:");
            int r = heap.removeMax();
            System.out.println(r);
            System.out.println(heap);
        }
    }
}
