package BinaryTree;

import utils.Utils;

import java.util.HashSet;

/**
 * Java 语言: 二叉查找树
 *
 * @author skywang
 * @date 2013/11/07
 */
public class RBTreeTest {

    private static int count = 0;
    private static Integer a[] = {10, 40, 30, 60, 90, 70, 20, 50, 80, 7, 9, 18, 17, 15, 13, 14, 8, 12, 21, 45, 68, 74, 88};
    private static final boolean mDebugInsert = true;    // "插入"动作的检测开关(false，关闭；true，打开)
    private static final boolean mDebugDelete = false;    // "删除"动作的检测开关(false，关闭；true，打开)

    public static void main(String[] args) {
        int i, ilen = a.length;
        RBTree<Integer> tree = new RBTree<Integer>();

        System.out.printf("== 原始数据: ");
        for (i = 0; i < ilen; i++)
            System.out.printf("%d ", a[i]);
        System.out.printf("\n");

        for (i = 0; i < ilen; i++) {
            tree.insert(a[i]);
            // 设置mDebugInsert=true,测试"添加函数"
            if (mDebugInsert) {
                System.out.printf("== 添加节点: %d\n", a[i]);
                System.out.printf("== 树的详细信息: \n");
                //tree.print();
                tree.printTree();
                System.out.printf("\n");
            }
        }

        System.out.printf("== 前序遍历: ");
        tree.preOrder();

        System.out.printf("\n== 中序遍历: ");
        tree.inOrder();

        System.out.printf("\n== 后序遍历: ");
        tree.postOrder();
        System.out.printf("\n");

        System.out.printf("== 最小值: %s\n", tree.minimum());
        System.out.printf("== 最大值: %s\n", tree.maximum());
        System.out.printf("== 树的详细信息: \n");
        tree.print();
        System.out.printf("\n");

        // 设置mDebugDelete=true,测试"删除函数"
        if (mDebugDelete) {
            for (i = 0; i < ilen; i++) {
                tree.remove(a[i]);

                System.out.printf("== 删除节点: %d\n", a[i]);
                System.out.printf("== 树的详细信息: \n");
                tree.print();
                System.out.printf("\n");
            }
        }

        System.out.println("------------------------打印树状结构-----------------------");
        tree.printTree();

        // 销毁二叉树
        tree.clear();

        System.out.println("=================性能测试=========================");
        count = 4600_0000;
        System.out.println("-----------------------条数据准备时间:-----------------------:");
        Utils.countTime(RBTreeTest::performanceTest);

        System.out.println("=================插入性能测试=================");
        System.out.println("-----平衡树AVLTree---" + a.length + "条数据插入时间:----------:");
        Utils.countTime(RBTreeTest::AVLTreePerformanceTest);

        System.out.println("-----红黑树RBTree----" + a.length + "条数据插入时间:----------:");
        Utils.countTime(RBTreeTest::RBTreePerformanceTest);

        System.out.println("=================查询性能测试=================");
        System.out.println("-----平衡树AVLTree---" + a.length + "条数据查询时间:----------:");
        Utils.countTime(RBTreeTest::AVLTreeSearchPerformanceTest);

        System.out.println("-----红黑树RBTree----" + a.length + "条数据查询时间:----------:");
        Utils.countTime(RBTreeTest::RBTreeSearchPerformanceTest);

        System.out.println("=================删除性能测试=================");
        System.out.println("-----平衡树AVLTree---" + a.length + "条数据删除时间:----------:");
        Utils.countTime(RBTreeTest::AVLTreeDelPerformanceTest);

        System.out.println("-----红黑树RBTree----" + a.length + "条数据删除时间:----------:");
        Utils.countTime(RBTreeTest::RBTreeDelPerformanceTest);

    }

    static  int del[];

    static void performanceTest() {
        //  a = new int[count];

        HashSet<Integer> set = new HashSet<>();

        for (int i = 0; i < count-1; i++) {
            set.add(Utils.getRandomInt(count * 20));
        }

        a = set.toArray(a);
    }
    static RBTree<Integer> RBTree = new RBTree<Integer>();
    static void RBTreePerformanceTest() {
        for (int i = 0; i < a.length; i++) {
            RBTree.insert(a[i]);
        }
        // 销毁二叉树
        //tree.clear();
    }

    static  AVLTree<Integer> AVLTree = new AVLTree<Integer>();
    static void AVLTreePerformanceTest() {
        for (int i = 0; i < a.length; i++) {
            AVLTree.insert(a[i]);
        }

        // 销毁二叉树
        //tree.destroy();
    }

    static void AVLTreeSearchPerformanceTest() {
        for (int i = a.length - 1; i >= 0; i--) {
            AVLTree.search(a[i]);
        }
    }

    static void RBTreeSearchPerformanceTest() {
        for (int i = a.length - 1; i >= 0; i--) {
            RBTree.search(a[i]);
        }
    }

    static void AVLTreeDelPerformanceTest() {
        for (int i = a.length - 1; i >= 0; i--) {
            AVLTree.remove(a[i]);
        }
    }

    static void RBTreeDelPerformanceTest() {
        for (int i = a.length - 1; i >= 0; i--) {
            RBTree.remove(a[i]);
        }
    }
}