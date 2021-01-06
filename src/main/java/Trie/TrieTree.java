package Trie;

import java.util.ArrayList;
import java.util.List;


public class TrieTree<E> {
    private final TrieNode<E> root;

    /*
     * 无数据结构设计下的蛮力中文键树
     */
    private static class TrieNode<E> {
        public E value;
        public ArrayList<TrieNode<E>> children;
        public boolean isEndingChar = false;

        public TrieNode(E value) {
            this.value = value;
            children = new ArrayList<>();
        }
    }

    public TrieTree() {
        root = new TrieNode<E>(null);
    }

    public void insert(List<E> datas) {
        TrieNode<E> p = root;
        for (E e : datas) {
            TrieNode<E> node = p.children.stream().filter(x -> x.value.equals(e)).findFirst().orElse(null);
            if (node == null) {
                node = new TrieNode<>(e);
                p.children.add(node);
            }
            p = node;
        }

        p.isEndingChar = true;
    }

    public List<List<E>> search(List<E> pattern) {
        TrieNode<E> p = root;
        List<List<E>> allWords = new ArrayList<>();

        for (E e : pattern) {
            TrieNode<E> node = p.children.stream().filter(x -> x.value.equals(e)).findFirst().orElse(null);
            if (node == null) {
                return allWords;
            }
            p = node;
        }

        if (p.isEndingChar) allWords.add(pattern);
        pattern.remove(pattern.size() - 1);
        getTipsWord(p, pattern, allWords);

        return allWords;
    }

    private void getTipsWord(TrieNode<E> node, List<E> prePattern, List<List<E>> allWords) {
        prePattern.add(node.value);
        List<E> pre = new ArrayList<>(prePattern);
        node.children.forEach(x -> {
            if (x.children.size() == 0) {
                List<E> pre1 = new ArrayList<>(pre);
                pre1.add(x.value);
                allWords.add(pre1);
            } else {
                if (x.isEndingChar) {
                    List<E> pre2 = new ArrayList<>(pre);
                    pre2.add(x.value);
                    allWords.add(pre2);
                }
                List<E> pre3 = new ArrayList<>(pre);
                getTipsWord(x, pre3, allWords);
            }
        });
    }

    private static List<Character> getList(String str) {
        List<Character> retList = new ArrayList<>();
        for (char c : str.toCharArray()) {
            retList.add(c);
        }

        return retList;
    }

    private static <E> String ListToString(List<E> list) {
        StringBuilder result = new StringBuilder();
        for (E e : list) {
            result.append(e);
        }

        return result.toString();
    }

    public static void main(String[] args) {
        TrieTree<Character> chinese = new TrieTree<Character>();
        chinese.insert(getList("中"));
        chinese.insert(getList("中国"));
        chinese.insert(getList("中国人"));
        chinese.insert(getList("中华人民"));
        chinese.insert(getList("中华儿女"));
        chinese.insert(getList("中华人崛起"));
        chinese.insert(getList("中华上下五千年"));
        chinese.insert(getList("中华民族"));


        List<List<Character>> retList = chinese.search(getList("中华"));
        for (List<Character> es : retList) {
            System.out.println(ListToString(es));
        }

    }
}