package org.example;

import javax.swing.*;

import java.awt.*;
import java.util.Objects;

public class StartScreen extends JPanel {
    private JButton StartButton, instructionsButton, leaderboardButton;
    private JPanel contentPanel;
    private Content game;
    private SoundPlayer backGroundMusic;
    private Instructions instructions;
    private ImageIcon Gif;
    private final ImageIcon startButtonImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/Start_game_photo-removebg-preview.png")));
    private final ImageIcon instructionsButtonImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/instruction-removebg-preview.png")));
    private final ImageIcon leaderboardButtonImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/__LEADERBOARDS_-_עותק-removebg-preview.png")));
    private final ImageIcon nameImages = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/Cool Text - space NIgga 482251320904050.png")));
    private final JFrame frame;

    public StartScreen(JFrame frame, int x, int y, int Width, int High) {
        this.frame = frame;
        this.setBounds(x, y, Width, High);
        setLayout(null);
        backGroundBuilder();
        contentBuilder(Width,High);
        instructionsBuilder(Width,High);
        startButtonBuilder(Width,High);
        instructionsButtonBuilder(Width,High);
        leaderboardButtonBuilder(Width,High);
        this.add(contentPanel);
        this.add(instructions);
    }

    private void backGroundBuilder(){
        backGroundMusic = new SoundPlayer("/Sounds/RICK_AND_MORTY.wav");
        backGroundMusic.playLoop();
        Gif = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/space 4K gif.gif")));
    }

    private void contentBuilder(int Width, int High){
        contentPanel = new JPanel();
        contentPanel.setBounds(0, 0, Width, High);
        contentPanel.setLayout(null);
        contentPanel.setOpaque(false);
    }

    private void instructionsBuilder(int Width, int High){
        this.instructions = new Instructions(0, 0, Width, High, this);
        this.instructions.setVisible(false);
    }

    private void startButtonBuilder(int Width, int High){
        this.startButtonImage.setImage(this.startButtonImage.getImage().getScaledInstance(375, 175, Image.SCALE_SMOOTH));
        this.StartButton = new JButton(startButtonImage);
        this.StartButton.setBounds((Width / 2) - (startButtonImage.getIconWidth() / 2), (High / 3) - (startButtonImage.getIconHeight() / 2)+50, 300, 70);
        this.StartButton.setContentAreaFilled(false);
        this.StartButton.setBorder(null);
        this.contentPanel.add(StartButton); // דווקא CONTENT כי זה מוסיף רק לפאנל הזה כי אחר כך כשאני עושה REMOVE זה ימחק רק מפה
        this.StartButton.addActionListener((E) -> StartGame());
    }

    private void instructionsButtonBuilder(int Width, int High){
        this.instructionsButtonImage.setImage(this.instructionsButtonImage.getImage().getScaledInstance(375, 175, Image.SCALE_SMOOTH));
        this.instructionsButton = new JButton(instructionsButtonImage);
        this.instructionsButton.setBounds((Width / 2) - (startButtonImage.getIconWidth() / 2), (High / 3) - (instructionsButtonImage.getIconHeight() / 2) + (startButtonImage.getIconHeight() / (2) + 10)+50, 300, 70);
        this.instructionsButton.setContentAreaFilled(false);
        this.instructionsButton.setBorder(null);
        this.contentPanel.add(instructionsButton);

        instructionsButton.addActionListener((E) -> {
            contentPanel.setVisible(false);
            instructions.setVisible(true);
            new Thread(() -> {
                try {
                    Thread.sleep(1200); // השהיה של 2000 מילישניות = 2 שניות
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // הפעולה שתרצה לבצע אחרי ההשהיה:
                SwingUtilities.invokeLater(() -> {
                    instructions.Text();
                    instructions.changePhotoBackground();
                });
            }).start();
            this.revalidate();
            this.repaint();
        });
    }

    private void leaderboardButtonBuilder(int Width, int High){
        this.leaderboardButtonImage.setImage(this.leaderboardButtonImage.getImage().getScaledInstance(375, 175, Image.SCALE_SMOOTH));
        this.leaderboardButton = new JButton(leaderboardButtonImage);
        this.leaderboardButton.setBounds((Width / 2) - (startButtonImage.getIconWidth() / 2), (High / 3) - (leaderboardButtonImage.getIconHeight() / 2) + (instructionsButtonImage.getIconHeight() * (2) / (2) + 10)+50, 300, 70);
        this.leaderboardButton.setContentAreaFilled(false);
        this.leaderboardButton.setBorder(null);
        this.contentPanel.add(leaderboardButton);
    }

    private void StartGame() {
        backGroundMusic.stop();
        game = new Content(frame,0, 0, frame.getWidth(), frame.getHeight(),this);
        frame.setContentPane(game);
        frame.revalidate();
        frame.repaint();
        game.requestFocusInWindow();
    }

    public void returnToPreviousPanel() {
        contentPanel.setVisible(true);
        instructions.setVisible(false);
        this.revalidate();
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Gif.getImage(), 0, 0, getWidth(), getHeight(), this);
        g.drawImage(nameImages.getImage(),getWidth()/2 - nameImages.getIconWidth()/2,70,nameImages.getIconWidth(),nameImages.getIconHeight(),this);
    }
}
