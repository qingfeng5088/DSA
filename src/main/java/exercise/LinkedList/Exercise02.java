package exercise.LinkedList;

import java.util.*;

/**
 * 给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。
 *
 * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/majority-element
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Exercise02 {

    public int majorityElement(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.merge(num,1,Integer::sum);
        }

        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet()); //转换为list
        return list.stream().max(Comparator.comparingInt(Map.Entry::getValue)).orElse(null).getKey();
    }

    public int majorityElement2(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length/2];
    }

    public static void main(String[] args) {
        int[] nums ={2,2,1,1,1,2,2};
        Exercise02 ex = new Exercise02();
        System.out.println(ex.majorityElement2(nums));
    }
}
