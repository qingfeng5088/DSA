package EuclideanDistance;

import java.util.Arrays;

public class SongRecommendation {
    static String[] users = {"小邱", "小明", "小王", "小红", "小花", "小白", "小黄", "小兰"};
    static int[][] likability = {{5, 3, 3, 0, -1, 2, 5, 4, 1, -1},
            {4, 5, 2, 1, 0, 3, 2, 0, 1, 1},
            {1, 0, 5, 5, -1, 5, 0, 0, 0, 2},
            {3, 0, 0, 3, 0, 2, 0, 4, -1, -1},
            {0, 0, 0, -1, 5, -1, 5, 0, 4, 11},
            {-1, -1, -1, 0, 0, 0, 5, 5, 5, 5},
            {4, 5, 2, 1, 0, 3, 2, 2, 2, 1},
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},};

    private static String[] getUsersED(String name) {
        String[] ret = new String[users.length - 1];

        int index = 0;
        for (int i = 0; i < users.length; i++) {
            if (users[i].equals(name)) {
                index = i;
                break;
            }
        }

        int retIndex = 0;
        for (int i = 0; i < likability.length; i++) {
            if (i == index) continue;
            int sum = 0;
            for (int j = 0; j < likability[0].length - 1; j++) {
                sum += Math.pow((likability[i][j] - likability[index][j]), 2);
            }

            ret[retIndex++] =users[ i]+":" + Math.sqrt(sum);
        }

        return  ret;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(getUsersED("小明")));
    }
}
