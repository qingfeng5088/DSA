package skipList;

import java.util.Random;

// 跳表中存储的是正整数，并且存储的数据是不重复的
public class SkipList {

    private static final int MAX_LEVEL = 16;    // 结点的个数

    private static int totalNodeCount = 0;

    private int levelCount = 1;   // 索引的层级数

    private Node head = new Node();    // 头结点

    private Random random = new Random();

    // 查找操作
    public Node find(int value) {
        Node p = head;
        for (int i = levelCount - 1; i >= 0; --i) {
            while (p.next[i] != null && p.next[i].data < value) {
                p = p.next[i];
            }
        }

        if (p.next[0] != null && p.next[0].data == value) {
            return p.next[0];    // 找到，则返回原始链表中的结点
        } else {
            return null;
        }
    }

    // 插入操作
    public void insert(int value) {
        totalNodeCount++;
        // 获取插入新数据的层级(0~level-1都需要插入)
        int level = randomLevel();
        Node newNode = new Node();
        newNode.data = value;
        newNode.maxLevel = level;   // 通过随机函数改变索引层的结点布置

        // 需要插入位置的结点数组
        Node update[] = new Node[level];
        for (int i = 0; i < level; ++i) {
            update[i] = head;
        }

        Node p = head;
        // 遍历跳表的0~level-1层，找到需要插入位置的结点
        for (int i = level - 1; i >= 0; --i) {
            // 遍历当前i层，找到合适的插入结点的位置，放入update数组中
            while (p.next[i] != null && p.next[i].data < value) {
                p = p.next[i];
            }
            // 更新要插入的结点位置
            update[i] = p;
        }

        // 将新结点插入到跳表中
        for (int i = 0; i < level; ++i) {
            // 将需要插入结点位置的next数组元素复制到新结点中，层级之间相互不影响
            newNode.next[i] = update[i].next[i];

            // 将新结点插入到当前结点的后面
            update[i].next[i] = newNode;
        }

        // 更新levelCount
        if (levelCount < level) {
            levelCount = level;
        }
    }

    // 删除操作
    public void delete(int value) {
        Node[] update = new Node[levelCount];
        Node p = head;
        for (int i = levelCount - 1; i >= 0; --i) {
            while (p.next[i] != null && p.next[i].data < value) {
                p = p.next[i];
            }
            update[i] = p;
        }

        if (p.next[0] != null && p.next[0].data == value) {
            for (int i = levelCount - 1; i >= 0; --i) {
                if (update[i].next[i] != null && update[i].next[i].data == value) {
                    update[i].next[i] = update[i].next[i].next[i];
                }
            }
        }
    }

    // 随机函数
    private int randomLevel() {
        int level = 1;
        for (int i = 1; i < MAX_LEVEL; ++i) {
            if (random.nextInt() % 2 == 1) {
                level++;
            }
        }

        return level;
    }

    // Node内部类
    public class Node {
        private int data = -1;
        private Node next[] = new Node[MAX_LEVEL];
        private int maxLevel = 0;

        // 重写toString方法
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("{data:");
            builder.append(data);
            builder.append("; leves: ");
            builder.append(maxLevel);
            builder.append(" }");
            return builder.toString();
        }
    }

    // 显示跳表中的结点
    public void display() {
        Node p = head;
        while (p.next[0] != null) {
            System.out.println(p.next[0] + " ");
            p = p.next[0];
        }
        System.out.println();
    }

    // 显示跳表中的结点
    public void allDisplay() {
        for (int i = levelCount - 1; i >= 0; i--) {
            Node next = head.next[i];
            Node start0 = head.next[0];

            System.out.print("第" + i + "层: ");
            while (next != null) {
                while (next.data > start0.data) {
                    System.out.print("----");
                    start0 = start0.next[0];
                }
                System.out.printf("->%-2d",next.data);
               // System.out.printf("(%d,%d)",x,y);
                start0 = start0.next[0];

                next = next.next[i];
            }

            System.out.println();
        }

    }

    public static void main(String[] args) {
        SkipList skipList = new SkipList();
        skipList.insert(1);
        skipList.insert(3);
        skipList.insert(5);
        skipList.insert(6);
        skipList.insert(7);
        skipList.insert(8);
        skipList.insert(9);
        skipList.insert(12);
        skipList.insert(24);
        skipList.insert(14);
        skipList.insert(34);
        skipList.insert(41);
        skipList.insert(26);
        skipList.insert(35);
        skipList.insert(18);
        skipList.insert(56);
        skipList.insert(15);
        skipList.insert(62);
        skipList.insert(97);
        skipList.insert(82);
        skipList.insert(74);
        skipList.insert(67);
        skipList.insert(71);
        skipList.insert(73);
        skipList.insert(85);
        skipList.insert(93);

        skipList.display();
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
        skipList.allDisplay();
        //   System.out.println(skipList.find(7));


    }

}