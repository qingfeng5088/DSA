package exercise.BinaryTreeAndHeap;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class InvertTree {
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        if (root.left == null && root.right == null) return root;

        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        invertTree(root.left);
        invertTree(root.right);

        return root;
    }

    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    public int maxDepth2(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        int ans = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                size--;
            }
            ans++;
        }
        return ans;
    }

    /**
     * 给定一个二叉树，判断其是否是一个有效的二叉搜索树。
     * <p>
     * 假设一个二叉搜索树具有如下特征：
     * <p>
     * 节点的左子树只包含小于当前节点的数。
     * 节点的右子树只包含大于当前节点的数。
     * 所有左子树和右子树自身必须也是二叉搜索树。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/validate-binary-search-tree
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param root
     * @return
     */
    public boolean isValidBST(TreeNode root) {
        if (root == null) return true;
        if (root.left == null && root.right == null) return true;
        boolean ret = isOk(root.left, root.val, 0) && isOk(root.right, root.val, 1);
        if (ret) return isValidBST(root.left) && isValidBST(root.right);

        return false;
    }

    private boolean isOk(TreeNode node, int v, int flg) {
        if (node == null) return true;
        LinkedList<TreeNode> qu = new LinkedList<>();
        qu.add(node);

        while (!qu.isEmpty()) {
            node = qu.poll();
            if (flg == 0) {
                if (node.val >= v) return false;
            } else {
                if (v >= node.val) return false;
            }

            if (node.left != null) qu.add(node.left);
            if (node.right != null) qu.add(node.right);
        }

        return true;
    }


    public boolean isValidBST2(TreeNode root) {
        return helper(root, null, null);
    }

    public boolean helper(TreeNode node, Integer lower, Integer upper) {
        if (node == null) {
            return true;
        }

        int val = node.val;
        if (lower != null && val <= lower) {
            return false;
        }
        if (upper != null && val >= upper) {
            return false;
        }

        if (!helper(node.right, val, upper)) {
            return false;
        }
        if (!helper(node.left, lower, val)) {
            return false;
        }
        return true;
    }

    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) return false;

        Map<TreeNode, Integer> map = new HashMap<>();
        map.put(root, 0);

        LinkedList<TreeNode> qu = new LinkedList<>();
        qu.add(root);
        while (!qu.isEmpty()) {
            root = qu.poll();
            int cur = map.get(root) + root.val;
            if (cur == sum && isLeaf(root)) return true;

            map.remove(root);
            if (root.left != null) {
                qu.add(root.left);
                map.put(root.left, cur);
            }

            if (root.right != null) {
                qu.add(root.right);
                map.put(root.right, cur);
            }
        }

        return false;
    }

    private boolean isLeaf(TreeNode node) {
        return node != null && node.left == null && node.right == null;
    }

    public boolean hasPathSum2(TreeNode root, int sum) {
        if (root == null) return false;
        if (isLeaf(root)) return root.val == sum;

        return hasPathSum(root.left,sum - root.val) || hasPathSum(root.right,sum - root.val);
    }


    public static void main(String[] args) {
        TreeNode n4 = new TreeNode(4);
        TreeNode n2 = new TreeNode(2);
        TreeNode n7 = new TreeNode(7);
        TreeNode n1 = new TreeNode(1);
        TreeNode n3 = new TreeNode(3);
        TreeNode n6 = new TreeNode(6);
        TreeNode n9 = new TreeNode(9);
        TreeNode n11 = new TreeNode(11);

        n4.left = n2;
        n4.right = n7;
        n2.left = n1;
        n2.right = n3;
        n7.left = n6;
        n7.right = n9;
        n9.right = n11;

        InvertTree invertTree = new InvertTree();
//        TreeNode retNode = invertTree.invertTree(n4);
//        System.out.println(invertTree.maxDepth2(n4));
//        System.out.println(invertTree.isValidBST(n4));

//        TreeNode N1_2 = new TreeNode(2);
//        TreeNode N1_1 = new TreeNode(1);
//        TreeNode N1_3 = new TreeNode(3);
//
//        N1_2.left = N1_1;
//        N1_2.right = N1_3;
//        System.out.println("----------1:" + invertTree.isValidBST(N1_2));
////
//        TreeNode N2_5 = new TreeNode(5);
//        TreeNode N2_1 = new TreeNode(1);
//        TreeNode N2_4 = new TreeNode(4);
//        TreeNode N2_3 = new TreeNode(3);
//        TreeNode N2_6 = new TreeNode(6);
//
//        N2_5.left = N2_1;
//        N2_5.right = N2_4;
//
//        N2_4.left = N2_3;
//        N2_4.right = N2_6;
//
//        System.out.println("----------2:" + invertTree.isValidBST(N2_5));
////
        TreeNode N3_0 = new TreeNode(0);
        TreeNode N3_1 = new TreeNode(-1);
        N3_0.left = N3_1;

        System.out.println("----------3:" + invertTree.isValidBST(N3_0));
//
//        TreeNode N4_5 = new TreeNode(5);
//        TreeNode N4_4 = new TreeNode(4);
//        TreeNode N4_6 = new TreeNode(6);
//        TreeNode N4_3 = new TreeNode(3);
//        TreeNode N4_7 = new TreeNode(7);
//
//        N4_5.left = N4_4;
//        N4_5.right = N4_6;
//        N4_6.left = N4_3;
//        N4_6.right = N4_7;
//
//        System.out.println("----------4:" + invertTree.isValidBST(N4_5));

//        TreeNode N5_32 = new TreeNode(32);
//        TreeNode N5_26 = new TreeNode(26);
//        TreeNode N5_47 = new TreeNode(47);
//        TreeNode N5_19 = new TreeNode(19);
//        TreeNode N5_56 = new TreeNode(56);
//        TreeNode N5_27 = new TreeNode(27);
//
//        N5_32.left = N5_26;
//        N5_32.right = N5_47;
//        N5_26.left = N5_19;
//        N5_19.right = N5_27;
//        N5_47.right = N5_56;
//
//        System.out.println("----------5:" + invertTree.isValidBST(N5_32));
//
//        System.out.println("-------结束-----");

//        TreeNode node5 = new TreeNode(5);
//        TreeNode node4 = new TreeNode(4);
//        TreeNode node8 = new TreeNode(8);
//        TreeNode node11 = new TreeNode(11);
//        TreeNode node13 = new TreeNode(13);
//        TreeNode node4_2 = new TreeNode(4);
//        TreeNode node7 = new TreeNode(7);
//        TreeNode node2 = new TreeNode(3);
//        TreeNode node1 = new TreeNode(5);
//
//        node5.left = node4;
//        node5.right = node8;
//
//        node4.left = node11;
//
//        node8.left= node13;
//        node8.right = node4_2;
//
//        node11.left = node7;
//        node11.right = node2;
//
//        node4_2.right = node1;

//        System.out.println(invertTree.hasPathSum(node5, 22));

        TreeNode node1 = new TreeNode(-2);
        TreeNode node2 = new TreeNode(-3);

        node1.right = node2;

        System.out.println(invertTree.hasPathSum(node1, -5));

    }
}
