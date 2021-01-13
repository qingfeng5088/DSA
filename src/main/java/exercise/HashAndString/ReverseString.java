package exercise.HashAndString;

import java.util.*;

public class ReverseString {

    public void reverseString(char[] s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s) {
            stack.add(c);
        }

        int i = 0;
        while (!stack.empty()) {
            s[i++] = stack.pop();
        }
    }

    public void reverseString2(char[] s) {
        List<Character> l = new ArrayList<>();
        for (char c : s) {
            l.add(c);
        }

        Collections.reverse(l);
        final int[] i = {0};
        l.forEach(x -> s[i[0]++] = x);
    }

    public void reverseString3(char[] s) {
        int n = s.length;
        char temp;
        for (int i = 0; i < n / 2; i++) {
            temp = s[i];
            s[i] = s[n - i - 1];
            s[n - i - 1] = temp;
        }
    }

    public String reverseWords(String s) {
        if (s.isBlank() || s.isEmpty()) return "";
        s = s.trim();

        Stack<String> stack = new Stack<>();
        for (String w : s.split(" ")) {
            if(w.isBlank()) continue;
            stack.push(w);
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.empty()) {
            sb.append(stack.pop());
            sb.append(" ");
        }

        return sb.toString().trim();
    }

    public String reverseWords2(String s) {
        // 除去开头和末尾的空白字符
        s = s.trim();
        // 正则匹配连续的空白字符作为分隔符分割
        List<String> wordList = Arrays.asList(s.split("\\s+"));
        Collections.reverse(wordList);
        return String.join(" ", wordList);

    }

    public static void main(String[] args) {
        ReverseString reverseString = new ReverseString();
        char[] s = "hello".toCharArray();
        reverseString.reverseString3(s);

        String str = "  Bob    Loves  Alice   ";

        System.out.println(reverseString.reverseWords2(str));
    }
}
