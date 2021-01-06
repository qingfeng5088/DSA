package graph;

import java.util.*;

/**
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
public class ShortestPath<E> {// 有向有权图的邻接表表示
    private final List<Vertex<E>> adj; // 邻接表

    public ShortestPath() {
        this.adj = new ArrayList<>();
    }

    public void addEdge(E s, E t, double weight) {
        Vertex<E> x;
        Vertex<E> vs = (x = get(s)) == null ? new Vertex<>(s) : x;
        Vertex<E> vt = (x = get(t)) == null ? new Vertex<>(t) : x;

        vs.edgeList.add(new Edge<>(vs, vt, weight));
        vt.edgeList.add(new Edge<>(vt, vs, weight));

        adj.add(vs);
        adj.add(vt);
    }

    public void addEdge(E s, E t, double weight, String info) {
        Vertex<E> x;
        Vertex<E> vs = (x = get(s)) == null ? new Vertex<>(s) : x;
        Vertex<E> vt = (x = get(t)) == null ? new Vertex<>(t) : x;

        vs.edgeList.add(new Edge<>(vs, vt, weight, info));
        vt.edgeList.add(new Edge<>(vt, vs, weight, info));

        adj.add(vs);
        adj.add(vt);
    }

    private Vertex<E> get(E e) {
        return this.adj.stream().filter(x -> x.name.equals(e)).findFirst().orElse(null);
    }

    public void dijkstra(E s, E t) {
        Vertex<E> vs = get(s);
        Vertex<E> vt = get(t);
        if (vs == null || vt == null) return;

        // 以顶点的最小距离构建小顶堆
        PriorityQueue<Vertex<E>> queue = new PriorityQueue<>(this::compare);
        vs.dist = 0;
        vs.visited = true;
        queue.add(vs);

        Vertex<E> vtem;
        while (!queue.isEmpty()) {
            vtem = queue.poll();
            if (vtem.id.equals(vt.id)) break;// 最短路径产生了

            Vertex<E> finalVtem = vtem;
            vtem.edgeList.forEach(x -> {
                Vertex<E> next = x.endVertex;
                if (finalVtem.dist + x.weight < next.dist) {
                    next.dist = finalVtem.dist + x.weight;
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

    private void printShortestPath(Vertex<E> vs, Vertex<E> vt) {
        Stack<Vertex<E>> stack = new Stack<>();
        stack.push(vt);
        Vertex<E> vtem = vt;
        while (vtem.shortestPreVertex.id != vs.id) {
            stack.push(vtem.shortestPreVertex);
            vtem = vtem.shortestPreVertex;
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

    private int compare(Vertex<E> x, Vertex<E> y) {
        return (int) (x.dist - y.dist);
    }

    public static void main(String[] args) {
        ShortestPath<String> shortestPath = new ShortestPath<>();
        shortestPathDemo(shortestPath);
        System.out.println("=====================================================");
        System.out.println();
        mixedTravelDemo(shortestPath);
    }

    private static void mixedTravelDemo(ShortestPath<String> shortestPath) {
        shortestPath.addEdge("家", "公交站A", 7, "公交");
        shortestPath.addEdge("家", "地铁A", 10, "地铁");
        shortestPath.addEdge("公交站A", "地铁A", 2.5, "步行");
        shortestPath.addEdge("公交站A", "公交站C", 17.5, "公交(换乘1.5分钟)");

        shortestPath.addEdge("地铁A", "地铁B", 21, "地铁");
        shortestPath.addEdge("地铁A", "地铁D", 16, "地铁");

        shortestPath.addEdge("公交站C", "地铁B", 4.8, "步行");
        shortestPath.addEdge("公交站C", "公交站B", 6, "公交");
        shortestPath.addEdge("公交站C", "公交站F", 14.5, "公交(换乘1.5分钟)");

        shortestPath.addEdge("地铁D", "地铁C", 8.5, "地铁");
        shortestPath.addEdge("地铁D", "地铁F", 9.8, "地铁");
        shortestPath.addEdge("地铁D", "公交站D", 3, "步行");

        shortestPath.addEdge("公交站B", "地铁B", 6, "步行");
        shortestPath.addEdge("公交站B", "公交站D", 6.5, "公交");
        shortestPath.addEdge("公交站B", "公交站E", 5.5, "公交");


        shortestPath.addEdge("公交站D", "公交站E", 7.5, "公交");
        shortestPath.addEdge("公交站D", "地铁F", 9.5, "步行");

        shortestPath.addEdge("地铁B", "公交站E", 5, "步行");
        shortestPath.addEdge("地铁B", "地铁G", 14.5, "地铁");

        shortestPath.addEdge("公交站E", "地铁F", 6, "步行");
        shortestPath.addEdge("公交站E", "地铁G", 15, "步行");

        shortestPath.addEdge("地铁C", "地铁F", 10, "地铁");

        shortestPath.addEdge("地铁F", "地铁G", 7, "地铁");

        shortestPath.addEdge("公交站F", "地铁G", 1.5, "步行(1.5分钟)");
        shortestPath.addEdge("公交站F", "公司", 8, "步行");

        shortestPath.addEdge("地铁G", "公司", 5, "步行(5分钟)");

        // 执行最短时间路径算法
        shortestPath.dijkstra("家", "公司");
    }

    private static void shortestPathDemo(ShortestPath<String> shortestPath) {
        shortestPath.addEdge("济南", "聊城", 5);
        shortestPath.addEdge("济南", "泰安", 2);
        shortestPath.addEdge("济南", "济宁", 9);
        shortestPath.addEdge("济南", "淄博", 4.5);
        shortestPath.addEdge("泰安", "济宁", 7.5);
        shortestPath.addEdge("泰安", "淄博", 6);
        shortestPath.addEdge("泰安", "聊城", 4);
        shortestPath.addEdge("济宁", "菏泽", 4);
        shortestPath.addEdge("济宁", "枣庄", 5);
        shortestPath.addEdge("济宁", "泗水", 3.5);
        shortestPath.addEdge("泗水", "临沂", 3);
        shortestPath.addEdge("枣庄", "临沂", 4.5);
        shortestPath.addEdge("泰安", "泗水", 8);
        shortestPath.addEdge("泰安", "曲阜", 5);
        shortestPath.addEdge("曲阜", "枣庄", 4);
        //  shortestPath.addEdge("济南", "临沂", 12);


        shortestPath.dijkstra("济南", "枣庄");
    }
}
