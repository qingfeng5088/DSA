package dynamicProgramming;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 动态规划求解 杨辉三角变形问题
 * “ 杨辉三角 ” 不知道你听说过吗？我们现在对它进行一些改造。每个位置的数字可以随意填写，经过某个数字只能到达下面一层相邻的两个数字。
 * 假设你站在第一层，往下移动，我们把移动到最底层所经过的所有数字之和，定义为路径的长度。请你编程求出从最高层移动到最底层的最短路径长度。
 * ----------5
 * ------7      8
 * ----2     3     4
 * -8    9      6   7
 * 5   7    9      4  5
 */
public class TriangleArray_DP {
    Node root;

    public TriangleArray_DP(Node root) {
        this.root = root;
    }

    private static class Node {
        int value;
        Node left;
        Node right;

        boolean visited;//标识顶点是否已访问

        int minPath = Integer.MAX_VALUE;
        Node minFatherNode;

        public Node(int value) {
            this.value = value;
        }

        public String toString() {
            return value + "";
        }
    }

    Node minPathNode; // 包含最短路径的节点

    /**
     * 按层遍历每个节点，并计算当前节点的最短路径，并记录到该节点的minPath中
     * 同时记录最近路径的父节点
     */
    public void computeNodePath() {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node.left == null && node.right == null) {
                if (minPathNode == null) minPathNode = node;
                if (minPathNode.minPath > node.minPath) minPathNode = node;
            }

            Node left = node.left;
            if (left != null) {
                queue.add(left);

                if (left.minPath > (left.value + node.minPath)) {
                    left.minPath = left.value + node.minPath;
                    left.minFatherNode = node;
                }
            }

            Node right = node.right;
            if (right != null) {
                queue.add(right);
                if (right.minPath > (right.value + node.minPath)) {
                    right.minPath = right.value + node.minPath;
                    right.minFatherNode = node;
                }
            }
        }
    }


    public void printMinPath(Node node) {
        if (node == null) return;
        Stack<Node> st = new Stack<>();
        Node temp = node;
        while (temp != root) {
            st.push(temp);
            temp = temp.minFatherNode;
        }
        st.push(temp);

        while (!st.isEmpty()) {
            temp = st.pop();
            System.out.print(temp.value + (st.isEmpty() ? "" : "->"));
        }
    }

    public void leavesOrder(Node rt) {
        if (rt == null) {
            return;
        }

        if (rt.left == null && rt.right == null && !rt.visited) {
            rt.visited = true;
            System.out.println("-----节点" + rt.value + "的最短路径---" + rt.minPath);
            printMinPath(rt);
            System.out.println();
            return;
        }

        leavesOrder(rt.left);
        leavesOrder(rt.right);
    }

    public static void main(String[] args) {
        Node root = initData();
        TriangleArray_DP tap = new TriangleArray_DP(root);
        tap.computeNodePath();
        tap.leavesOrder(root);
        System.out.println("--------------最小到底层路径--节点:" + tap.minPathNode.value + "--------" + tap.minPathNode.minPath);
        tap.printMinPath(tap.minPathNode);
    }

    private static Node initData() {
        Node n5_1 = new Node(5);
        Node n7_1 = new Node(7);
        Node n8_1 = new Node(8);
        Node n2_1 = new Node(2);
        Node n3 = new Node(3);
        Node n4_1 = new Node(4);
        Node n8_2 = new Node(8);
        Node n9_1 = new Node(9);
        Node n6 = new Node(6);
        Node n7_3 = new Node(7);
        Node n5_3 = new Node(5);
        Node n7_2 = new Node(7);
        Node n9_2 = new Node(9);
        Node n4_2 = new Node(4);
        Node n5_2 = new Node(5);

        n5_1.minPath = 5;


        n5_1.left = n7_1;
        n5_1.right = n8_1;

        n7_1.left = n2_1;
        n7_1.right = n3;

        n8_1.left = n3;
        n8_1.right = n4_1;

        n2_1.left = n8_2;
        n2_1.right = n9_1;

        n3.left = n9_1;
        n3.right = n6;

        n4_1.left = n6;
        n4_1.right = n7_3;

        n8_2.left = n5_3;
        n8_2.right = n7_2;

        n9_1.left = n7_2;
        n9_1.right = n9_2;

        n6.left = n9_2;
        n6.right = n4_2;

        n7_3.left = n4_2;
        n7_3.right = n5_2;

        return n5_1;
    }
}