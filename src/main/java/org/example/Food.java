package org.example;
import java.awt.*;
import java.util.ArrayList;

public class Food {
    private final int width, height, unitSize;
    private Point location;

    public Food(int width, int height, int unitSize) {
        this.width = width;
        this.height = height;
        this.unitSize = unitSize;
    }

    public void generateFood(ArrayList<Point> snakeBody, ArrayList<Point> obstacles) {
        int x, y;
        do {
            x = (int) (Math.random() * (width / unitSize)) * unitSize;
            y = (int) (Math.random() * (height / unitSize)) * unitSize;

            location = new Point(x, y);
        } while (snakeBody.contains(location) || obstacles.contains(location));
    }

    public int getX() {
        return location.x;
    }

    public int getY() {
        return location.y;
    }

    public Point getLocation() {
        return location;
    }
}
