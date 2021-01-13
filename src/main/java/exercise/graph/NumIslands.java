package exercise.graph;

import java.util.*;

public class NumIslands {
    /**
     * 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
     * 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
     * 此外，你可以假设该网格的四条边均被水包围。
     *
     *  来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/number-of-islands
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param grid
     * @return
     */
    public int numIslands(char[][] grid) {
        Map<String, Vertex> vertexMap = new HashMap<>();

        // 把所有‘1’的点，建立无向图
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '1') {
                    Vertex v0 = vertexMap.containsKey(i + "-" + j) ? vertexMap.get(i + "-" + j) : new Vertex(1);
                    vertexMap.put(i + "-" + j, v0);

                    // 处理右侧节点，如果有‘1’则连接右侧节点
                    if ((j + 1) < grid[i].length && grid[i][j + 1] == '1') {
                        Vertex v1 = new Vertex(1);
                        v0.adj.add(v1);
                        v1.adj.add(v0);
                        vertexMap.put(i + "-" + (j + 1), v1);
                    }

                    // 处理上一行正上方节点， 如果正上方节点为‘1’则连接正上方节点
                    if ((i - 1) >= 0 && grid[i - 1][j] == '1') {
                        Vertex v2 = vertexMap.get((i - 1) + "-" + j);
                        v0.adj.add(v2);
                        v2.adj.add(v0);
                    }
                }
            }

        }

        //BFS 广度优先遍历
        int ret = 0;
        for (Vertex vertex : vertexMap.values()) {
            if (vertex.visited) continue;

            LinkedList<Vertex> qu = new LinkedList<>();
            qu.push(vertex);
            vertex.visited = true;
            ret++; // 计数岛的个数

            Vertex temp;
            while (!qu.isEmpty()) {
                temp = qu.poll();
                for (Vertex v : temp.adj) {
                    if (v.visited) continue;

                    v.visited = true;
                    qu.push(v);
                }
            }

        }

        return ret;
    }

    private static class Vertex {
        int val;
        List<Vertex> adj = new ArrayList<>(); // 邻接表
        boolean visited;//标识顶点是否已访问

        public Vertex(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        NumIslands numIslands = new NumIslands();

        char[][] grid = {{'1', '1', '1', '1', '1'},
                {'1', '1', '0', '0', '0'},
                {'0', '1', '1', '0', '0'},
                {'0', '1', '0', '1', '1'}};

        System.out.println(numIslands.numIslands(grid));
    }

}
