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
        Vertex<E> vs = (x = get(s)) == null ? new Vertex<E>(s) : x;
        Vertex<E> vt = (x = get(t)) == null ? new Vertex<E>(t) : x;

        vs.edgeList.add(new Edge<>(vt));
        vt.edgeList.add(new Edge<>(vs));

        adj.add(vs);
        adj.add(vt);
    }

    private Vertex<E> get(E e) {
        return this.adj.stream().filter(x -> x.label.equals(e)).findFirst().orElse(null);
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
                if (!q.vertex.visited) {
                    q.vertex.previousVertex = w;

                    if (q.vertex.label.equals(t)) {
                        print(vs, q.vertex);
                        clear(s);
                        return;
                    }
                    q.vertex.visited = true;
                    queue.add(q.vertex);
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
            Edge<E> nextEdge = next.edgeList.stream().filter(x -> !x.vertex.visited).findFirst().orElse(null);
            if (nextEdge != null) {
                nextEdge.vertex.previousVertex = next;
                next = nextEdge.vertex;
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
                if (q.vertex.visited) {
                    q.vertex.previousVertex = null;
                    q.vertex.degree = 0;
                    q.vertex.visited = false;
                    queue.add(q.vertex);
                }
            }
        }
    }


    private void print(Vertex<E> s, Vertex<E> t) { // 递归打印s->t的路径
        if (!s.label.equals(t.label)) {
            print(s, t.previousVertex);
        }

        System.out.print(t.label + " ");
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
                if (!x.vertex.visited) {
                    queue.add(x.vertex);
                    x.vertex.visited = true;

                    int de = finalVs.degree + 1;
                    x.vertex.degree = de;
                    if (de <= d && !retList.contains(x.vertex)) {
                        retList.add(x.vertex);
                        x.vertex.previousVertex = finalVs;
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
            Edge<E> nextEdge = next.edgeList.stream().filter(x -> !x.vertex.visited || x.vertex.degree > finalNext.degree + 1).findFirst().orElse(null);
            if (nextEdge != null) {
                nextEdge.vertex.previousVertex = next;
                nextEdge.vertex.degree = next.degree + 1;
                next = nextEdge.vertex;
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

    static class Vertex<E> {
        E label;//标识标点,可以用不同类型来标识顶点如String,Integer....
        List<Edge<E>> edgeList;//到该顶点邻接点的边,实际以java.util.LinkedList存储
        boolean visited;//标识顶点是否已访问
        Vertex<E> previousVertex;//该顶点的前驱顶点
        double cost;//顶点的权值,与边的权值要区别开来
        int degree;//度，是查询顶点的？度

        Vertex(E label) {
            this.label = label;
            edgeList = new LinkedList<>();
        }

        Vertex(E label, Edge<E> edge) {
            this.label = label;
            edgeList = new LinkedList<>();
            edgeList.add(edge);
        }

        public String toString() {
            return label.toString();
        }
    }

    static class Edge<E> {
        Vertex<E> vertex;// 终点
        double weight;//权值

        public Edge(Vertex<E> vertex) {
            this.vertex = vertex;
        }

        public Edge(Vertex<E> vertex, double weight) {
            this.vertex = vertex;
            this.weight = weight;
        }
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

        String s = "A", t = "I";

        System.out.println("------从" + s + "->" + t + "的广度优先遍历----");
        gp.bfs(s, t);

        System.out.println();
        System.out.println("------从" + s + "->" + t + "的深度优先遍历----");
        gp.dfs(s, t);


        int de = 5;
        System.out.println();
        System.out.println("----------求A的" + de + "度好友:--（广度优先实现）---");
        System.out.println(gp.degreeBybfs("A", de));

        System.out.println();
        System.out.println("----------求A的" + de + "度好友:--（深度优先实现）---");
        System.out.println(gp.degreeBydfs("A", de));
    }
}