package sort;

import java.util.Arrays;

public class BinarySearch {
    /**
     * 二分查找，递归实现
     *
     * @param a
     * @param low
     * @param height
     * @param v
     * @return
     */
    public static int indexOf(int[] a, int low, int height, int v) {
        int ret = -1;
        int mid = low + ((height - low) >> 1);

        if (low > height) {
            return ret;
        }

        if (a[mid] == v) {
            return mid;
        }

        if (a[mid] < v) {
            low = mid + 1;
        } else {
            height = mid - 1;
        }

        return indexOf(a, low, height, v);
    }

    /**
     * 变体一：查找第一个值等于给定值的元素
     *
     * @param a
     * @param low
     * @param height
     * @param v
     * @return
     */
    public static int firstIndexOf(int[] a, int low, int height, int v) {
        int mid = low + ((height - low) >> 1);

        if (low > height) {
            return -1;
        }

        if (a[mid] < v) {
            low = mid + 1;
        } else if (a[mid] > v) {
            height = mid - 1;
        } else {
            if (mid == 0 || a[mid - 1] != v) {
                return mid;
            }
            height = mid - 1;
        }

        return firstIndexOf(a, low, height, v);
    }

    /**
     * 变体二：查找最后一个值等于给定值的元素
     *
     * @param a
     * @param low
     * @param height
     * @param v
     * @return
     */
    public static int lastIndexOf(int[] a, int low, int height, int v) {
        int mid = low + ((height - low) >> 1);

        if (low > height) {
            return -1;
        }

        if (a[mid] < v) {
            low = mid + 1;
        } else if (a[mid] > v) {
            height = mid - 1;
        } else {
            if (mid == a.length - 1 || a[mid + 1] != v) {
                return mid;
            }
            if (mid + 1 < height && a[mid + 1] == v) {
                low = mid + 1;
            }
        }

        return lastIndexOf(a, low, height, v);
    }

    /**
     * 变体三：查找第一个大于等于给定值的元素
     *
     * @param a
     * @param low
     * @param height
     * @param v
     * @return
     */
    public static int firstLargerIndexOf(int[] a, int low, int height, int v) {
        int mid = low + ((height - low) >> 1);

        if (low > height) {
            return -1;
        }

        if (a[mid] >= v) {
            if (mid == 0 || a[mid - 1] < v) {
                return mid;
            }
            height = mid - 1;
        } else {
            low = mid + 1;
        }

        return firstLargerIndexOf(a, low, height, v);
    }

    /**
     * 变体四：查找最后一个小于等于给定值的元素
     *
     * @param a
     * @param low
     * @param height
     * @param v
     * @return
     */
    public static int lastLessIndexOf(int[] a, int low, int height, int v) {
        int mid = low + ((height - low) >> 1);

        if (low > height) {
            return -1;
        }

        if (a[mid] <= v) {
            if (mid == a.length - 1 || a[mid + 1] > v) {
                return mid;
            }
            low = mid + 1;
        } else {
            height = mid - 1;
        }

        return lastLessIndexOf(a, low, height, v);
    }


    public static void main(String[] args) {
        int[] a = {1, 3, 4, 5, 6, 6, 8, 8, 8, 8, 11, 11, 18};

        int ret = -1;
        System.out.println(Arrays.toString(a));
        System.out.println("值=5的元素下标:" + (ret = indexOf(a, 0, a.length - 1, 5)) + ", 元素值:a[" + ret + "]=" + a[ret]);
        System.out.println("第一个=8的元素下标:" + (ret = firstIndexOf(a, 0, a.length - 1, 8)) + ", 元素值:a[" + ret + "]=" + a[ret]);
        System.out.println("最后一个=8的元素下标:" + (ret = lastIndexOf(a, 0, a.length - 1, 8)) + ", 元素值:a[" + ret + "]=" + a[ret]);
        System.out.println("第一个大于>=9的元素下标:" + (ret = firstLargerIndexOf(a, 0, a.length - 1, 10)) + ", 元素值:a[" + ret + "]=" + a[ret]);
        System.out.println("最后一个<=7的元素下标:" + (ret = lastLessIndexOf(a, 0, a.length - 1, 7)) + ", 元素值:a[" + ret + "]=" + a[ret]);
    }
}
