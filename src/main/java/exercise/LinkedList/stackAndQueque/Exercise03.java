package exercise.LinkedList.stackAndQueque;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * 给你一个整数数组 nums，有一个大小为k的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k个数字。滑动窗口每次只向右移动一位。
 * 返回滑动窗口中的最大值。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/sliding-window-maximum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Exercise03 {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums.length <= k) return new int[]{Arrays.stream(nums).max().orElse(0)};
        if (k == 1) return nums;

        int[] ret = new int[nums.length - k + 1];
        ret[0] = Arrays.stream(Arrays.copyOfRange(nums, 0, k)).max().orElse(0);
        for (int i = k; i < nums.length; i++) {
            if (nums[i - k] <= nums[i] || nums[i - k] < ret[i - k]) {
                ret[i - k + 1] = Math.max(nums[i], ret[i - k]);
                continue;
            }
            ret[i - k + 1] = Arrays.stream(Arrays.copyOfRange(nums, i - k + 1, i + 1)).max().orElse(0);
        }
        return ret;
    }

    public int[] maxSlidingWindow2(int[] nums, int k) {
        int[] ret = new int[nums.length - k + 1];

        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.reverseOrder());

        for (int i = 0; i < k; i++) {
            queue.offer(nums[i]);
        }

        ret[0] = queue.peek();
        queue.remove(nums[0]);

        for (int i = k; i < nums.length; i++) {
            queue.offer(nums[i]);
            ret[i - k + 1] = queue.peek();
            queue.remove(nums[i - k + 1]);
        }
        return ret;
    }

    public int[] maxSlidingWindow3(int[] nums, int k) {
        int n = nums.length;
        PriorityQueue<int[]> pq = new PriorityQueue<>((pair1, pair2) -> pair1[0] != pair2[0] ? pair2[0] - pair1[0] : pair2[1] - pair1[1]);
        for (int i = 0; i < k; ++i) {
            pq.offer(new int[]{nums[i], i});
        }
        int[] ans = new int[n - k + 1];
        ans[0] = pq.peek()[0];
        for (int i = k; i < n; ++i) {
            pq.offer(new int[]{nums[i], i});
            while (pq.peek()[1] <= i - k) {
                pq.poll();
            }
            ans[i - k + 1] = pq.peek()[0];
        }
        return ans;

    }

    public int[] maxSlidingWindow4(int[] nums, int k) {
        int n = nums.length;
        int[] ans = new int[n - k + 1];
        LinkedList<Integer> queue = new LinkedList<>();

        for (int i = 0; i < n; ++i) {
            while (!queue.isEmpty() && nums[queue.peekLast()] <= nums[i]){
                queue.pollLast();
            }

            queue.addLast(i);

            if(queue.peekFirst() <= i - k ){
                queue.pollFirst();
            }

            if (i >= k-1){
                ans[i - k + 1] = nums[queue.peekFirst()];
            }
        }

        return ans;

    }

    public int[] maxSlidingWindow5(int[] nums, int k) {
        if(nums == null || nums.length < 2) return nums;
        // 双向队列 保存当前窗口最大值的数组位置 保证队列中数组位置的数值按从大到小排序
        LinkedList<Integer> queue = new LinkedList();
        // 结果数组
        int[] result = new int[nums.length-k+1];
        // 遍历nums数组
        for(int i = 0;i < nums.length;i++){
            // 保证从大到小 如果前面数小则需要依次弹出，直至满足要求
            while(!queue.isEmpty() && nums[queue.peekLast()] <= nums[i]){
                queue.pollLast();
            }
            // 添加当前值对应的数组下标
            queue.addLast(i);
            // 判断当前队列中队首的值是否有效
            if(queue.peek() <= i-k){
                queue.poll();
            }
            // 当窗口长度为k时 保存当前窗口中最大值
            if(i+1 >= k){
                result[i+1-k] = nums[queue.peek()];
            }
        }
        return result;
    }


    public static void main(String[] args) {
        Exercise03 ex = new Exercise03();
//         int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
        int[] nums = {9, 10, 9, -7, -4, -8, 2, -6};

//        int[] nums = {7, 2, 4};

        int k = 5;
        System.out.println(Arrays.toString(ex.maxSlidingWindow4(nums, k)));
    }
}
