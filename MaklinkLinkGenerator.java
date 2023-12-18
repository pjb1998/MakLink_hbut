import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MaklinkLinkGenerator {
    private static List<LinkLine> linkLines = new ArrayList<>();        //所有连接线合集
    public static Obstacle createObstacle(List<Point> vertices){
        return new Obstacle(vertices);
    }
    public static void generateLinkLines(List<Obstacle> obstacles, List<LinkLine> linkLines) {
        Set<LinkLine> uniqueLinkLines = new HashSet<>();

        for (int i = 0; i < obstacles.size() - 1; i++) {
            Obstacle obstacle1 = obstacles.get(i);
            List<Point> vertices1 = obstacle1.getVertices();

            for (int j = i + 1; j < obstacles.size(); j++) {
                Obstacle obstacle2 = obstacles.get(j);
                List<Point> vertices2 = obstacle2.getVertices();

                for (Point vertex1 : vertices1) {
                    for (Point vertex2 : vertices2) {
                        LinkLine linkLine = new LinkLine(vertex1, vertex2);
                        uniqueLinkLines.add(linkLine);
                    }
                }
            }
        }

        linkLines.addAll(uniqueLinkLines);
    }
    public static void generateMapLinkLines(List<Obstacle> obstacles, List<LinkLine> linkLines) {
        // 定义地图边界的顶点
        Point mapVertex1 = new Point(0, 0);
        Point mapVertex2 = new Point(200, 0);
        Point mapVertex3 = new Point(200, 200);
        Point mapVertex4 = new Point(0, 200);

        for (Obstacle obstacle : obstacles) {
            List<Point> vertices = obstacle.getVertices();

            for (Point vertex : vertices) {
                // 生成与地图上边界的连线
                LinkLine topLinkLine = new LinkLine(vertex, new Point(vertex.getX(), mapVertex1.getY()));
                linkLines.add(topLinkLine);

                // 生成与地图下边界的连线
                LinkLine bottomLinkLine = new LinkLine(vertex, new Point(vertex.getX(), mapVertex3.getY()));
                linkLines.add(bottomLinkLine);

                // 生成与地图左边界的连线
                LinkLine leftLinkLine = new LinkLine(vertex, new Point(mapVertex4.getX(), vertex.getY()));
                linkLines.add(leftLinkLine);

                // 生成与地图右边界的连线
                LinkLine rightLinkLine = new LinkLine(vertex, new Point(mapVertex2.getX(), vertex.getY()));
                linkLines.add(rightLinkLine);
            }
        }
    }

    public static void printLinkLines(List<LinkLine> linkLines) {
        for (LinkLine linkLine : linkLines) {
//            System.out.println("Link Line: " + "("+linkLine.getStartPoint().getX()+","+ linkLine.getStartPoint().getY()+")" + " -> " + "("+linkLine.getEndPoint().getX()+","+ linkLine.getEndPoint().getY()+")");
            System.out.println(linkLine.getStartPoint().getX()+" "+linkLine.getStartPoint().getY()+"\n"+linkLine.getEndPoint().getX()+" "+linkLine.getEndPoint().getY());
        }
    }

    public static List<LinkLine> removeDuplicateLinkLines(List<LinkLine> linkLines) {
        Set<Pair<Point, Point>> pointPairs = new HashSet<>();
        List<LinkLine> uniqueLinkLines = new ArrayList<>();

        for (LinkLine linkLine : linkLines) {
            Pair<Point, Point> pointPair = new Pair<>(linkLine.getStartPoint(), linkLine.getEndPoint());

            if (!pointPairs.contains(pointPair)) {
                pointPairs.add(pointPair);
                uniqueLinkLines.add(linkLine);
            }
        }

        return uniqueLinkLines;
    }
    public static void main(String[] args) {
        List<Obstacle> obstacles = new ArrayList<>();   //障碍物合集

        // 多边形障碍物的顶点列表-->菱形1
        List<Point> obstaclePoints1 = new ArrayList<>();
        obstaclePoints1.add(new Point(40, 140));
        obstaclePoints1.add(new Point(60, 160));
        obstaclePoints1.add(new Point(100, 40));
        obstaclePoints1.add(new Point(60, 120));
        Obstacle obstacleOne = createObstacle(obstaclePoints1);
        obstacles.add(obstacleOne);     //加入合集

        // 多边形障碍物的顶点列表-->菱形2
        List<Point> obstaclePoints2 = new ArrayList<>();
        obstaclePoints2.add(new Point(50, 30));
        obstaclePoints2.add(new Point(30, 40));
        obstaclePoints2.add(new Point(80, 80));
        obstaclePoints2.add(new Point(100, 40));
        Obstacle obstacleTwo = createObstacle(obstaclePoints2);
        obstacles.add(obstacleTwo);     //加入合集

        // 多边形障碍物的顶点列表-->菱形3
        List<Point> obstaclePoints3 = new ArrayList<>();
        obstaclePoints3.add(new Point(120, 160));
        obstaclePoints3.add(new Point(140, 100));
        obstaclePoints3.add(new Point(180, 170));
        obstaclePoints3.add(new Point(165, 180));
        Obstacle obstacleThree = createObstacle(obstaclePoints3);
        obstacles.add(obstacleThree);     //加入合集

        // 多边形障碍物的顶点列表-->三角形
        List<Point> obstaclePoints4 = new ArrayList<>();
        obstaclePoints4.add(new Point(120, 40));
        obstaclePoints4.add(new Point(170, 40));
        obstaclePoints4.add(new Point(140, 80));
        Obstacle obstacleFour = createObstacle(obstaclePoints4);
        obstacles.add(obstacleFour);     //加入合集

        generateLinkLines(obstacles,linkLines);     // 生成各障碍物顶点之间的link线
        generateMapLinkLines(obstacles,linkLines);   // 生成合障碍物顶点与地图边界之间的最短连线
        removeDuplicateLinkLines(linkLines);
        printLinkLines(linkLines);
    }
}

