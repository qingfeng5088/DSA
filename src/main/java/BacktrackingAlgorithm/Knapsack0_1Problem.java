package BacktrackingAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Total capacity
 */
public class Knapsack0_1Problem {
    private int C;//背包的总容量
    private int N;//物品的总各数
    public int[][] B; //B[n][c]表示n个物品在背包容量为c时的最高价值
    public int[] B2; //B[n][c]表示n个物品在背包容量为c时的最高价值
    private Goods[] goodsList;

    public Knapsack0_1Problem(int totalCapacity, int n, Goods[] goodsList) {
        this.C = totalCapacity;
        this.goodsList = goodsList;
        this.N = n;
        this.B = new int[N + 1][C + 1];
        this.B2 = new int[C + 1];
        knapsack();
    }

    public void knapsack() {
        for (int k = 1; k <= N; k++) {
            for (int c = 1; c <= C; c++) {
                if (goodsList[k - 1].weight > c) {
                    B[k][c] = B[k - 1][c];
                } else {
                    int v1 = B[k - 1][c - goodsList[k - 1].weight] + goodsList[k - 1].value;//放入该物品的价值计算
                    int v2 = B[k - 1][c];//不放该物品时，就是上个物品总数的价值
                    if (v1 > v2) {
                        B[k][c] = v1;
                    } else {
                        B[k][c] = v2;
                    }
                }
            }
        }
    }

    /**
     * 求解当前背包在n个商品选择时的最大价值
     *
     * @param n 表示前nge物品 n<=N
     * @param c 表示背包的容量
     */
    public void printResult(int n, int c) {
        for (int i = n; i >= 1; i--) {
            if (B[i][c] > B[i - 1][c]) {
                goodsList[i - 1].isAdd = true;
                c -= goodsList[i - 1].weight;
            }

        }
    }

    private static class Goods implements Comparable<Goods> {
        String name;
        int weight;
        int value;
        boolean isAdd;

        public Goods(String name, int weight, int value) {
            this.name = name;
            this.weight = weight;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getWeight() {
            return weight;
        }

        public String toString() {
            return name + ":(重量:" + weight + ",价值:" + value + ")";
        }

        @Override
        public int compareTo(Goods o) {
            return this.weight - o.weight;
        }
    }

    List<Goods> result = new ArrayList<>();//全局或成员变量,下标表示行,值表示queen存储在哪一列
    List<Goods> oldResult = new ArrayList<>();//全局或成员变量,下标表示行,值表示queen存储在哪一列
    int kinds = 0;

    public void KindsOfInstall(Goods[] goodsArr, int i) {
        for (int j = i; j < goodsArr.length; j++) {
            int w = result.stream().mapToInt(Goods::getWeight).sum();
            if ((w + goodsArr[j].getWeight()) <= C) {
                result.add(goodsArr[j]);
                KindsOfInstall(goodsArr, j + 1);
            }
        }

        int w = result.stream().mapToInt(Goods::getWeight).sum();
        System.out.println("--------------一种装包方式:第" + (++kinds) + "种---数量:" + result.size());
        result.forEach(System.out::println);
        System.out.println("--------------总重量:" + w);

        if (result.size() > 0) {
            result.remove(result.size() - 1);
            oldResult = new ArrayList<>(result);
        }

        result = new ArrayList<>(oldResult);


    }

    private boolean isSameList(List<Goods> l1, List<Goods> l2) {
        if (l1.size() != l2.size()) return false;

        for (Goods goods : l1) {
            if (!l2.contains(goods)) return false;
        }
        return true;
    }


    public int maxW = Integer.MIN_VALUE; //存储背包中物品总重量的最大值

    // cw表示当前已经装进去的物品的重量和；i表示考察到哪个物品了；
// w背包重量；items表示每个物品的重量；n表示物品个数
// 假设背包可承受重量100，物品个数10，物品重量存储在数组a中，那可以这样调用函数：
// f(0, 0, a, 10, 100)

    String str = "";

    public void f(int i, int cw, int[] items, int n, int w) {
        System.out.println("-----i:" + i + "---cw:" + cw + "---str:" + str);
        if (cw == w || i == n) { // cw==w表示装满了;i==n表示已经考察完所有的物品
            if (cw > maxW) {
                maxW = cw;
                // System.out.println("============i:" + i + "---cw:" + cw + "---str:" + str);
                str = "";
            }
            return;
        }
        str += "(-" + (i + 1) + "),";
        f(i + 1, cw, items, n, w);
        if (cw + items[i] <= w) {// 已经超过可以背包承受的重量的时候，就不要再装了
            str += (i + 1) + ",";
            f(i + 1, cw + items[i], items, n, w);
        }
    }

    public static void main(String[] args) {
        String[] goodsArr = {"手表,30,3000", "手机,60,5000", "小音箱,50,400",
                "电脑,280,6000", "电视机,350,4500", "项链,35,9000", "戒指,10,3800",
                "电子表,20,600", "电子称,200,600", "夹克,120,500", "行李箱,180,800",
                "麦克风,145,900", "空调,480,3800", "家庭影院,458,4800", "书,125,300"};
//        String[] goodsArr = {"手表,3000,3000", "手机,5000,5000", "小音箱,400,400",
//                "电脑,6000,6000", "电视机,4500,4500", "项链,9000,9000", "戒指,3800,3800",
//                "电子表,600,600", "电子称,600,600", "夹克,500,500", "行李箱,800,800",
//                "麦克风,900,900", "空调,3800,3800", "家庭影院,4800,4800", "书,300,300"};

        Goods[] goodsList = new Goods[goodsArr.length];


        for (int i = 0; i < goodsArr.length; i++) {
            String[] goodsSP = goodsArr[i].split(",");
            goodsList[i] = new Goods(goodsSP[0], Integer.parseInt(goodsSP[1]), Integer.parseInt(goodsSP[2]));
        }

        //Arrays.stream(goodsList).forEach(System.out::println);

        int currentWeight = 350;
        Knapsack0_1Problem knapsack0_1Problem = new Knapsack0_1Problem(100000, goodsList.length, goodsList);
        System.out.println("-------背包容量:" + currentWeight + " -----以下商品的组合总价值最大:" + knapsack0_1Problem.B[goodsArr.length][currentWeight]);
        knapsack0_1Problem.printResult(goodsArr.length, currentWeight);
        Arrays.stream(goodsList).filter(x -> x.isAdd).forEach(System.out::println);
        int totalWeight = Arrays.stream(goodsList).filter(x -> x.isAdd).mapToInt(x -> x.weight).sum();
        int totalValue = Arrays.stream(goodsList).filter(x -> x.isAdd).mapToInt(x -> x.value).sum();
        System.out.println("------总重量:" + totalWeight);
        System.out.println("------总价值:" + totalValue);


//        int[] a = {30, 45, 65, 20, 42, 62, 47, 75, 15, 13};
//        knapsack0_1Problem.f(0, 0, a, 10, 100);
    }
}
