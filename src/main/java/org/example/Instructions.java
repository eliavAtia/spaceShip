package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Instructions extends JPanel {
    private StartScreen parentPanel;
    private ImageIcon backgroundImage;

    public Instructions(int x, int y, int Width, int High, StartScreen parentPanel) {
        this.setBounds(x, y, Width, High);
        setLayout(null);
        setOpaque(false);
        this.parentPanel = parentPanel;
        backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/מגילה GIF 2.gif")));

        ImageIcon exitIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/exit.png")));
        exitIcon.setImage(exitIcon.getImage().getScaledInstance(50,30,Image.SCALE_SMOOTH));

        JButton close = new JButton(exitIcon);
        close.setBounds(getWidth()-(getWidth()/10)-30, getHeight()-(getHeight()/10)*9, 30, 20); // מיקום כפתור "סגור"
        close.addActionListener((e) -> {
            parentPanel.returnToPreviousPanel();
            parentPanel.revalidate();
            parentPanel.repaint();
        });

        add(close);

    }


    public void changePhotoBackground(){
        backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/scroll last frame.gif")));
    }

    public void Text() {
        JTextArea textArea = new JTextArea();
        textArea.setBounds(getWidth() / 6 + 20, getHeight() / 10 + 110, getWidth() - (getWidth() / 10) * 2 - (getWidth() / 6 + 20), 700);
        textArea.setFont(new Font("Aharoni", Font.BOLD, 20));
        textArea.setLineWrap(true);  // מתן אפשרות למעבר שורות
        textArea.setWrapStyleWord(true);  // חיתוך המילים במקום באמצע
        textArea.setEditable(false);  // לא לאפשר עריכה
        textArea.setOpaque(false);
        add(textArea);

        String instrutionsText = """  
                Welcome to the space shooter game! In this game, you control a spaceship and your goal is to
                survive and destroy all incoming threats in space.
          
                You can move your spaceship using the W, A, S, and D keys – W to move up,
                S to move down, A to move left, and D to move right.
                Press the space bar to shoot bullets and destroy enemies.
          
                Throughout the game, you’ll encounter meteors and enemy spaceships.
                Your job is to destroy them before they hit you.
                Sometimes, after destroying an enemy or a meteor, they may drop power-ups (boosts) that
                will help you in your mission.
                You begin the game with 5 lives, and each time you're hit – whether by a meteor or an enemy
                projectile – you lose one life. If you lose all your lives, the game ends. Stay alert,
                shoot fast, and collect any boosts you can to help you survive longer.
          
                Good luck, and enjoy the battle in space!
          """;
        new Thread(() -> {
            for (int i = 0; i < instrutionsText.length(); i++) {
                final int indx = i;

                SwingUtilities.invokeLater(() -> {
                    char currentChar = instrutionsText.charAt(indx);
                    textArea.append(String.valueOf(currentChar));
                });
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage.getImage(), getWidth()/10, getHeight()/10, getWidth()-(getWidth()/10)*2, getHeight()-(getWidth()/10)*2, this);
    }

}
