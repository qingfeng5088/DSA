package graph;

import java.util.LinkedList;
import java.util.Queue;

public class Graph { // 无向图
    private final int v; // 顶点的个数
    private final LinkedList<Integer>[] adj; // 邻接表

    public Graph(int v) {
        this.v = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i) {
            adj[i] = new LinkedList<>();
        }
    }

    public void addEdge(int s, int t) { // 无向图一条边存两次
        adj[s].add(t);
        adj[t].add(s);
    }

    public void bfs(int s, int t) {
        if (s == t) return;
        boolean[] visited = new boolean[v];
        visited[s] = true;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        int[] prev = new int[v];
        for (int i = 0; i < v; ++i) {
            prev[i] = -1;
        }
        while (queue.size() != 0) {
            int w = queue.poll();
            for (int i = 0; i < adj[w].size(); ++i) {
                int q = adj[w].get(i);
                if (!visited[q]) {
                    prev[q] = w;
                    if (q == t) {
                        print(prev, s, t);
                        return;
                    }
                    visited[q] = true;
                    queue.add(q);
                }
            }
        }
    }

    private void print(int[] prev, int s, int t) { // 递归打印s->t的路径
        if (prev[t] != -1 && t != s) {
            print(prev, s, prev[t]);
        }
        System.out.print(t + " ");
    }

    public static void main(String[] args) {
        Graph gp = new Graph(30);
        gp.addEdge(0, 1);
        gp.addEdge(0, 3);
        gp.addEdge(1, 2);
        gp.addEdge(1, 4);
        gp.addEdge(2, 5);
        gp.addEdge(2, 11);
        gp.addEdge(2, 13);
        gp.addEdge(3, 4);
        gp.addEdge(4, 5);
        gp.addEdge(4, 6);
        gp.addEdge(5, 7);
        gp.addEdge(6, 7);
        gp.addEdge(7, 8);
        gp.addEdge(7, 9);
        gp.addEdge(8, 10);
        gp.addEdge(9, 10);
        gp.addEdge(9, 11);
        gp.addEdge(11, 12);
        gp.addEdge(11, 15);
        gp.addEdge(12, 13);
        gp.addEdge(12, 14);
        gp.addEdge(13, 2);
        gp.addEdge(13, 12);
        gp.addEdge(14, 12);
        gp.addEdge(14, 15);
        gp.addEdge(15, 16);
        gp.addEdge(15, 17);
        gp.addEdge(16, 18);
        gp.addEdge(16, 19);
        gp.addEdge(17, 18);
        gp.addEdge(17, 28);


        gp.bfs(0, 18);
    }
}