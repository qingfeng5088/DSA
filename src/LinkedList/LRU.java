package LinkedList;

public class LRU {
    static final int MAXLENGHT = 10;
    static SingleChainList<String> stringSingleChainList = new SingleChainList<String>();

    public static void main(String[] args) {
        System.out.println("------创建一个单链表------list1--");
        SingleChainList<String> list1 = new SingleChainList<String>();

        list1.add("任务01");
        list1.add("任务03");
        list1.add("任务23");
        list1.add("任务23");
        list1.add("任务25");
        list1.add("任务73");
        System.out.println(list1.toString());
        System.out.println("------创建一个单链表------list2--");
        SingleChainList<String> list2 = new SingleChainList<String>();
        list2.add("任务02");
        list2.add("任务025");
        list2.add("任务09");
        list2.add("任务15");
        list2.add("任务16");
        list2.add("任务166");
        list2.add("任务19");


        System.out.println(list2.toString());

        list1.merge(list2);
        System.out.println("------合并后的结果--");
        System.out.println(list1.toString());

    }

    private static String getNew() {
        return stringSingleChainList.getFirst();

    }

    private static void addNew(String s) {
        if (stringSingleChainList.Size() >= MAXLENGHT && (stringSingleChainList.get(s) == null)) {
            stringSingleChainList.removeLast();
        }

        stringSingleChainList.addFirst(s);

        System.out.println(stringSingleChainList.toString());
    }

}

class SingleChainList<E> {
    private Node<E> first;
    private Node<E> last;
    private int size = 0;

    public void merge(SingleChainList list) {
        Node<E> thisFirst = first;
        Node<E> otherFirst = list.first;

        if (thisFirst.compareTo(otherFirst) > 0) {
            list.removeFirst();
            addFirst(otherFirst.e);
            thisFirst = first;
            otherFirst = list.first;
        }

        Node<E> pre = null;
        Node<E> next = null;


        while (thisFirst != null && otherFirst != null) {
            if (thisFirst.compareTo(otherFirst) <= 0) {
                pre = thisFirst;
                thisFirst = thisFirst.next;
            } else {
                next = otherFirst.next;
                pre.next = otherFirst;
                otherFirst.next = thisFirst;
                pre = otherFirst;
                otherFirst = next;
            }
        }

        pre.next = thisFirst == null ? otherFirst : thisFirst;
        size += list.size;
    }

    public void reversal() {
        Node<E> pre = null;
        Node<E> next = null;

        while (first != null) {
            next = first.next;
            first.next = pre;
            pre = first;
            first = next;
        }

        first = last;
    }


    public E getFirst() {
        return first.e;
    }

    public E getLast() {
        return last.e;
    }

    public Node<E> get(E e) {
        Node<E> item = first;
        while (item != null) {
            if (e.equals(item.e)) {
                return item;
            }

            item = item.next;
        }
        return null;
    }

    public int Size() {
        return size;
    }

    public void remove(E e) {
        if (size == 0) {
            return;
        }

        Node<E> item = first;
        Node<E> previous = null;
        while (item != null) {
            if (item.e.equals(e)) {
                if (item.next == null) {
                    if (previous != null) {
                        previous.next = null;
                    } else {
                        item = null;
                    }
                    size--;
                    return;
                }

                if (previous != null) {
                    previous.next = item.next;
                    size--;
                    return;
                }

                removeFirst();
                return;
            }

            previous = item;
            item = item.next;
        }
    }

    public void removeFirst() {
        if (size == 0) {
            return;
        }

        size--;
        first = first.next;
    }

    public void removeLast() {
        if (size == 0) {
            return;
        }

        size--;
        Node<E> item = first;
        if (item.e.equals(last.e)) {
            item = null;
            return;
        }

        while (item.next != null) {
            if (last.e.equals(item.next.e)) {
                last = item;
                last.next = null;
                break;
            }

            item = item.next;
        }
    }

    public String toString() {
        StringBuilder str = new StringBuilder();

        if (size == 0) {
            return str.toString();
        }

        Node<E> item = first;

        int i = 0;
        while (item != null) {
            i++;
            str.append("[").append(item.e).append("]");

            if (i < size) {
                str.append("->");
            }

            item = item.next;
        }

        return str.toString();
    }

    public void add(E e) {
        Node<E> item = get(e);
        if (item != null) {
            remove(e);
        }

        addLast(e);
    }

    public void addFirst(E e) {
        remove(e);

        size++;
        first = new Node<E>(e, first);
    }

    public void addLast(E e) {
        remove(e);

        last = new Node<E>(e, null);
        size++;
        if (first == null) {
            first = last;
            return;
        }

        Node<E> n = first;
        while (n.next != null) {
            n = n.next;
        }
        n.next = last;
    }


    static class Node<E> implements Comparable<Node<E>> {
        private E e;
        private Node<E> next;

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

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        @Override
        public int compareTo(Node<E> o) {
            return ((String) this.e).compareTo((String) o.e);
        }
    }


}


