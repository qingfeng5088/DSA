package dynamicProgramming;

import java.util.*;

/**
 * 最长递增子序列长度
 * Longest increasing subsequence length
 */
public class LISQL {

    static int max = 0;

    public static int getLISQL(int[] a, int n) {
        if (a.length == 0) return 0;

        List<Stack<Integer>> stackList = new ArrayList<>();
        Stack<Integer> checkStack = new Stack<>();
        checkStack.push(a[0]);
        stackList.add(checkStack);

        for (int i = 1; i < a.length; i++) {
            List<Stack<Integer>> sameEndList = new ArrayList<>();
            for (Stack<Integer> stack : stackList) {
                // 当前元素比当前子串的最大元素大时
                if (a[i] > stack.peek()) {
                    sameEndList.add(stack);
                }
            }

            // 只向最长的子串中添加当前元素
            if (sameEndList.size() > 0) {
                getLargest(sameEndList).add(a[i]);
            } else {
                //已知列表中没有比当前子串最大元素更小的子串时
                List<Stack<Integer>> newStackList = new ArrayList<>();
                // 循环所有已知的列表，按顺序把小于当前值的元素放入这个堆中，然后比较并取出最长子串堆放入原列表
                for (Stack<Integer> stack : stackList) {
                    Stack<Integer> newStack = new Stack<>();
                    for (Integer v : stack) {
                        if (v < a[i]) {
                            newStack.add(v);
                        }
                    }
                    newStack.add(a[i]);
                    newStackList.add(newStack);
                }

                stackList.add(getLargest(newStackList));
            }
        }

        Stack<Integer> retStack = getLargest(stackList);
        System.out.println(retStack);

        return retStack.size();
    }

    private static Stack<Integer> getLargest(List<Stack<Integer>> stackList) {
        return stackList.stream().max((x, y) -> {
            if (x.size() == y.size()) {
                if (y.get(0) == x.get(0)) {
                    return y.get(y.size() - 1) - x.get(x.size() - 1);
                } else {
                    return y.get(0) - x.get(0);
                }
            }
            return x.size() - y.size();
        }).orElse(null);
    }


    static Map<Integer, Integer> retMap = new HashMap<>();

    public static int recursionCount4(int[] arrays, int index) {
        if (index == 0) {
            return 1;
        }
        int max = 0;
        // 此问题的解，递归的核心就是在之前的序列中找到最大递增子序列加 1
        // 所以需要遍历此此之前的全部数据项
        for (int i = 0; i < index; i++) {
            // 递归求解每项的最递增序列
            int value = retMap.containsKey(index) ? retMap.get(index) : recursionCount4(arrays, i);
            if (arrays[i] < arrays[index]) {
                if (value > max) {
                    max = value;
                }
            }
        }
        int ret = max + 1;
        retMap.put(index, max);
        return ret;
    }

    public static void countDynamic(int[] arrays) {
        int length = arrays.length;
        int[] status = new int[length];
        status[0] = 1;
        int commMax = 0;
        for (int i = 1; i < length; i++) {
            int max = 0;
            for (int j = 0; j < i; j++) {
                if (arrays[j] < arrays[i]) {
                    if (status[j] > max) {
                        max = status[j];
                    }
                }
            }
            status[i] = max + 1;
            if (status[i] > commMax) {
                commMax = status[i];
            }
        }
        System.out.println(" 最大递增序列为 ： " + commMax);
        int maxComp = commMax;
        System.out.println(" 递增 :" + Arrays.toString(status));
        for (int i = length - 1; i >= 0; i--) {
            if (status[i] == maxComp) {
                System.out.print("-->" + arrays[i]);
                maxComp = maxComp - 1;
            }
        }
    }

    public static void main(String[] args) {
        // int[] a = {2, 9, 3, 6, 5, 1, 7, 12};
        int[] a = {16, 4, 47, 20, 42, 14, 17, 1, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 5, 6, 7, 8, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 15, 148};

        System.out.println("--------数组a的最长递增子序列的长度是:" + getLISQL(a, a.length));

        System.out.println("--------------------打印完毕----------------");
        countDynamic(a);
        // System.out.println(recursionCount4(a, a.length - 1));
    }
}
