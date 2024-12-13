package org.example;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Game {
    private static final String RESULTS_FILE = "top_results.txt";
    private static final ArrayList<Integer> results = new ArrayList<>();

    public static void main(String[] args) {
        loadResults(); // Загружаем результаты при старте
        JFrame frame = new JFrame("Змейка");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(800, 600);

        MenuPanel menuPanel = new MenuPanel(frame);
        frame.add(menuPanel);
        frame.setVisible(true);
    }

    public static ArrayList<Integer> getResults() {
        return results;
    }

    public static void saveResult(int score) {
        if (!results.contains(score)) {
            results.add(score);
        }
        results.sort((a, b) -> b - a); // Сортировка по убыванию
        while (results.size() > 5) {  // Ограничиваем до 5 лучших
            results.remove(results.size() - 1);
        }
        saveResultsToFile(); // Сохраняем результаты в файл
    }

    private static void loadResults() {
        try (BufferedReader reader = new BufferedReader(new FileReader(RESULTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                results.add(Integer.parseInt(line));
            }
            results.sort((a, b) -> b - a); // Сортировка по убыванию
        } catch (FileNotFoundException e) {
            System.out.println("Файл результатов не найден, будет создан новый.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveResultsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RESULTS_FILE))) {
            for (int result : results) {
                writer.write(String.valueOf(result));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
