package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StartScreen extends JPanel {
    private JButton StartButton, instructionsButton,leaderboardButton;
    private JPanel contentPanel;
    private Content game;
    private SoundPlayer backGround;
    private Instructions instructions;
    private ImageIcon Gif;
    private final  ImageIcon startButtonImage = new ImageIcon(getClass().getResource("/Images/Start_game_photo-removebg-preview.png"));
    private final  ImageIcon instructionsButtonImage = new ImageIcon(getClass().getResource("/Images/instruction-removebg-preview.png"));
    private final  ImageIcon leaderboardButtonImage = new ImageIcon(getClass().getResource("/Images/__LEADERBOARDS_-_עותק-removebg-preview.png"));




    public StartScreen(int x, int y, int Width, int High) {
        this.setBounds(x, y, Width, High);
        setLayout(null);

        backGround = new SoundPlayer("/Sounds/RICK_AND_MORTY.wav");
        backGround.playLoop();

        Gif = new ImageIcon(getClass().getResource("/Images/space 4K gif.gif"));

        contentPanel = new JPanel();
        contentPanel.setBounds(0, 0, Width, High);
        contentPanel.setLayout(null);
        contentPanel.setOpaque(false);

        instructions = new Instructions(0, 0, Width, High,this);
        instructions.setVisible(false); // מוסתר כברירת מחדל

        this.startButtonImage.setImage(this.startButtonImage.getImage().getScaledInstance(375,175,Image.SCALE_SMOOTH));
        this.StartButton = new JButton(startButtonImage);
        this.StartButton.setBounds((Width / 2) - (startButtonImage.getIconWidth()/2), (High / 3) -(startButtonImage.getIconHeight()/2) , 300, 70);
        this.StartButton.setContentAreaFilled(false);
        this.StartButton.setBorder(null);
        contentPanel.add(StartButton); // דווקא CONTENT כי זה מוסיף רק לפאנל הזה כי אחר כך כשאני עושה REMOVE זה ימחק רק מפה
        this.StartButton.addActionListener((E) -> StartGame());


        this.instructionsButtonImage.setImage(this.instructionsButtonImage.getImage().getScaledInstance(375,175,Image.SCALE_SMOOTH));
        this.instructionsButton = new JButton(instructionsButtonImage);
        this.instructionsButton.setBounds((Width / 2) - (startButtonImage.getIconWidth()/2), (High / 3) -(instructionsButtonImage.getIconHeight()/2)+(startButtonImage.getIconHeight()/(2)+30),300, 70);
        this.instructionsButton.setContentAreaFilled(false);
        this.instructionsButton.setBorder(null);
        contentPanel.add(instructionsButton);


        instructionsButton.addActionListener((E) -> {
            contentPanel.setVisible(false);
            instructions.setVisible(true);
            new Thread(() -> {
                try {
                    Thread.sleep(1450); // השהיה של 2000 מילישניות = 2 שניות
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // הפעולה שתרצה לבצע אחרי ההשהיה:
                SwingUtilities.invokeLater(() -> {
                    instructions.changePhotoBackground();
                });
            }).start();
            this.revalidate();
            this.repaint();
        });

        this.leaderboardButtonImage.setImage(this.leaderboardButtonImage.getImage().getScaledInstance(375,175,Image.SCALE_SMOOTH));
        this.leaderboardButton = new JButton(leaderboardButtonImage);
        this.leaderboardButton.setBounds((Width / 2) - (startButtonImage.getIconWidth()/2), (High / 3) -(leaderboardButtonImage.getIconHeight()/2)+(instructionsButtonImage.getIconHeight()*(2)/(2)+30),300, 70);
        this.leaderboardButton.setContentAreaFilled(false);
        this.leaderboardButton.setBorder(null);
        contentPanel.add(leaderboardButton);


        this.add(contentPanel);
        this.add(instructions); // חשוב שזה יופיע אחרי contentPanel כדי להיות מעליו
    }

    private void StartGame() {
        this.removeAll();
        backGround.stop();
        game = new Content(0, 0, getWidth(), getHeight());
        this.add(game);
        game.requestFocusInWindow();
        this.revalidate();
        this.repaint();
    }
    public void returnToPreviousPanel() {
        contentPanel.setVisible(true);  // מציג את contentPanel מחדש
        instructions.setVisible(false);  // מסתיר את ההוראות
        this.revalidate();
        this.repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Gif.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}
