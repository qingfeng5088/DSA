package LinkedList;

public class Node<E> implements Comparable<Node<E>> {
    public E e;
    public Node<E> next;

    public Node(E e, Node<E> next) {
        this.e = e;
        this.next = next;
    }

    public E getE() {
        return e;
    }

    public void setE(E e) {
        this.e = e;
    }


    @Override
    public int compareTo(Node<E> o) {
        return ((String) this.e).compareTo((String) o.e);
    }
}
