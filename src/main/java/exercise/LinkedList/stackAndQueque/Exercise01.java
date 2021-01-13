package exercise.LinkedList.stackAndQueque;

import java.util.Stack;

/**
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 * <p>
 * 有效字符串需满足：
 * <p>
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 注意空字符串可被认为是有效字符串。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/valid-parentheses
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Exercise01 {
    public boolean isValid(String s) {
        if (s.isEmpty() || s.isBlank()) return false;

        String in = "({[";
        String out = ")}]";
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (in.indexOf(c) >= 0) {
                stack.push(c);
                continue;
            }
            int k;
            if ((k = out.indexOf(c)) >= 0) {
                if (stack.isEmpty()) return false;
                if (in.charAt(k) != stack.pop()) return false;
            }
        }
        return stack.isEmpty();
    }

    /**
     * 给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。
     *
     * @param s
     * @return
     */
    public int longestValidParentheses(String s) {
        int maxans = 0;
        int[] dp = new int[s.length()];
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
                } else if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] - 1) == '(') {
                    dp[i] = dp[i - 1] + ((i - dp[i - 1]) >= 2 ? dp[i - dp[i - 1] - 2] : 0) + 2;
                }
                maxans = Math.max(maxans, dp[i]);
            }
        }
        return maxans;
    }

    /**
     * 给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。
     *
     * @param s
     * @return
     */
    public int longestValidParentheses2(String s) {
        if (s.isEmpty() || s.isBlank()) return 0;
        Stack<Integer> stack = new Stack<>();
        stack.add(-1);

        int maxLength = 0;
        int curLength;

        for (int i = 0; i < s.toCharArray().length; i++) {
            if (s.charAt(i) == '(') {
                stack.add(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.add(i);
                    continue;
                }

                curLength = i - stack.peek();
                maxLength = Math.max(curLength, maxLength);
            }
        }
        return maxLength;
    }

    public static void main(String[] args) {
        String str = "{[]}";
        Exercise01 ex = new Exercise01();
        System.out.println(ex.isValid(str));

        //  String s = "()()()()()()";
        //  String s = "()(()";
        //  String s = "(()()";
        String s = "(()(((()";
        System.out.println(ex.longestValidParentheses2(s));
    }
}
