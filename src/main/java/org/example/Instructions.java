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

        // שמירה על הפאנל הקודם
        this.parentPanel = parentPanel;

        backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/מגילה GIF 2.gif")));

        ImageIcon exitIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/exit.png")));
        exitIcon.setImage(exitIcon.getImage().getScaledInstance(50,30,Image.SCALE_SMOOTH));

        JButton close = new JButton(exitIcon);
        close.setBounds(getWidth()-(getWidth()/10)-30, getHeight()-(getHeight()/10)*9, 30, 20); // מיקום כפתור "סגור"
        close.addActionListener((e) -> {
            // הסתרת פאנל ההוראות

            // הצגת הפאנל הקודם
            parentPanel.returnToPreviousPanel();

            // ריענון של הפאנל הקודם
            parentPanel.revalidate();
            parentPanel.repaint();
        });

        add(close);

    }
    public void changePhotoBackground(){
        backgroundImage = new ImageIcon(getClass().getResource("/Images/scroll last frame.gif"));
    }
    public void Text() {
        JTextArea textArea = new JTextArea();
        textArea.setBounds(getWidth() / 6 + 20, getHeight() / 10 + 100, getWidth() - (getWidth() / 10) * 2 - (getWidth() / 6 + 20), 700);
        textArea.setFont(new Font("Aharoni", Font.BOLD, 20));
        textArea.setLineWrap(true);  // מתן אפשרות למעבר שורות
        textArea.setWrapStyleWord(true);  // חיתוך המילים במקום באמצע
        textArea.setEditable(false);  // לא לאפשר עריכה
        textArea.setOpaque(false);
        add(textArea);

        String instrutionsText = """  
        Hi welcome to our game! ================================================================================================ 
         
         so this is how to play
         
         
         
  
         
         Enjoy!
          """;
        new Thread(() -> {
            for (int i = 0; i < instrutionsText.length(); i++) {
                final int indx = i;

                SwingUtilities.invokeLater(() -> {
                    char currentChar = instrutionsText.charAt(indx);
                    textArea.append(String.valueOf(currentChar));
                });
                try {
                    Thread.sleep(40);
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
