package greedyAlgorithm;

import utils.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 假设有n个人等待被服务，但是服务窗口只有一个，每个人需要被服务的时间长度是不同的，如何安排被服务的先后顺序，才能让这n个人总的等待时间最
 * 短？
 */
public class WaitingInLine {
    private static class Customer implements Comparable<Customer> {
        String name;
        int handingTime;

        public Customer(String name, int handingTime) {
            this.name = name;
            this.handingTime = handingTime;
        }


        @Override
        public int compareTo(Customer o) {
            return this.handingTime - o.handingTime;
        }

        public String toString() {
            return name + ":" + handingTime;
        }
    }

    public static void main(String[] args) {
        String[] names = {"彤彤", "晨晨", "轩轩", "苗苗", "巧巧", "书艺"};
        List<Customer> cus = new ArrayList<>();
        for (String name : names) {
            cus.add(new Customer(name, Utils.getRandomInt(10)));
        }

        PriorityQueue<Customer> queue = new PriorityQueue<>();
        for (Customer customer : cus) {
            queue.offer(customer);
        }

        System.out.println("----------------优先队列处理的情况-------------------");
        int allTime = 0;
        while (!queue.isEmpty()) {
            Customer customer = queue.poll();
            int waitTime = customer.handingTime * (queue.size());
            System.out.println("正在服务:" + customer.name + " 需要等待:" + customer.handingTime + "分钟， 其他人需要等待" + waitTime + "分钟");
            allTime += waitTime;

        }
        System.out.println("=====总的等待时间:" + allTime + "分钟=============");

        allTime = 0;
        System.out.println("----------------随机排队的情况-------------------");
        int count = cus.size();
        for (Customer customer : cus) {
            int waitTime = customer.handingTime * (--count);
            System.out.println("正在服务:" + customer.name + " 需要等待:" + customer.handingTime + "分钟， 其他人需要等待" + waitTime + "分钟");
            allTime += waitTime;
        }

        System.out.println("=====随机排队总的等待时间:" + allTime + "分钟=============");

    }
}
