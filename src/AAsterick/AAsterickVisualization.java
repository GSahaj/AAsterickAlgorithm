package AAsterick;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class AAsterickVisualization extends JPanel {
    private static final int ROWS = 40;
    private static final int COLS = 40;
    private static final int CELL_SIZE = 10;
    private int[][] grid;
    private int currentPathIndex = 0;
    private int currentExploredIndex = 0;
    private List<Node> path;
    private List<Node> exploredNodes;

    private Node start;
    private Node goal;
    private Timer timer;
    private Random random;

    public AAsterickVisualization(){
        random = new Random();
        grid = new int[ROWS][COLS];
        path = new ArrayList<>();
        exploredNodes = new ArrayList<>();
        start = new Node(0, 0);
        goal = generateRandomGoal();

        generateRandomObstacles();
        findPath();
        startVisualization();
    }


    private Node generateRandomGoal(){
        Node randomGoal;
        do{
            int x = random.nextInt(ROWS);
            int y  =  random.nextInt(COLS);
            randomGoal = new Node(x, y);
        } while(randomGoal.equals(start) || grid[randomGoal.x][randomGoal.y] == 1);

        return randomGoal;
    }

    private void generateRandomObstacles(){
        Random random = new Random();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (random.nextDouble() < 0.2) {
                    grid[i][j] = 1;
                }
            }
        }
        grid[start.x][start.y] = 0;
        grid[goal.x][goal.y] = 0;
    }


    private void findPath(){
        path = AAsterick.findPath(grid, start, goal, exploredNodes);
    }

    private void startVisualization(){
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(currentExploredIndex < exploredNodes.size()){
                    currentExploredIndex++;
                }
                else if(currentPathIndex < path.size()){
                    currentPathIndex++;
                }
                else{
                    timer.stop();
                }
                repaint();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if(grid[i][j] == 1){
                    g.setColor(Color.BLACK);
                }
                else{
                    g.setColor(Color.WHITE);
                }
                g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                g.setColor(Color.GRAY);
                g.drawRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        g.setColor(Color.CYAN);
        for (int i = 0; i < currentExploredIndex; i++) {
            Node node = exploredNodes.get(i);
            g.fillRect(node.y * CELL_SIZE, node.x * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        g.setColor(Color.GREEN);
        g.fillRect(start.y * CELL_SIZE, start.x * CELL_SIZE, CELL_SIZE, CELL_SIZE);

        g.setColor(Color.RED);
        g.fillRect(goal.y * CELL_SIZE, goal.x * CELL_SIZE, CELL_SIZE, CELL_SIZE);

        g.setColor(Color.BLUE);
        for (Node node: path){
            g.fillRect(node.y * CELL_SIZE, node.x * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }

    public static void main(String[] args){
        JFrame frame = new JFrame();
        AAsterickVisualization panel = new AAsterickVisualization();
        frame.add(panel);
        frame.setSize(COLS * CELL_SIZE , ROWS * CELL_SIZE );
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}