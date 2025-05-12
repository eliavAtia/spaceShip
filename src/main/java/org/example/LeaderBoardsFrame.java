package org.example;

import javax.swing.*;
import java.awt.*;

public class LeaderBoardsFrame extends JPanel {
    private final Image image;

    public LeaderBoardsFrame(Leaderboard leaderboard, int x, int y) {
        // קביעת גודל הפאנל
        image = new ImageIcon(getClass().getResource("/Images/leaderboard.png")).getImage();
        int w = image.getWidth(this);
        int h = image.getHeight(this);
        setBounds(x-w,y-h/2, w, h);

        // הגדרת Layout מתאים (FlowLayout במקרה זה)
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(null);


        // טקסט
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setOpaque(false);
        textArea.setBorder(null);
        textArea.setForeground(Color.WHITE);
        textArea.setFont(new Font("Arial", Font.BOLD, 18));
        textArea.setFocusable(false);

        StringBuilder sb = new StringBuilder();
        leaderboard.loadFromFile();
        leaderboard.getSortedScores().forEach(score -> sb.append(score.toString()).append("\n"));
        textArea.setText(sb.toString());

        // יצירת JScrollPane עם הטקסט
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.setFocusable(false);

        // הוספת JScrollPane לפאנל
        add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, image.getWidth(this), image.getHeight(this), this);
    }
}
