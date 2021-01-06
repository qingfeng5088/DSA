package Trie;

import java.util.*;

/**
 * Trie 树只是不适合精确匹配查找，这种问题更适合用散列表或者红黑树来解决。 Trie 树比较适合的是查找前缀匹配的字符串
 */
public class Trie {
    private TrieNode root = new TrieNode('/'); // 存储无意义字符

    // 往Trie树中插入一个字符串
    public void insert(char[] text) {
        TrieNode p = root;
        for (int i = 0; i < text.length; ++i) {
            int index = text[i] - 'a';
            if (p.children[index] == null) {
                TrieNode newNode = new TrieNode(text[i]);
                p.children[index] = newNode;
            }
            p = p.children[index];
        }
        p.isEndingChar = true;
    }

    // 在Trie树中查找一个字符串
    public boolean find(char[] pattern) {
        TrieNode p = root;
        for (int i = 0; i < pattern.length; ++i) {
            int index = pattern[i] - 'a';
            if (p.children[index] == null) {
                return false; // 不存在pattern
            }
            p = p.children[index];
        }
        // 找到pattern
        return p.isEndingChar; // 不能完全匹配，只是前缀
    }

    public static class TrieNode {
        public char data;
        public TrieNode[] children = new TrieNode[26];
        public boolean isEndingChar = false;

        public TrieNode(char data) {
            this.data = data;
        }
    }

    public String[] keywordTips(char[] pattern) {
        TrieNode p = root;
        ArrayList<String> allWords = new ArrayList<>();

        for (char c : pattern) {
            int index = c - 'a';
            if (p.children[index] == null) {
                return null;
            }
            p = p.children[index];
        }

        if (p.isEndingChar) allWords.add(String.valueOf(pattern));
        getTipsWord(p, String.valueOf(Arrays.copyOfRange(pattern, 0, pattern.length - 1)), allWords);

        return allWords.toArray(new String[0]);
    }

    private void getTipsWord(TrieNode x, String preWord, List<String> allWords) {
        preWord += x.data;

        String finalPreWord = preWord;
        Arrays.stream(x.children).filter(Objects::nonNull).forEach(y -> {
            if (Arrays.stream(y.children).allMatch(Objects::isNull)) {
                allWords.add(finalPreWord + y.data);
            } else {
                if (y.isEndingChar) allWords.add(finalPreWord + y.data);
                getTipsWord(y, finalPreWord, allWords);
            }
        });
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        String[] words = {"hello", "her", "hero", "herps", "here", "hereof", "how", "hi", "see", "so", "soak"};
        //  String[] words = {"see", "so"};

        for (String word : words) {
            trie.insert(word.toCharArray());
        }

        System.out.println();
        System.out.println("====利用 Trie 树，实现搜索关键词的提示功能=======================");
        System.out.println("----输入h，得到的提示关键词---");
        System.out.println(Arrays.toString(trie.keywordTips("h".toCharArray())));
        System.out.println("----输入he，得到的提示关键词---");
        System.out.println(Arrays.toString(trie.keywordTips("he".toCharArray())));
        System.out.println("----输入her，得到的提示关键词---");
        System.out.println(Arrays.toString(trie.keywordTips("her".toCharArray())));
        System.out.println("----输入here，得到的提示关键词---");
        System.out.println(Arrays.toString(trie.keywordTips("here".toCharArray())));
        System.out.println("----输入s，得到的提示关键词---");
        System.out.println(Arrays.toString(trie.keywordTips("s".toCharArray())));
        System.out.println("----输入so，得到的提示关键词---");
        System.out.println(Arrays.toString(trie.keywordTips("so".toCharArray())));
    }
}