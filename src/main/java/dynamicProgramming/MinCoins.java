package dynamicProgramming;

import java.util.HashMap;
import java.util.Map;

public class MinCoins {
    public static int minCoins(int money) {
        if (money == 1 || money == 3 || money == 5) return 1;
        boolean[][] state = new boolean[money][money + 1];
        if (money >= 1) state[0][1] = true;
        if (money >= 3) state[0][3] = true;
        if (money >= 5) state[0][5] = true;
        for (int i = 1; i < money; i++) {
            for (int j = 1; j <= money; j++) {
                if (state[i - 1][j]) {
                    if (j + 1 <= money) state[i][j + 1] = true;
                    if (j + 3 <= money) state[i][j + 3] = true;
                    if (j + 5 <= money) state[i][j + 5] = true;
                    if (state[i][money]) return i + 1;
                }
            }
        }
        return money;
    }

    public static int countMoneyMin(int[] moneyItems, int resultMemory) {
        if (null == moneyItems || moneyItems.length < 1) {
            return -1;
        }
        if (resultMemory < 1) {
            return -1;
        }
        //  计算遍历的层数，此按最小金额来支付即为最大层数
        int levelNum = resultMemory / moneyItems[0];
        int leng = moneyItems.length;
        int[][] status = new int[levelNum][resultMemory + 1];
        //  初始化状态数组
        for (int i = 0; i < levelNum; i++) {
            for (int j = 0; j < resultMemory + 1; j++) {
                status[i][j] = -1;
            }
        }
        //  将第一层的数数据填充
        for (int i = 0; i < leng; i++) {
            status[0][moneyItems[i]] = moneyItems[i];
        }
        int minNum = -1;
        //  计算推导状态
        for (int i = 1; i < levelNum; i++) {
            //  推导出当前状态
            for (int j = 0; j < resultMemory; j++) {
                if (status[i - 1][j] != -1) {
                    //  遍历元素 , 进行累加
                    for (int k = 0; k < leng; k++) {
                        if (j + moneyItems[k] <= resultMemory) {
                            status[i][j + moneyItems[k]] = moneyItems[k];
                        }
                    }
                }
                //  找到最小的张数
                if (status[i][resultMemory] >= 0) {
                    minNum = i + 1;
                    break;
                }
            }
            if (minNum > 0) {
                break;
            }
        }
        int befValue = resultMemory;
        //  进行反推出，币的组合
        System.out.print(" 币的组合 :" );
        for (int i = minNum - 1; i >= 0; i--) {
            for (int j = resultMemory; j >= 0; j--) {
                if (j == befValue) {
                    System.out.print( status[i][j]+",");
                    befValue = befValue - status[i][j];
                    break;
                }
            }
        }
        System.out.println();
        return minNum;
    }

    static Map<Integer, Integer> dteMap = new HashMap<>();

    public static int dynamicTransferequation(int money) {
        if (money == 1) return 1;
        if (money == 2) return 2;
        if (money == 3) return 1;
        if (money == 4) return 2;
        if (money == 5) return 1;

        if (dteMap.containsKey(money)) return dteMap.get(money);

        int v1 = dynamicTransferequation(money - 1);
        int v3 = dynamicTransferequation(money - 3);
        int v5 = dynamicTransferequation(money - 5);

        int ret = Math.min(Math.min(v1, v3), v5) + 1;
        dteMap.put(money, ret);

        return ret;
    }


    public static void main(String[] args) {
        int money = 9;
        int[] moneyItems = {1, 3, 5};
        for (int i = 0; i < 99; i++) {
            money++;
            System.out.println("-----------------" + money + "元-------------------");
            System.out.println("-----状态转移表法------" + minCoins(money));
            System.out.println("-----动态转移方程法------" + dynamicTransferequation(money));
            System.out.println("-----通用求解法------" +countMoneyMin(moneyItems, money));
        }

        System.out.println("==================完成======================");
        System.out.println(dteMap);
    }


}


