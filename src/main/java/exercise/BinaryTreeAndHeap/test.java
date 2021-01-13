package exercise.BinaryTreeAndHeap;

import java.util.LinkedList;

public class test {
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
    public static void main(String[] args) {
        LinkedList<TreeNode> qu = new LinkedList<>();
        TreeNode node1 = new TreeNode(4);
        TreeNode node3 = new TreeNode(4);

        qu.push(node1);
        qu.push(node3);

        System.out.println(qu.size());
        qu.poll();
        System.out.println(qu.isEmpty());

    }
}
