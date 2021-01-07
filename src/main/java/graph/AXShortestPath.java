package graph;

import java.util.*;

/**
 * A*算法是对Dijkstra算法的优化和改造
 * <p>
 * 最短路径：地图软件是如何计算出最优出行路径的？
 * 基础篇的时候，我们学习了图的两种搜索算法，深度优先搜索和广度优先搜索。这两种算法主要是针对无权图的搜索算法。针对有权图，也就是图中的每条边都
 * 有一个权重，我们该如何计算两点之间的最短路径（经过的边的权重和最小）呢？今天，我就从地图软件的路线规划问题讲起，带你看看常用的最短路径算
 * 法（Shortest Path Algorithm）。
 * 像 Google 地图、百度地图、高德地图这样的地图软件，我想你应该经常使用吧？如果想从家开车到公司，你只需要输入起始、结束地址，地图就会给你规划一条
 * 最优出行路线。这里的最优，有很多种定义，比如最短路线、最少用时路线、最少红绿灯路线等等。作为一名软件开发工程师，你是否思考过，地图软件的最优
 * 路线是如何计算出来的吗？底层依赖了什么算法呢？
 * <p>
 * 显然，把地图抽象成图最合适不过了。我们把每个岔路口看作一个顶点，岔路口与岔路口之间的路看作一
 * 条边，路的长度就是边的权重。如果路是单行道，我们就在两个顶点之间画一条有向边；如果路是双行道，我们就在两个顶点之间画两条方向不同的边。这样，
 * 整个地图就被抽象成一个有向有权图。
 */
public class AXShortestPath<E> {// 有向有权图的邻接表表示
    private final List<CVertex<E>> adj; // 邻接表

    public AXShortestPath() {
        this.adj = new ArrayList<>();
    }

    public void addEdge(E s, int x1, int y1, E t, int x2, int y2, double weight) {
        CVertex<E> x;
        CVertex<E> vs = (x = get(s)) == null ? new CVertex<>(s, x1, y1) : x;
        CVertex<E> vt = (x = get(t)) == null ? new CVertex<>(t, x2, y2) : x;

        vs.edgeList.add(new Edge<>(vs, vt, weight));
        vt.edgeList.add(new Edge<>(vt, vs, weight));

        adj.add(vs);
        adj.add(vt);
    }

    public void addEdge(E s, int x1, int y1, E t, int x2, int y2, double weight, String info) {
        CVertex<E> x;
        CVertex<E> vs = (x = get(s)) == null ? new CVertex<>(s, x1, y1) : x;
        CVertex<E> vt = (x = get(t)) == null ? new CVertex<>(t, x2, y2) : x;

        vs.edgeList.add(new Edge<>(vs, vt, weight, info));
        vt.edgeList.add(new Edge<>(vt, vs, weight, info));

        adj.add(vs);
        adj.add(vt);
    }

    private CVertex<E> get(E e) {
        return this.adj.stream().filter(x -> x.name.equals(e)).findFirst().orElse(null);
    }

    public void axing(E s, E t) {
        CVertex<E> vs = get(s);
        CVertex<E> vt = get(t);
        if (vs == null || vt == null) return;

        // 以顶点的最小距离构建小顶堆
        PriorityQueue<CVertex<E>> queue = new PriorityQueue<>(this::compare);
        vs.dist = 0;
        vs.f = 0;
        vs.visited = true;
        queue.add(vs);

        CVertex<E> vtem;
        while (!queue.isEmpty()) {
            vtem = queue.poll();
            if (vtem.id.equals(vt.id)) break;// 最短路径产生了

            CVertex<E> finalVtem = vtem;
            vtem.edgeList.forEach(x -> {
                CVertex<E> next = (CVertex<E>) x.endVertex;
                if (finalVtem.dist + x.weight < next.dist) {
                    next.dist = finalVtem.dist + x.weight;
                    next.f = next.dist + hManhattan(next, vt);
                    next.shortestPreVertex = finalVtem;//记录当前最近的上一个顶点

                    if (next.visited) {
                        // 如果已经入队处理过，更新队列，按最新的路径距离 重新排序小顶堆
                        queue.remove(next);
                        queue.add(next);
                    } else {
                        queue.add(next);
                        next.visited = true;
                    }
                }
            });
        }
        // 输出最短路径
        printShortestPath(vs, vt);
    }

