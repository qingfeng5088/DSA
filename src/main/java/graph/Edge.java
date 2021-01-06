package graph;

public class Edge<E> {
    public Vertex<E> startVertex;// 终点
    public Vertex<E> endVertex;// 终点
    public double weight;//权值
    public String info;//边的相关信息

    public Edge(Vertex<E> endVertex) {
        this.endVertex = endVertex;
    }

    public Edge(Vertex<E> startVertex, Vertex<E> endVertex) {
        this.startVertex = startVertex;
        this.endVertex = endVertex;
    }

    public Edge(Vertex<E> startVertex, Vertex<E> endVertex, double weight) {
        this.startVertex = startVertex;
        this.endVertex = endVertex;
        this.weight = weight;
    }

    public Edge(Vertex<E> startVertex, Vertex<E> endVertex, double weight, String info) {
        this.startVertex = startVertex;
        this.endVertex = endVertex;
        this.weight = weight;
        this.info = info;
    }

    public Edge(Vertex<E> vertex, double weight) {
        this.endVertex = vertex;
        this.weight = weight;
    }
}
