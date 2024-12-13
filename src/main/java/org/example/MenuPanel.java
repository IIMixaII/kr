package org.example;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MenuPanel extends JPanel {
    private final JFrame frame;

    public MenuPanel(JFrame frame) {
        this.frame = frame;

        // Устанавливаем макет для панели
        this.setLayout(new BorderLayout());

        // Фоновое изображение
        JLabel background = new JLabel(new ImageIcon("background.jpg"));
        background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS));
        this.add(background);

        // Заголовок
        JLabel titleLabel = new JLabel("Змейка", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 60));
        titleLabel.setForeground(Color.GREEN);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));

        // Кнопки
        JButton startButton = createStyledButton("Начать игру");
        JButton topResultsButton = createStyledButton("Топ 5 результатов");
        JButton exitButton = createStyledButton("Выход");

        // Слушатели для кнопок
        startButton.addActionListener(e -> startGame());
        topResultsButton.addActionListener(e -> showTopResults());
        exitButton.addActionListener(e -> System.exit(0));

        // Панель для размещения кнопок
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false); // Прозрачный фон
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createVerticalStrut(20)); // Пробел между кнопками
        buttonPanel.add(topResultsButton);
        buttonPanel.add(Box.createVerticalStrut(20)); // Пробел между кнопками
        buttonPanel.add(exitButton);

        // Добавляем элементы на фон
        background.add(titleLabel);
        background.add(Box.createVerticalGlue()); // Центрируем кнопки
        background.add(buttonPanel);
        background.add(Box.createVerticalGlue());
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setBackground(new Color(50, 205, 50));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void startGame() {
        frame.getContentPane().removeAll();
        GamePanel gamePanel = new GamePanel(frame);
        frame.add(gamePanel);
        frame.revalidate();
        frame.repaint();
        gamePanel.startGame();
    }

    private void showTopResults() {
        StringBuilder resultsMessage = new StringBuilder("Топ 5 результатов:\n");
        for (int i = 0; i < Game.getResults().size(); i++) {
            resultsMessage.append(i + 1).append(". ").append(Game.getResults().get(i)).append("\n");
        }

        JOptionPane.showMessageDialog(this, resultsMessage.toString(), "Топ 5 результатов", JOptionPane.INFORMATION_MESSAGE);
    }
}
