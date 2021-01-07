package LinkedList;

public class Reversal {

    private static void print(Node<String> node) {

        while (node.next != null) {
            System.out.print("-->" + node.e);
            node = node.next;
        }
        System.out.print("-->" + node.e);


    }

    private static Node<String> reversal(Node<String> node) {
        if (node == null || node.next == null) return null;

        Node<String> pre = null;
        Node<String> cur = node;
        Node<String> temp;
        while (cur != null) {
            temp = cur;
            cur = cur.next;
            temp.next = pre;
            pre = temp;
        }

        return pre;
    }

    public static void main(String[] args) {
        Node<String> nodeE = new Node<>("E", null);
        Node<String> nodeD = new Node<>("D", nodeE);
        Node<String> nodeC = new Node<>("C", nodeD);
        Node<String> nodeB = new Node<>("B", nodeC);
        Node<String> nodeA = new Node<>("A", nodeB);

        print(nodeA);

        System.out.println();
        Node<String> node = reversal(nodeA);
//
        print(node);
    }

}
