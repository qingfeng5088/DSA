package exercise.LinkedList.stackAndQueque;

import java.util.Stack;

public class Exercise02 {
    /**
     * 根据 逆波兰表示法，求表达式的值。
     * 有效的运算符包括 +, -, *, / 。每个运算对象可以是整数，也可以是另一个逆波兰表达式。
     * 说明：
     * 整数除法只保留整数部分。
     * 给定逆波兰表达式总是有效的。换句话说，表达式总会得出有效数值且不存在除数为 0 的情况。
     *
     * @param tokens
     * @return
     */
    public int evalRPN(String[] tokens) {
        if (tokens == null || tokens.length == 0) return 0;
        if (tokens.length == 1) return Integer.parseInt(tokens[0]);
        Stack<Integer> stack = new Stack<>();
        int c;
        for (String token : tokens) {
            if (!"+-*/".contains(token)) {
                stack.add(Integer.parseInt(token));
                continue;
            }

            int a = stack.pop();
            int b = stack.pop();
            switch (token) {
                case "+":
                    c = b + a;
                    break;
                case "-":
                    c = b - a;
                    break;
                case "*":
                    c = b * a;
                    break;
                default:
                    c = b / a;
                    break;
            }
            stack.add(c);
        }
        return stack.pop();
    }

    public static void main(String[] args) {
        System.out.println((6 / -132));
        String[] tokens = {"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"};
        Exercise02 ex = new Exercise02();
        System.out.println(ex.evalRPN(tokens));

    }
}
