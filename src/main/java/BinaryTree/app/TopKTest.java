package BinaryTree.app;

import utils.Utils;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.concurrent.ArrayBlockingQueue;

public class TopKTest {
    private static ArrayBlockingQueue<HotWord> queue = new ArrayBlockingQueue<>(100);
    private static String[] HotKeyword = {"逆行者", "后浪", "凡尔赛文学", "打工人", "地摊经济", "直播带货",
            "耗子尾汁", "集美", "网抑云", "社会性死亡", "秋天的第一杯奶茶", "生而为人我很抱歉", "干饭人", "一起爬山吗",
            "黑人抬棺", "淡黄的长裙", "职场PUA", "不讲武德", "干啥啥不行，xx第一名", "捞五条人", "脚艺人", "迪斯尼在逃公主",
            "尾款人", "我还有机会吗", "工具人", "直呼内行", "总裁，夫人肯认错了"};

    private static HotWord[] HotWordArr = new HotWord[HotKeyword.length];

    private static HashMap<HotWord, Integer> kw = new HashMap<>();

    private static PriorityQueue<HotWord> heap = new PriorityQueue<>(5);

    private static int allCount = 0;

    public static void main(String[] args) {
        for (int i = 0; i < HotKeyword.length; i++) {
            HotWordArr[i] = new HotWord(HotKeyword[i]);
        }

        Thread produce = new Thread(() -> {
            while (true) {
                try {
                    HotWord keyword = HotWordArr[Utils.getRandomInt(HotKeyword.length)];
                    kw.merge(keyword, 1, Integer::sum);
                    queue.put(keyword);
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Produce");
        produce.start();

        Thread consumer = new Thread(() -> {
            while (true) {
                try {
                    HotWord keyword = queue.take();
                    if (heap.contains(keyword)) {
                        continue;
                    }

                    int count = kw.get(keyword);
                    keyword.count = count;
                    if (heap.size() < 5) {
                        heap.offer(keyword);
                        continue;
                    }

                    HotWord topKeyword = heap.peek();
                    if (count > topKeyword.count) {
                        heap.poll();
                        heap.offer(keyword);
                        System.out.println("-------------Top5热词:----" + heap.toString() + "堆大小:" + heap.size() + "最热词:" + topKeyword);
                    }

                    allCount++;
                    if (allCount == 1000) {
                        System.out.println("-----------------------------打印整个堆------------------------------");
                        for (int i = 0; i < 5; i++) {
                            System.out.print(heap.poll() + " | ");
                        }
                        System.out.println();
                        System.out.println("-----------------------------打印整个热词列表--------------------------");
                        kw.keySet().stream().sorted().forEach(System.out::println);

                        return;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "consumer");

        consumer.run();
    }
}

class HotWord implements Comparable<HotWord> {
    String name;
    int count;

    public HotWord(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(HotWord o) {
        return this.count - o.count;
    }

    public String toString() {
        return name + ":" + count;
    }
}
