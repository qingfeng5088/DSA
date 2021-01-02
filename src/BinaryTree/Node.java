package BinaryTree;

public class Node<E> {
    private E data;
    private Node<E> father;
    private Node<E> left;
    private Node<E> right;
    private boolean isLeft;
    String humffmanCode = "";

    public Node(E data, Node<E> left, Node<E> right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }

    public Node(E data, Node<E> left, Node<E> right, Node<E> father) {
        this.data = data;
        this.left = left;
        this.right = right;
        this.father = father;
    }

    public E getData() {
        return data;
    }

    public Node<E> getFather() {
        return father;
    }

    public void setFather(Node<E> father) {
        this.father = father;
    }

    public void setData(E data) {
        this.data = data;
    }

    public Node<E> getLeft() {
        return left;
    }

    public void setLeft(Node<E> left) {
        this.left = left;
    }

    public Node<E> getRight() {
        return right;
    }

    public void setRight(Node<E> right) {
        this.right = right;
    }

    public boolean isLeft() {
        return father != null && father.left == this;
    }
}
