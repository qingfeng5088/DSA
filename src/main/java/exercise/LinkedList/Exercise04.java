package exercise.LinkedList;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * 1:
 * 给定一个链表，判断链表中是否有环。
 * <p>
 * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。
 * <p>
 * 如果链表中存在环，则返回 true 。 否则，返回 false 。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/linked-list-cycle
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * <p>
 * 2:
 * 给你一个链表数组，每个链表都已经按升序排列。
 * 请你将所有链表合并到一个升序链表中，返回合并后的链表。
 */
public class Exercise04 {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public boolean hasCycle(ListNode head) {
        if (head == null) return false;
        HashSet<ListNode> set = new HashSet<>();
        set.add(head);
        while (head.next != null) {
            head = head.next;
            if (set.contains(head)) return true;
            set.add(head);
        }

        return false;
    }

    public boolean hasCycle2(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }

    /**
     * 给你一个链表数组，每个链表都已经按升序排列。
     * 请你将所有链表合并到一个升序链表中，返回合并后的链表。
     *
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        PriorityQueue<ListNode> queue = new PriorityQueue<>(Comparator.comparingInt(x -> x.val));

        for (ListNode node : lists) {
            if (node == null) continue;
            queue.add(node);
        }

        ListNode retNode = new ListNode(-1);
        ListNode endNode = retNode;
        while (!queue.isEmpty()) {
            ListNode node = queue.poll();
            if (node.next != null) queue.add(node.next);
            endNode.next = node;
            endNode = node;
        }

        return retNode.next;
    }

    public ListNode mergeKLists2(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        int n = lists.length;

        while (n > 1) {
            int k = 0;
            for (int i = 0; i < n; i += 2) {
                if (i == n - 1) {
                    lists[k++] = lists[i];
                } else {
                    lists[k++] = mergeTwoList2(lists[i], lists[i + 1]);
                }
            }

            n = k;
        }

        return lists[0];
    }

    private ListNode mergeTwoLists(ListNode a, ListNode b) {
        if (a == null) return b;
        if (b == null) return a;

        ListNode mergedNode;
        if (a.val < b.val) {
            mergedNode = a;
            mergedNode.next = mergeTwoLists(a.next, b);
        } else {
            mergedNode = b;
            mergedNode.next = mergeTwoLists(b.next, a);
        }
        return mergedNode;
    }

    /**
     * 非递归方式
     *
     * @param head1 有序单链表1
     * @param head2 有序单链表2
     * @return 合并后的单链表
     */
    public static ListNode mergeTwoList2(ListNode head1, ListNode head2) {
        if (head1 == null || head2 == null) {
            return head1 != null ? head1 : head2;
        }
        //合并后单链表头结点
        ListNode head = head1.val < head2.val ? head1 : head2;

        ListNode cur1 = head == head1 ? head1 : head2;
        ListNode cur2 = head == head1 ? head2 : head1;

        ListNode pre = null;//cur1前一个元素
        ListNode next = null;//cur2的后一个元素

        while (cur1 != null && cur2 != null) {
            //第一次进来肯定走这里
            if (cur1.val <= cur2.val) {
                pre = cur1;
                cur1 = cur1.next;
            } else {
                next = cur2.next;
                pre.next = cur2;
                cur2.next = cur1;
                pre = cur2;
                cur2 = next;
            }
        }
        pre.next = cur1 == null ? cur2 : cur1;
        return head;
    }

    public static void main(String[] args) {
        test02();

    }

    private static void test01() {
        Exercise04 ex = new Exercise04();
        ListNode node11 = new ListNode(1);
        ListNode node14 = new ListNode(4);
        ListNode node15 = new ListNode(5);
        node11.next = node14;
        node14.next = node15;

        ListNode node21 = new ListNode(1);
        ListNode node23 = new ListNode(3);
        ListNode node24 = new ListNode(4);
        node21.next = node23;
        node23.next = node24;

        ListNode node32 = new ListNode(2);
        ListNode node36 = new ListNode(6);
        node32.next = node36;

        ListNode[] lists = {node11, node21, node32, null};
        // System.out.println(ex.mergeKLists(lists));
        ex.mergeKLists2(lists);
    }

    private static void test02() {
        Exercise04 ex = new Exercise04();
        ListNode node11 = new ListNode(-1);
        ListNode node14 = new ListNode(1);
        node11.next = node14;

        ListNode node21 = new ListNode(-3);
        ListNode node23 = new ListNode(1);
        ListNode node24 = new ListNode(4);
        node21.next = node23;
        node23.next = node24;

        ListNode node31 = new ListNode(-2);
        ListNode node32 = new ListNode(-1);
        ListNode node33 = new ListNode(0);
        ListNode node34 = new ListNode(2);
        node31.next = node32;
        node32.next = node33;
        node33.next = node34;

        ListNode[] lists = {node11, node21, node31};
        // System.out.println(ex.mergeKLists(lists));
        ex.mergeKLists2(lists);
    }
}
