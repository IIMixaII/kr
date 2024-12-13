package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int UNIT_SIZE = 25;
    private static final int START_DELAY = 150;

    private final Timer timer;
    private final Snake snake;
    private final Food food;
    private final JFrame frame;
    private final ArrayList<Point> obstacles = new ArrayList<>();

    private boolean running = false;

    public GamePanel(JFrame frame) {
        this.frame = frame;

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

        this.timer = new Timer(START_DELAY, this);
        this.snake = new Snake();
        this.food = new Food(WIDTH, HEIGHT, UNIT_SIZE);
    }

    public void startGame() {
        food.generateFood(snake.getSnakeBody(), obstacles);
        running = true;
        this.requestFocusInWindow();
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        if (running) {
            g.setColor(Color.RED);
            g.fillOval(food.getX(), food.getY(), UNIT_SIZE, UNIT_SIZE);

            snake.draw(g, UNIT_SIZE);

            g.setColor(Color.GRAY);
            for (Point obstacle : obstacles) {
                g.fillRect(obstacle.x, obstacle.y, UNIT_SIZE, UNIT_SIZE);
            }

            g.setColor(Color.WHITE);
            g.setFont(new Font("Ink Free", Font.BOLD, 20));
            g.drawString("Очки: " + snake.getScore(), 10, 20);
        } else {
            showGameOver(g);
        }
    }

    private void showGameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (WIDTH - metrics.stringWidth("Game Over")) / 2, HEIGHT / 2);

        g.setFont(new Font("Ink Free", Font.BOLD, 30));
        g.drawString("Ваш результат: " + snake.getScore(),
                (WIDTH - metrics.stringWidth("Ваш результат: " + snake.getScore())) / 2, HEIGHT / 2 + 50);

        saveResult();

        JButton menuButton = new JButton("Назад в меню");
        menuButton.setBounds(WIDTH / 2 - 75, HEIGHT / 2 + 100, 150, 40);
        menuButton.addActionListener(e -> {
            this.remove(menuButton);
            backToMenu();
            this.requestFocusInWindow();
        });
        this.setLayout(null);
        this.add(menuButton);
        this.repaint();
    }

    private void saveResult() {
        Game.saveResult(snake.getScore());
    }

    private void backToMenu() {
        timer.stop();
        frame.getContentPane().removeAll();
        MenuPanel menuPanel = new MenuPanel(frame);
        frame.add(menuPanel);
        frame.revalidate();
        frame.repaint();
    }

    private void adjustGameSpeed() {
        int newDelay = Math.max(50, START_DELAY - snake.getScore() / 5);
        timer.setDelay(newDelay);
    }

    private void generateObstacles() {
        int requiredObstacles = snake.getScore() / 3;
        while (obstacles.size() < requiredObstacles) {
            int x = (int) (Math.random() * (WIDTH / UNIT_SIZE)) * UNIT_SIZE;
            int y = (int) (Math.random() * (HEIGHT / UNIT_SIZE)) * UNIT_SIZE;

            Point newObstacle = new Point(x, y);
            if (!snake.getSnakeBody().contains(newObstacle) && !food.getLocation().equals(newObstacle)) {
                obstacles.add(newObstacle);
            }
        }
    }

    private boolean checkObstacleCollision() {
        for (Point obstacle : obstacles) {
            if (snake.getHead().equals(obstacle)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            snake.move();
            if (GameUtils.checkCollision(snake, WIDTH, HEIGHT) || checkObstacleCollision()) {
                running = false;
                timer.stop();
            }
            if (GameUtils.checkFoodEaten(snake, food)) {
                snake.grow();
                snake.incrementScore();
                food.generateFood(snake.getSnakeBody(), obstacles);
                adjustGameSpeed();
                generateObstacles();
            }
        }
        repaint();
    }

    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            snake.setDirection(e.getKeyCode());
        }
    }
}
