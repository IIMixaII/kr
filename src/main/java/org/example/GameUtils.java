package org.example;
import java.awt.*;

public class GameUtils {
    public static boolean checkCollision(Snake snake, int width, int height) {
        Point head = snake.getHead();
        if (head.x < 0 || head.y < 0 || head.x >= width || head.y >= height) {
            return true;
        }

        for (int i = 1; i < snake.getSnakeBody().size(); i++) {
            if (head.equals(snake.getSnakeBody().get(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkFoodEaten(Snake snake, Food food) {
        return snake.getHead().equals(food.getLocation());
    }
}


