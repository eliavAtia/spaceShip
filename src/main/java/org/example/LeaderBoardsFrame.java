package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class LeaderBoardsFrame extends JPanel {
    private final Image image;
    private static Font customFont;
    private StartScreen parentPanel;


    public LeaderBoardsFrame(Leaderboard leaderboard, int x, int y,StartScreen parentPanel) {
        image = new ImageIcon(getClass().getResource("/Images/leaderboard.png")).getImage();
        int width = image.getWidth(this);
        int height = image.getHeight(this);
        this.parentPanel=parentPanel;
        ImageIcon exitIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/exit.png")));
        setBounds(x - width / 2, y - height / 2, width*2, height);

        setLayout(null);
        setOpaque(false);
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"))).deriveFont(24f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            customFont = new Font("Arial", Font.PLAIN, 24);
        }
        leaderboard.loadFromFile();
        java.util.List<PlayerScore> scores = leaderboard.getSortedScores();

        int baseY = 198;
        int rowHeight = 67;

        for (int i = 0; i < 5; i++) {
            String text=" ";
            if (i < scores.size()) {
                PlayerScore score = scores.get(i);
                text =  score.toString();
            }


            JLabel scoreLabel = new JLabel(text);
            scoreLabel.setForeground(Color.WHITE);
            scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
            scoreLabel.setFont(customFont);
            scoreLabel.setBounds(130, baseY + i * rowHeight, 300, 33); // X, Y, רוחב, גובה
            add(scoreLabel);
        }
        JButton close = new JButton(exitIcon);
        close.setBounds(getWidth()/2-40, getHeight()-(getHeight()/10)*9+50, 30, 20); // מיקום כפתור "סגור"
        close.addActionListener((e) -> {
            parentPanel.remove(this);
            parentPanel.returnToPreviousPanel();
            parentPanel.revalidate();
            parentPanel.repaint();
        });
        this.add(close);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, image.getWidth(this), image.getHeight(this), this);
    }

}
