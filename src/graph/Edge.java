package graph;

public class Edge<E> {
    public Vertex<E> vertex;// 终点
    public double weight;//权值

    public Edge(Vertex<E> vertex) {
        this.vertex = vertex;
    }

    public Edge(Vertex<E> vertex, double weight) {
        this.vertex = vertex;
        this.weight = weight;
    }
}
