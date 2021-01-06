package LinkedList;

import java.util.Stack;

public class StackTest {
    public static void main(String[] args) {
        //Queue<String> queue = new LinkedList<String>();

        Stack<Character> stack = new Stack<>();

        String str = "2((((((((*(6+2)*[5+4*(5+9)+8/(11sa[sd9(fa)]s)(2-1)]))))))))";

        char[] ca = str.toCharArray();

        boolean isOK = true;
        for (char c : ca) {
            if (c == '(' || c == '[')
                stack.push(c);

            if (c == ')') {
                if (stack.empty()) {
                    isOK = false;
                    break;
                }

                char c1 = stack.pop();

                if ('(' != c1) {
                    isOK = false;
                    break;
                }
            }
            if (c == ']') {
                if (stack.empty()) {
                    isOK = false;
                    break;
                }

                char c1 = stack.pop();

                if ('[' != c1) {
                    isOK = false;
                    break;
                }
            }
        }

        if (!stack.empty()) {
            isOK = false;
        }

        System.out.println(isOK);

    }
}
