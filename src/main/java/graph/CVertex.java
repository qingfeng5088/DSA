package graph;

/**
 * 适用于A* 算法的顶点
 *
 * @param <E>
 */
public class CVertex<E> extends Vertex<E> {
    public double f = Integer.MAX_VALUE; // 新增：f(i)=g(i)+h(i)
    public double x, y; // 新增：顶点在地图中的坐标（x, y）

    public CVertex(E name, double x, double y) {
        super(name);
        this.x = x;
        this.y = y;
    }
}
