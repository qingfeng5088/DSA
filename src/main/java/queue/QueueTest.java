package queue;

public class QueueTest {
    public static void main(String[] args) {
        CircularQueue queue = new CircularQueue(8);

        queue.enqueue("Qiu01");
        queue.enqueue("Qiu02");
        queue.enqueue("Qiu03");
        queue.enqueue("Qiu04");
        queue.enqueue("Qiu05");
        queue.enqueue("Qiu06");
        queue.enqueue("Qiu07");
        System.out.println(queue.toString());

        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.toString());
        queue.enqueue("Qiu08");
        System.out.println(queue.toString());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.toString());
        queue.enqueue("Qiu011");
        queue.enqueue("Qiu012");
        queue.enqueue("Qiu014");
        queue.enqueue("Qiu018");

        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.toString());
        queue.enqueue("Qiu035");
        System.out.println(queue.toString());
    }
}
