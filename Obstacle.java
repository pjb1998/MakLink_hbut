import java.util.List;

public class Obstacle {
    private List<Point> vertices; // 障碍物的顶点坐标列表

    public Obstacle(List<Point> vertices) {
        this.vertices = vertices;
    }

    // 获取障碍物的顶点坐标列表
    public List<Point> getVertices() {
        return vertices;
    }

    // 其他方法和属性...
}