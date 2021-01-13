package exercise.LinkedList;

import java.util.HashSet;
import java.util.Set;

/**
 * 给你一个未排序的整数数组，请你找出其中没有出现的最小的正整数。
 * 示例 1:
 * <p>
 * 输入: [1,2,0]
 * 输出: 3
 * 示例 2:
 * <p>
 * 输入: [3,4,-1,1]
 * 输出: 2
 * 示例 3:
 * <p>
 * 输入: [7,8,9,11,12]
 * 输出: 1
 *  
 * <p>
 * 提示：
 * <p>
 * 你的算法的时间复杂度应为O(n)，并且只能使用常数级别的额外空间。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/first-missing-positive
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Exercise03 {
    public int firstMissingPositive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (num > 0) set.add(num);
        }

        if (!set.contains(1)) return 1;

        for (int i = 1; i < set.size(); i++) {
            if (!set.contains(i + 1)) {
                return i + 1;
            }
        }

        return set.size() + 1;
    }

    public int firstMissingPositive2(int[] nums) {
        int i = 0;
        for (int num : nums) {
            if (num > 0) nums[i++] = num;
        }

        for (int j = 0; j < i; j++) {
            if ((Math.abs(nums[j]) - 1) >= i || (Math.abs(nums[j]) - 1) < 0) continue;
            nums[Math.abs(nums[j]) - 1] = -Math.abs(nums[Math.abs(nums[j]) - 1]);
        }

        for (int j = 0; j < i; j++) {
            if (nums[j] > 0) return j + 1;
        }

        return i + 1;
    }

    public static void main(String[] args) {
        Exercise03 ex = new Exercise03();
        int[] nums = {7,8,9,11,12};

        System.out.println(ex.firstMissingPositive2(nums));


    }
}
