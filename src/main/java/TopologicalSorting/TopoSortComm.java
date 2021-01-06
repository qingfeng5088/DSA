package TopologicalSorting;


import graph.Edge;
import graph.Vertex;

import java.util.LinkedList;

public class TopoSortComm<E> {

    private LinkedList<Vertex<E>> adj; // 邻接表

    public TopoSortComm() {
        adj = new LinkedList<>();
    }

    public void addVertex(E e) {
        Vertex<E> vt = get(e);
        if (vt != null) adj.remove(vt);
        adj.add(new Vertex<>(e));
    }

    public void addEdge(E s, E t) { // s先于t，边s->t
        Vertex<E> vs = get(s);
        Vertex<E> vt = get(t);

        if (vs == null) {
            vs = new Vertex<>(s);
            adj.add(vs);
        }

        if (vt == null) {
            vt = new Vertex<>(t);
            adj.add(vt);
        }

        // 入度+1
        vt.degree++;

        Edge<E> edge = new Edge<>(vt);
        vs.edgeList.add(edge);
    }

    private Vertex<E> get(E e) {
        return this.adj.stream().filter(x -> x.name.equals(e)).findFirst().orElse(null);
    }

    public void topoSortByKahn() {
        LinkedList<Vertex<E>> queue = new LinkedList<>();
        //把度为0的顶点入队列
        adj.stream().filter(x -> x.degree == 0).forEach(queue::add);

        while (!queue.isEmpty()) {
            Vertex<E> vt = queue.remove();
            System.out.print("->" + vt.name);

            vt.edgeList.forEach(x -> {
                x.endVertex.degree--;
                if (x.endVertex.degree == 0) queue.add(x.endVertex);
            });
        }

        printLoopVertex();
    }

    private void printLoopVertex() {
        System.out.println();
        if (adj.stream().filter(x -> x.degree > 0).count() > 0) {
            System.out.println("--------以下是存在于环的顶点-------");

            adj.stream().filter(x -> x.degree > 0 && x.edgeList.size() > 0).forEach(x -> {
                LinkedList<Vertex<E>> qu = new LinkedList<>();
                qu.add(x);
                Vertex<E> vl = x;
                E firstName = vl.name;
                int count = 0;
                while (!qu.isEmpty()) {
                    count++;
                    vl = qu.pop();
                    System.out.print("->" + vl.name);
                    if (count > 1 && firstName.equals(vl.name)) break;
                    vl.edgeList.forEach(y -> qu.push(y.endVertex));
                }
                System.out.println();
            });

        }
    }

    public void topoSortByDFS() {
        // 通过邻接表生成逆邻接表
        adj.stream().filter(x -> x.edgeList.size() > 0).forEach(x -> {
            x.edgeList.forEach(y -> y.endVertex.inverseEdgeList.add(new Edge<>(x)));
        });

        adj.stream().filter(x -> !x.visited).forEach(this::dsf);
    }

    private void dsf(Vertex<E> v) {
        v.visited = true;
        v.inverseEdgeList.stream().filter(x -> !x.endVertex.visited).forEach(x -> {
            x.endVertex.visited = true;
            dsf(x.endVertex);
        });
        System.out.print("->" + v.name);
    }

    public static void main(String[] args) {
        TopoSortComm<String> tpSort = new TopoSortComm<>();

        tpSort.addEdge("内裤", "裤子");
        tpSort.addEdge("内裤", "鞋子");
        tpSort.addEdge("裤子", "鞋子");
        tpSort.addEdge("领带", "外套");
        tpSort.addEdge("裤子", "腰带");
        tpSort.addEdge("袜子", "鞋子");
        tpSort.addEdge("衬衫", "外套");
        tpSort.addEdge("衬衫", "领带");


//        tpSort.addEdge("A", "B");
//        tpSort.addEdge("B", "C");
//        tpSort.addEdge("C", "D");
//        tpSort.addEdge("D", "E");
//        tpSort.addEdge("E", "F");
//        tpSort.addEdge("F", "G");
//        tpSort.addEdge("G", "H");
//        tpSort.addEdge("H", "G");
//        tpSort.addEdge("F", "E");


        System.out.println("===================Kahn遍历=================");
        tpSort.topoSortByKahn();


        System.out.println();

        System.out.println("=================深度优先遍历=================");
        tpSort.topoSortByDFS();

        System.out.println();
        System.out.println();
        System.out.println("----------------结束------------------");
    }
}
