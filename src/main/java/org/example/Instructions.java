package org.example;

import javax.swing.*;
import java.awt.*;

public class Instructions extends JPanel {
    private StartScreen parentPanel;
    private ImageIcon backgroundImage;

    public Instructions(int x, int y, int Width, int High, StartScreen parentPanel) {
        this.setBounds(x, y, Width, High);
        setLayout(null);
        setOpaque(false);

        // שמירה על הפאנל הקודם
        this.parentPanel = parentPanel;

        backgroundImage = new ImageIcon(getClass().getResource("/Images/מגילה GIF 2.gif"));


        JTextArea textArea = new JTextArea();
        textArea.setText("ghfffffffffffffffffffffffffffffffffffffffffffffffffffffffff" +
                "dsfsdfdfsfsf" +
                "dfsfsfs" +
                "");
        textArea.setBounds(x / 2, y / 2, 50, 10);
        textArea.setLineWrap(true);  // מתן אפשרות למעבר שורות
        textArea.setWrapStyleWord(true);  // חיתוך המילים במקום באמצע
        textArea.setEditable(false);  // לא לאפשר עריכה
        textArea.setOpaque(false);

        ImageIcon exitIcon = new ImageIcon(getClass().getResource("/Images/exit.2.png"));
        exitIcon.setImage(exitIcon.getImage().getScaledInstance(50,30,Image.SCALE_SMOOTH));

        JButton close = new JButton(exitIcon);
        close.setBounds(getWidth()-(getWidth()/10)-30, getHeight()-(getHeight()/10)*9, 30, 20); // מיקום כפתור "סגור"
        close.addActionListener((e) -> {
            // הסתרת פאנל ההוראות
            this.setVisible(false);

            // הצגת הפאנל הקודם
            parentPanel.returnToPreviousPanel();

            // ריענון של הפאנל הקודם
            parentPanel.revalidate();
            parentPanel.repaint();
        });

        add(close);
        add(textArea);

    }
    public void changePhotoBackground(){
        backgroundImage = new ImageIcon(getClass().getResource("/Images/scroll last frame.gif"));
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage.getImage(), getWidth()/10, getHeight()/10, getWidth()-(getWidth()/10)*2, getHeight()-(getWidth()/10)*2, this);
    }

}
