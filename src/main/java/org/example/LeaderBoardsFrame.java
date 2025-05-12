package org.example;
import javax.swing.*;
import java.awt.*;

public class LeaderBoardsFrame extends JPanel {
    public LeaderBoardsFrame(Leaderboard leaderboard,int x,int y,int width ,int height) {
        setBounds(x,y,width,height);// מרכז המסך
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        StringBuilder sb = new StringBuilder();
        leaderboard.loadFromFile();
        leaderboard.getSortedScores().forEach(score ->
                sb.append(score.toString()).append("\n")
        );
        textArea.setText(sb.toString());

        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }
}

