package AAsterick;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;


public class AAsterick {
    private static final int[][] dirs = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1},
            {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
    };

    public static List<Node> findPath(int[][] grid, Node start, Node goal, List<Node> exploredNodes){
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Set<Node> closedSet = new HashSet<>();
        start.g = 0;
        start.h = heuristic(start, goal);
        openSet.add(start);

        while(!openSet.isEmpty()){
            Node current = openSet.poll();
            exploredNodes.add(current);

            if(current.equals(goal)){
                return reconstructPath(current);
            }

            closedSet.add(current);

            for (int[] dir : dirs) {
                int newX = current.x + dir[0];
                int newY = current.y + dir[1];

                if(newX < 0|| newX >= grid.length || newY < 0 || newY >= grid[0].length || grid[newX][newY] == 1){
                    continue;
                }

                Node neighbor = new Node(newX, newY);

                if(closedSet.contains(neighbor)){
                    continue;
                }

                double tentativeG = current.g +1;

                if(!openSet.contains(neighbor) || tentativeG < neighbor.g){
                    neighbor.parent = current;
                    neighbor.g = tentativeG;
                    neighbor.h = heuristic(neighbor, goal);
                    openSet.add(neighbor);
                }
            }
        }
        return Collections.emptyList();
    }

    private static double heuristic(Node a, Node b){
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    public static List<Node> reconstructPath(Node node){
        List<Node> path = new ArrayList<>();
        while(node != null){
            path.add(node);
            node = node.parent;
        }

        Collections.reverse(path);
        return path;
    }
}