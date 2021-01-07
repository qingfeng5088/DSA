package graph;

import utils.IdUtils;

import java.util.LinkedList;
import java.util.List;

public class Vertex<E> implements Cloneable {
    public String id;
    public E name;//标识标点,可以用不同类型来标识顶点如String,Integer....
    public List<Edge<E>> edgeList;//到该顶点邻接点的边,实际以java.util.LinkedList存储
    public List<Edge<E>> inverseEdgeList;//指向该顶点的边
    public double dist = Integer.MAX_VALUE; // 从起始顶点到这个顶点的距离

    public boolean visited;//标识顶点是否已访问
    public Vertex<E> previousVertex;//该顶点的前驱顶点
    public double cost;//顶点的权值,与边的权值要区别开来
    public int degree;//度，是查询顶点的？度,如：入度
    public Vertex<E> shortestPreVertex;//到这个顶点最近的前个顶点

    public Vertex() {

    }

    public Vertex(E name) {
        this.id = IdUtils.getIncreaseIdByNanoTime();
        this.name = name;
        edgeList = new LinkedList<>();
        inverseEdgeList = new LinkedList<>();
    }

    public Vertex(E name, Edge<E> edge) {
        this.id = IdUtils.getIncreaseIdByNanoTime();
        this.name = name;
        edgeList = new LinkedList<>();
        edgeList.add(edge);
    }

    public String toString() {
        return name.toString();
    }

    public Vertex<?> clone() {
        try {
            return (Vertex<?>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }
}
