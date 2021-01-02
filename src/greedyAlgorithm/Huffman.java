package greedyAlgorithm;

import BinaryTree.BinaryTree;
import BinaryTree.Node;

import java.util.*;

public class Huffman {
    public static class KeyValue implements Comparable<KeyValue> {
        char c;
        int n;

        public KeyValue(char c, int n) {
            this.c = c;
            this.n = n;
        }

        public String toString() {
            return c + ":" + n;
        }

        @Override
        public int compareTo(KeyValue o) {
            return this.n - o.n;
        }
    }

    public static void main(String[] args) {
        PriorityQueue<KeyValue> heap = new PriorityQueue<KeyValue>();
        char maxchar = 0;
        for (char c : old.toCharArray()) {
            if (c > maxchar) maxchar = c;

            KeyValue e = heap.stream().filter(x -> x.c == c).findFirst().orElse(null);
            int n = 1;
            if (e != null) {
                heap.remove(e);
                n = e.n + 1;
            }
            heap.offer(new KeyValue(c, n));
        }

        char r = (char)(maxchar + 1);
        Node<KeyValue> rootNode = null;
        BinaryTree<KeyValue> tree = new BinaryTree<KeyValue>(null);
        List<Node<KeyValue>> nodes = new ArrayList<>();

        KeyValue kv1, kv2;
        while (!heap.isEmpty()) {
            kv1 = heap.poll();
            kv2 = heap.poll();

            if (kv1 == null || kv2 == null) break;

            KeyValue root = new KeyValue(++r, kv1.n + kv2.n);
            heap.offer(root);
            rootNode = new Node<>(root, null, null);
            nodes.add(rootNode);

            KeyValue finalKv1 = kv1;
            Node<KeyValue> node1 = nodes.stream().filter(x -> x.getData().c == finalKv1.c).findFirst().orElse(null);
            if (node1 == null) {
                node1 = new Node<>(kv1, null, null);
                nodes.add(node1);
            }
            node1.setFather(rootNode);

            KeyValue finalKv2 = kv2;
            Node<KeyValue> node2 = nodes.stream().filter(x -> x.getData().c == finalKv2.c).findFirst().orElse(null);
            if (node2 == null) {
                node2 = new Node<>(kv2, null, null);
                nodes.add(node2);
            }
            node2.setFather(rootNode);

            if (node1.getData().n < node2.getData().n) {
                rootNode.setLeft(node1);
                rootNode.setRight(node2);
            } else {
                rootNode.setLeft(node2);
                rootNode.setRight(node1);
            }

        }


        tree.addRootNode(rootNode);
        tree.setHumffmanCode(tree.getRoot());
        System.out.println("-------遍历叶子节点--------");
        tree.leavesOrder(tree.getRoot());

        Map<KeyValue, String> codeList = new HashMap<>();
        tree.getHumffmanCode(tree.getRoot(), codeList);

        System.out.println();
        System.out.println("-------打印树状结构--------");
        tree.show(tree.getRoot());

        Map<String, Character> huffmanCodes = new HashMap<>();
        Map<Character, String> huffmanCharCodes = new HashMap<>();
        for (KeyValue kv : codeList.keySet()) {
            huffmanCodes.put(codeList.get(kv), kv.c);
            huffmanCharCodes.put(kv.c, codeList.get(kv));
        }

        String old2 = "";
        String compress = "";
        String decompression = "";
        for (char c : old.toCharArray()) {
            old2 += Integer.toBinaryString(c);
            compress += huffmanCharCodes.get(c);
        }

        String temp = "";
        for (char c : compress.toCharArray()) {
            temp += c;
            if (huffmanCodes.containsKey(temp)) {
                decompression += huffmanCodes.get(temp);
                temp = "";
            }
        }

        System.out.println("     源字串:" + old);
        System.out.println("源字串二进制:" + old2);
        System.out.println("压缩后二进制:" + compress);
        System.out.println("     解压后:" + decompression);

    }

   static String old = "中新网1月2日电 ABC abc据据据据据据据据据据据据据据据据商务部网站消息，商务部新闻发言人2日就美国纽约证券交易所将三家中国企业从纽交所摘牌事发表谈话时表示。";
}
