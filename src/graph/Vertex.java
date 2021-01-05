package graph;

import java.util.LinkedList;
import java.util.List;

public class Vertex<E> {
    public E name;//标识标点,可以用不同类型来标识顶点如String,Integer....
    public List<Edge<E>> edgeList;//到该顶点邻接点的边,实际以java.util.LinkedList存储
    public List<Edge<E>> inverseEdgeList;//指向该顶点的边

    public boolean isVisited() {
        return visited;
    }

    public boolean visited;//标识顶点是否已访问
    public Vertex<E> previousVertex;//该顶点的前驱顶点
    public double cost;//顶点的权值,与边的权值要区别开来
    public int degree;//度，是查询顶点的？度,如：入度

    public Vertex(E name) {
        this.name = name;
        edgeList = new LinkedList<>();
        inverseEdgeList = new LinkedList<>();
    }

    public Vertex(E label, Edge<E> edge) {
        this.name = label;
        edgeList = new LinkedList<>();
        edgeList.add(edge);
    }

    public String toString() {
        return name.toString();
    }
}
