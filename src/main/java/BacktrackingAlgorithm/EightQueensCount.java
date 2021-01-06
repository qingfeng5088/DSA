package BacktrackingAlgorithm;

public class EightQueensCount {
    int queensCount;
    int[] result;//全局或成员变量,下标表示行,值表示queen存储在哪一列
    int modifyCount;

    public EightQueensCount(int queensCount) {
        this.queensCount = queensCount;
        result = new int[queensCount];
    }


    public void cal8queens(int row) { // 调用方式：cal8queens(0);
        if (row == queensCount) { // 8个棋子都放置好了，打印结果
            printQueens(result);
            return; // 8行棋子都放好了，已经没法再往下递归了，所以就return
        }

        modifyCount += queensCount;
        for (int column = 0; column < queensCount; ++column) { // 每一行都有8中放法
            if (isOk(row, column)) { // 有些放法不满足要求
                result[row] = column; // 第row行的棋子放到了column列
                cal8queens(row + 1); // 考察下一行
            }
        }
    }

    private boolean isOk(int row, int column) {//判断row行column列放置是否合适
        int leftup = column - 1, rightup = column + 1;
        for (int i = row - 1; i >= 0; --i) { // 逐行往上考察每一行
            modifyCount += 5;
            if (result[i] == column) return false; // 第i行的column列有棋子吗？
            if (leftup >= 0) { // 考察左上对角线：第i行leftup列有棋子吗？
                if (result[i] == leftup) return false;
            }
            if (rightup < queensCount) { // 考察右上对角线：第i行rightup列有棋子吗？
                if (result[i] == rightup) return false;
            }
            --leftup;
            ++rightup;
        }
        return true;
    }

    private void printQueens(int[] result) { // 打印出一个二维矩阵
        modifyCount += queensCount * queensCount;
        for (int row = 0; row < queensCount; ++row) {
            for (int column = 0; column < queensCount; ++column) {
                if (result[row] == column) System.out.print("Q ");
                else System.out.print("* ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int count = 8;
        EightQueensCount eightQueens = new EightQueensCount(count);
        eightQueens.cal8queens(0);

        System.out.println("执行次数:" + eightQueens.modifyCount);
        System.out.println("阶乘结果:" + factorial(count));
        System.out.println(" 幂结果:" + Math.pow(count,count));
    }

    public static long factorial(long number) {
        if (number <= 1)
            return 1;
        else
            return number * factorial(number - 1);
    }
}
