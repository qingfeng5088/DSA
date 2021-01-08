package BinaryTree;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class BinaryTree<E> {

    private Node<E> root;


    public BinaryTree(E rootData) {
        addRoot(rootData);
    }

    public Node<E> getRoot() {
        return root;
    }

    public void addRoot(E data) {
        root = new Node<>(data, null, null);
    }

    public void addRootNode(Node<E> node) {
        root = node;
    }

    public void addLeft(Node<E> node, E data) {
        if (node == null) {
            return;
        }

        node.setLeft(new Node<E>(data, null, null, node));
    }

    public void addRight(Node<E> node, E data) {
        if (node == null) {
            return;
        }

        node.setRight(new Node<E>(data, null, null, node));
    }


    // 用于获得树的层数
    public int getTreeDepth(Node<E> root) {
        return root == null ? 0 : (1 + Math.max(getTreeDepth(root.getLeft()), getTreeDepth(root.getRight())));
    }

    public void show(Node<E> root) {
        if (root == null) System.out.println("EMPTY!");
        // 得到树的深度
        int treeDepth = getTreeDepth(root);
        SetGaps(treeDepth);

        // 最后一行的宽度为2的（n - 1）次方乘3，再加1
        // 作为整个二维数组的宽度
        int arrayHeight = treeDepth * 2 - 1;
        int arrayWidth = (2 << (treeDepth - 2)) * 6;
        // 用一个字符串数组来存储每个位置应显示的元素
        String[][] res = new String[arrayHeight][arrayWidth];
        // 对数组进行初始化，默认为一个空格
        for (int i = 0; i < arrayHeight; i++) {
            for (int j = 0; j < arrayWidth; j++) {
                res[i][j] = " ";
            }
        }


        // 从根节点开始，递归处理整个树
        // res[0][(arrayWidth + 1)/ 2] = (char)(root.val + '0');
        writeArray(root, 0, (arrayWidth / 2), res, treeDepth);

        // 此时，已经将所有需要显示的元素储存到了二维数组中，将其拼接并打印即可
        for (String[] line : res) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < line.length; i++) {
                sb.append(line[i]);
                if (line[i].length() > 1 && i <= line.length - 1) {
                    i += line[i].length() > 4 ? 2 : line[i].length() - 1;
                }
            }
            System.out.println(sb.toString());
        }
    }

    int[] gaps = null;

    void SetGaps(int treeDepth) {
        gaps = new int[treeDepth];

        for (int i = 0; i < treeDepth - 1; i++) {
            int arrayWidth = (2 << (treeDepth - 2 - i)) * 6;
            int rootlet = (arrayWidth / 2);
            gaps[i] = rootlet / 4;
        }

        gaps[gaps.length - 2] = 2;
        gaps[gaps.length - 1] = 1;
    }

    private void writeArray(Node<E> currNode, int rowIndex, int columnIndex, String[][] res, int treeDepth) {
        // 保证输入的树不为空
        if (currNode == null) return;
        if (columnIndex > res[0].length - 1) return;

        // 先将当前节点保存到二维数组中
        res[rowIndex][columnIndex] = String.valueOf(currNode.getData());

        // 计算当前位于树的第几层
        int currLevel = ((rowIndex + 1) / 2);
        // 若到了最后一层，则返回
        if (currLevel >= treeDepth) return;
        // 计算当前行到下一行，每个元素之间的间隔（下一行的列索引与当前元素的列索引之间的间隔）
        if (columnIndex < 3) return;

        int gap = gaps[currLevel];

        // 对左儿子进行判断，若有左儿子，则记录相应的"/"与左儿子的值
        if (currNode.getLeft() != null) {
            res[rowIndex + 1][columnIndex - gap] = "/";
            int d = (gap == 2 ? 3 : gap * 2);
            writeArray(currNode.getLeft(), rowIndex + 2, columnIndex - d, res, treeDepth);
        }

        // 对右儿子进行判断，若有右儿子，则记录相应的"\"与右儿子的值
        if (currNode.getRight() != null) {
            res[rowIndex + 1][columnIndex + gap] = "\\";

            int d = (gap == 2 ? 3 : gap * 2);
            writeArray(currNode.getRight(), rowIndex + 2, columnIndex + d, res, treeDepth);
        }

    }

    /**
     * 前序遍历
     *
     * @param rt
     */
    public void preOrder(Node<E> rt) {
        if (rt == null) {
            return;
        }

        System.out.print(rt.getData() + "->");
        preOrder(rt.getLeft());
        preOrder(rt.getRight());
    }

    /**
     * 前序遍历 设置哈夫曼编码
     *
     * @param rt
     */
    public void setHumffmanCode(Node<E> rt) {
        if (rt == null) {
            return;
        }

        if (rt != root) {
            rt.humffmanCode = rt.getFather().humffmanCode + (rt.isLeft() ? "0" : "1");
        }

        setHumffmanCode(rt.getLeft());
        setHumffmanCode(rt.getRight());
    }

    /**
     * 中序遍历
     *
     * @param rt
     */
    public void inOrder(Node<E> rt) {
        if (rt == null) {
            return;
        }

        inOrder(rt.getLeft());
        System.out.print(rt.getData() + "->");
        inOrder(rt.getRight());
    }

    /**
     * 中序遍历
     *
     * @param rt
     */
    public void postOrder(Node<E> rt) {
        if (rt == null) {
            return;
        }

        postOrder(rt.getLeft());
        postOrder(rt.getRight());
        System.out.print(rt.getData() + "->");
    }

    /**
     * 按层遍历
     * 借助队列，先进先出，出节点的同时把左右子节点按顺序入队列。
     * 然后依次出队列，正好是按层遍历。
     *
     * @param rt
     */
    public void layerOrder(Node<E> rt) {
        if (rt == null) {
            return;
        }

        Queue<Node<E>> queue = new LinkedList<Node<E>>();
        queue.add(rt);

        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            System.out.print(node.getData() + "->");
            Node<E> left = node.getLeft();
            if (left != null) {
                queue.add(left);
            }

            Node<E> right = node.getRight();
            if (right != null) {
                queue.add(right);
            }
        }
    }

    public void leavesOrder(Node<E> rt) {
        if (rt == null) {
            return;
        }

        if (rt.getLeft() == null && rt.getRight() == null) {
            System.out.print(rt.getData() + "->");
            return;
        }

        leavesOrder(rt.getLeft());
        leavesOrder(rt.getRight());
    }

    public void getHumffmanCode(Node<E> rt, Map<E, String> codeList) {
        if (rt == null) {
            return;
        }

        if (rt.getLeft() == null && rt.getRight() == null) {
            codeList.put(rt.getData(), rt.humffmanCode);
            return;
        }

        getHumffmanCode(rt.getLeft(), codeList);
        getHumffmanCode(rt.getRight(), codeList);
    }

    public static void main(String[] args) {
        BinaryTree<String> bt = new BinaryTree<>("A");
        initBT(bt);


        //前序遍历 A->B->D->E->C->F->G

        System.out.println("前序遍历");
        bt.preOrder(bt.root);

        //中序遍历 D->B->E->A->F->C->G
        System.out.println();
        System.out.println("中序遍历");
        bt.inOrder(bt.root);

        //后序遍历 D->E->B->F->G->C->A
        System.out.println();
        System.out.println("后序遍历");
        bt.postOrder(bt.root);

        //按层遍历 A->B->C->D->E->F->G
        System.out.println();
        System.out.println("按层遍历");
        bt.layerOrder(bt.root);

        bt.setHumffmanCode(bt.root);
        //叶子遍历 A->B->C->D->E->F->G
        System.out.println();
        System.out.println("按叶子遍历");
        bt.leavesOrder(bt.root);


        System.out.println();
        System.out.println("-----------------打印整颗树----------------------");
        bt.show(bt.root);
    }

    /**
     * 构建二叉树
     * A
     * /\
     * B     C
     * /\    /\
     * D   E  F  G
     *
     * @param bt
     */
    private static void initBT(BinaryTree<String> bt) {
        bt.addLeft(bt.root, "B");
        bt.addRight(bt.root, "C");

        Node<String> left1 = bt.root.getLeft();
        bt.addLeft(left1, "D");
        bt.addRight(left1, "E");

        Node<String> right1 = bt.root.getRight();
        bt.addLeft(right1, "F");
        bt.addRight(right1, "G");

        Node<String> left11 = left1.getLeft();
        bt.addLeft(left11, "H");
        bt.addRight(left11, "I");

        Node<String> left12 = left1.getRight();
        bt.addLeft(left12, "J");
        bt.addRight(left12, "K");

        Node<String> left121 = left12.getRight();
        bt.addLeft(left121, "R");
        bt.addRight(left121, "SSS");


        Node<String> right11 = right1.getLeft();
        bt.addLeft(right11, "L");
        bt.addRight(right11, "M");

        Node<String> right111 = right11.getLeft();
        bt.addLeft(right111, "TT");
        bt.addRight(right111, "UUU");


        Node<String> right12 = right1.getRight();
        bt.addLeft(right12, "N");
        bt.addRight(right12, "O");

        Node<String> left111 = left11.getLeft();
        bt.addLeft(left111, "P");
        bt.addRight(left111, "Q");
    }

}
