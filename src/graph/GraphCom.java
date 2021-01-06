package graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 图
 * <p>
 * 广度优先搜索（ BFS ）
 * 广度优先搜索（ Breadth-First-Search ），我们平常都把简称为 BFS 。直观地讲，它其实就是一种 “ 地毯式 ” 层层推进的搜索策略，即先查找离起始顶点最近的，然后
 * 是次近的，依次往外搜索。
 *
 * @param <E>
 */
public class GraphCom<E> { // 无向图
    private final List<Vertex<E>> adj; // 邻接表

    public GraphCom() {
        adj = new ArrayList<>();
    }

    public void addEdge(E s, E t) { // 无向图一条边存两次
        Vertex<E> x;
        Vertex<E> vs = (x = get(s)) == null ? new Vertex<>(s) : x;
        Vertex<E> vt = (x = get(t)) == null ? new Vertex<>(t) : x;

        vs.edgeList.add(new Edge<>(vt));
        vt.edgeList.add(new Edge<>(vs));

        adj.add(vs);
        adj.add(vt);
    }

    private Vertex<E> get(E e) {
        return this.adj.stream().filter(x -> x.name.equals(e)).findFirst().orElse(null);
    }

    /**
     * 广度优先搜索（ BFS ）
     * 广度优先搜索（ Breadth-First-Search ），我们平常都把简称为 BFS 。直观地讲，它其实就是一种 “ 地毯式 ” 层层推进的搜索策略，即先查找离起始顶点最近的，然后
     * 是次近的，依次往外搜索。
     *
     * @param s 开始顶点的值
     * @param t 结束顶点的值
     */
    public void bfs(E s, E t) {
        if (s.equals(t)) return;
        Vertex<E> vs = get(s);

        vs.visited = true;
        Queue<Vertex<E>> queue = new LinkedList<>();
        queue.add(vs);

        while (queue.size() != 0) {
            Vertex<E> w = queue.poll();
            for (int i = 0; i < w.edgeList.size(); i++) {
                Edge<E> q = w.edgeList.get(i);
                if (!q.endVertex.visited) {
                    q.endVertex.previousVertex = w;

                    if (q.endVertex.name.equals(t)) {
                        print(vs, q.endVertex);
                        clear(s);
                        return;
                    }
                    q.endVertex.visited = true;
                    queue.add(q.endVertex);
                }
            }
        }
    }

    public void dfs(E s, E t) {
        if (s.equals(t)) return;
        Vertex<E> vs = get(s);
        Vertex<E> vt = get(t);
        if (vs == null || vt == null) return;
        vs.visited = true;

        Vertex<E> next = vs;
        while (vt != next) {
            Edge<E> nextEdge = next.edgeList.stream().filter(x -> !x.endVertex.visited).findFirst().orElse(null);
            if (nextEdge != null) {
                nextEdge.endVertex.previousVertex = next;
                next = nextEdge.endVertex;
                next.visited = true;
            } else {
                next = next.previousVertex;
            }
        }

        print(vs, vt);
        clear(s);
    }

    public void clear(E s) {
        Vertex<E> vs = get(s);
        if (vs == null) return;

        Queue<Vertex<E>> queue = new LinkedList<>();
        queue.add(vs);

        while (queue.size() != 0) {
            Vertex<E> w = queue.poll();
            for (int i = 0; i < w.edgeList.size(); i++) {
                Edge<E> q = w.edgeList.get(i);
                if (q.endVertex.visited) {
                    q.endVertex.previousVertex = null;
                    q.endVertex.degree = 0;
                    q.endVertex.visited = false;
                    queue.add(q.endVertex);
                }
            }
        }
    }


    private void print(Vertex<E> s, Vertex<E> t) { // 递归打印s->t的路径
        if (!s.name.equals(t.name)) {
            print(s, t.previousVertex);
        }

        System.out.print(t.name + " ");
    }

    public List<Vertex<E>> degreeBybfs(E s, int d) {
        List<Vertex<E>> retList = new ArrayList<>();
        Vertex<E> vs = get(s);
        if (vs == null) return null;

        Queue<Vertex<E>> queue = new LinkedList<>();
        queue.add(vs);
        while (!queue.isEmpty()) {
            vs = queue.poll();
            vs.visited = true;
            Vertex<E> finalVs = vs;

            vs.edgeList.forEach(x -> {
                if (!x.endVertex.visited) {
                    queue.add(x.endVertex);
                    x.endVertex.visited = true;

                    int de = finalVs.degree + 1;
                    x.endVertex.degree = de;
                    if (de <= d && !retList.contains(x.endVertex)) {
                        retList.add(x.endVertex);
                        x.endVertex.previousVertex = finalVs;
                    }
                }
            });
        }
        clear(s);
        return retList;
    }

    public List<Vertex<E>> degreeBydfs(E s, int d) {
        List<Vertex<E>> retList = new ArrayList<>();

        Vertex<E> vs = get(s);
        if (vs == null) return null;
        vs.visited = true;

        Vertex<E> next = vs;
        while (null != next) {
            Vertex<E> finalNext = next;
            Edge<E> nextEdge = next.edgeList.stream().filter(x -> !x.endVertex.visited || x.endVertex.degree > finalNext.degree + 1).findFirst().orElse(null);
            if (nextEdge != null) {
                nextEdge.endVertex.previousVertex = next;
                nextEdge.endVertex.degree = next.degree + 1;
                next = nextEdge.endVertex;
                next.visited = true;

                if (!retList.contains(next)) {
                    retList.add(next);
                }

                if (next.degree == d) {
                    next = next.previousVertex;
                }
            } else {
                next = next.previousVertex;
            }
        }

        // print(vs, vt);
        clear(s);
        return retList;
    }


    public static void main(String[] args) {
        GraphCom<String> gp = new GraphCom<>();
        gp.addEdge("A", "B");
        gp.addEdge("B", "C");
        gp.addEdge("B", "E");
        gp.addEdge("A", "D");
        gp.addEdge("D", "E");
        gp.addEdge("E", "F");
        gp.addEdge("E", "G");
        gp.addEdge("C", "F");
        gp.addEdge("G", "H");
        gp.addEdge("F", "H");
        gp.addEdge("C", "K");
        gp.addEdge("C", "J");
        gp.addEdge("F", "K");
        gp.addEdge("J", "K");
        gp.addEdge("K", "L");
        gp.addEdge("K", "M");
        gp.addEdge("M", "N");
        gp.addEdge("L", "N");
        gp.addEdge("N", "O");
        gp.addEdge("O", "P");
        gp.addEdge("H", "Q");
        gp.addEdge("Q", "I");
        gp.addEdge("I", "P");
        gp.addEdge("M", "P");
        gp.addEdge("G", "I");

        String s = "A", t = "P";

        System.out.println("------从" + s + "->" + t + "的广度优先遍历----");
        gp.bfs(s, t);

        System.out.println();
        System.out.println("------从" + s + "->" + t + "的深度优先遍历----");
        gp.dfs(s, t);


        int de = 4;
        System.out.println();
        System.out.println("----------求A的" + de + "度好友:--（广度优先实现）---");
        System.out.println(gp.degreeBybfs("A", de));

        System.out.println();
        System.out.println("----------求A的" + de + "度好友:--（深度优先实现）---");
        System.out.println(gp.degreeBydfs("A", de));
    }
}