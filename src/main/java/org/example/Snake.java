package org.example;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Snake {
    private final ArrayList<Point> body = new ArrayList<>();
    private int direction = KeyEvent.VK_RIGHT;
    private int score = 0;

    public Snake() {
        body.add(new Point(100, 100));
    }

    public void move() {
        Point head = new Point(body.get(0));
        switch (direction) {
            case KeyEvent.VK_UP -> head.translate(0, -25);
            case KeyEvent.VK_DOWN -> head.translate(0, 25);
            case KeyEvent.VK_LEFT -> head.translate(-25, 0);
            case KeyEvent.VK_RIGHT -> head.translate(25, 0);
        }
        body.add(0, head);
        body.remove(body.size() - 1);
    }

    public void grow() {
        body.add(new Point(body.get(body.size() - 1)));
    }

    public void setDirection(int keyCode) {
        if ((keyCode == KeyEvent.VK_UP && direction != KeyEvent.VK_DOWN) ||
                (keyCode == KeyEvent.VK_DOWN && direction != KeyEvent.VK_UP) ||
                (keyCode == KeyEvent.VK_LEFT && direction != KeyEvent.VK_RIGHT) ||
                (keyCode == KeyEvent.VK_RIGHT && direction != KeyEvent.VK_LEFT)) {
            direction = keyCode;
        }
    }

    public void draw(Graphics g, int unitSize) {
        g.setColor(Color.GREEN);
        for (Point point : body) {
            g.fillRect(point.x, point.y, unitSize, unitSize);
        }
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        score++;
    }

    public ArrayList<Point> getSnakeBody() {
        return body;
    }

    public Point getHead() {
        return body.get(0);
    }
}