    private void printShortestPath(CVertex<E> vs, CVertex<E> vt) {
        Stack<CVertex<E>> stack = new Stack<>();
        stack.push(vt);
        CVertex<E> vtem = vt;
        while (!vtem.shortestPreVertex.id.equals(vs.id)) {
            stack.push((CVertex<E>) vtem.shortestPreVertex);
            vtem = (CVertex<E>) vtem.shortestPreVertex;
        }
        stack.push(vs);

        System.out.println("---------------------从[" + vs.name + "]到[" + vt.name + "]的最短路径------------");
        while (!stack.isEmpty()) {
            vtem = stack.pop();

            String edgeInfo = "";
            if (!stack.isEmpty()) {
                edgeInfo = Objects.requireNonNull(vtem.edgeList.stream().filter(x -> x.endVertex == stack.peek()).findFirst().orElse(null)).info;
            }

            System.out.print("-->" + vtem.name + "(" + vtem.dist + ")" + ((edgeInfo != null && !edgeInfo.isEmpty()) ? "--[" + edgeInfo + "]" : ""));
        }
        System.out.println();
        System.out.println("最短距离:" + vt.dist);
        System.out.println("---------------------打印结束-----------------------");
    }

    private int compare(CVertex<E> x, CVertex<E> y) {
        if (x.f > y.f) {
            return 1;
        } else if (x.f == y.f) {
            return 0;
        } else {
            return -1;
        }
    }

    double hManhattan(CVertex<E> v1, CVertex<E> v2) { // Vertex表示顶点，后面有定义
        return Math.abs(v1.x - v2.x) + Math.abs(v1.y - v2.y);
    }

    public static void main(String[] args) {
        AXShortestPath<String> shortestPath = new AXShortestPath<>();
        shortestPathDemo(shortestPath);
        System.out.println("=====================================================");
        System.out.println();
    }

    private static void shortestPathDemo(AXShortestPath<String> shortestPath) {
        shortestPath.addEdge("济南", 0, 10, "聊城", -5, 9, 5);
        shortestPath.addEdge("济南", 0, 10, "济宁", -2, 3, 9);
        shortestPath.addEdge("济南", 0, 10, "泰安", -1, 8, 2);
        shortestPath.addEdge("济南", 0, 10, "淄博", 4, 11, 4.5);
        shortestPath.addEdge("济南", 0, 10, "东平", 0, 7, 3.2);
        shortestPath.addEdge("东平", 0, 7, "曲阜", 0, 4, 3);
        shortestPath.addEdge("泰安", -1, 8, "济宁", -2, 3, 7.5);
        shortestPath.addEdge("泰安", -1, 8, "淄博", 4, 11, 6);
        shortestPath.addEdge("泰安", -1, 8, "聊城", -5, 9, 4);
        shortestPath.addEdge("济宁", -2, 3, "菏泽", 6, 1, 4);
        shortestPath.addEdge("济宁", -2, 3, "枣庄", 0, -1, 5);
        shortestPath.addEdge("济宁", -2, 3, "泗水", 3, 2, 3.5);
        shortestPath.addEdge("泗水", 3, 2, "临沂", 5, 0, 3);
        shortestPath.addEdge("枣庄", 0, -1, "临沂", 5, 0, 4.5);
        shortestPath.addEdge("泰安", -1, 8, "泗水", 3, 2, 8);
        shortestPath.addEdge("泰安", -1, 8, "曲阜", 0, 4, 5);
        shortestPath.addEdge("曲阜", 0, 4, "枣庄", 0, -1, 4);


        shortestPath.axing("济南", "枣庄");
    }
}
