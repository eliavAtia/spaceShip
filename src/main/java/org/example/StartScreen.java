package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class StartScreen extends JPanel {
    private JButton StartButton, instructionsButton, leaderboardButton, okButton;
    private JTextField enterName;
    private JPanel contentPanel;
    private Content game;
    private SoundPlayer backGroundMusic;
    private Instructions instructions;
    private ImageIcon Gif;
    private final ImageIcon startButtonImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/Start_game_photo-removebg-preview.png")));
    private final ImageIcon instructionsButtonImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/instruction-removebg-preview.png")));
    private final ImageIcon leaderboardButtonImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/__LEADERBOARDS_-_עותק-removebg-preview.png")));
    private final ImageIcon nameImages = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/cooltext482504470959714.png")));
    private final JFrame frame;
    private String currentPlayerName;// StartScreen
    private LeaderBoardsFrame leaderBoardsFrame;
    private Leaderboard leaderboard;
    private boolean nameEntered = false;

    public StartScreen(JFrame frame, int x, int y, int Width, int High) {
        this.frame = frame;
        this.setBounds(x, y, Width, High);
        setLayout(null);
        backGroundBuilder();
        leaderboard = new Leaderboard();
        try {
            leaderboard.loadFromFile();
        } catch (Exception e) {
            System.out.println("לא נטען לוח ניקוד - נתחיל חדש.");
        }
        contentBuilder(Width, High);
        instructionsBuilder(Width, High);
        startButtonBuilder(Width, High);
        leaderboardsBuilder(Width, High);
        instructionsButtonBuilder(Width, High);
        leaderboardButtonBuilder(Width, High);
        this.add(contentPanel);
        this.add(instructions);
        textField();
    }

    private void backGroundBuilder(){
        backGroundMusic = new SoundPlayer("/Sounds/RICK_AND_MORTme.wav");
        backGroundMusic.playLoop();
        Gif = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/space 4K gif.gif")));
    }

    private void contentBuilder(int Width, int High){
        contentPanel = new JPanel();
        contentPanel.setBounds(0, 0, Width, High);
        contentPanel.setLayout(null);
        contentPanel.setOpaque(false);
    }

    private void leaderboardsBuilder(int Width,int High){
        this.leaderBoardsFrame=new LeaderBoardsFrame(leaderboard,getWidth()/2,getHeight()/2,this);
        this.leaderBoardsFrame.setVisible(true);
    }

    private void instructionsBuilder(int Width, int High){
        this.instructions = new Instructions(0, 0, Width, High, this);
        this.instructions.setVisible(false);
    }

    private void startButtonBuilder(int Width, int High){
        this.startButtonImage.setImage(this.startButtonImage.getImage().getScaledInstance(375, 175, Image.SCALE_SMOOTH));
        this.StartButton = new JButton(startButtonImage);
        this.StartButton.setBounds((Width / 2) - (startButtonImage.getIconWidth() / 2), (High / 3) - (startButtonImage.getIconHeight() / 2)+100, 300, 70);
        this.StartButton.setContentAreaFilled(false);
        this.StartButton.setBorder(null);
        this.StartButton.addActionListener((E) -> StartGame());
    }

    private void instructionsButtonBuilder(int Width, int High){
        this.instructionsButtonImage.setImage(this.instructionsButtonImage.getImage().getScaledInstance(375, 175, Image.SCALE_SMOOTH));
        this.instructionsButton = new JButton(instructionsButtonImage);
        this.instructionsButton.setBounds((Width / 2) - (startButtonImage.getIconWidth() / 2), (High / 3) - (instructionsButtonImage.getIconHeight() / 2)+20+(instructionsButtonImage.getIconHeight()) , 300, 70);
        this.instructionsButton.setContentAreaFilled(false);
        this.instructionsButton.setBorder(null);
        instructionsButton.addActionListener((E) -> {
            contentPanel.setVisible(false);
            instructions.setVisible(true);
            new Thread(() -> {
                try {
                    Thread.sleep(1000); // השהיה של 2000 מילישניות = 2 שניות
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
        this.leaderboardButton.setBounds((Width / 2) - (startButtonImage.getIconWidth() / 2), (High / 3) - (instructionsButtonImage.getIconHeight() / 2)+(leaderboardButtonImage.getIconHeight())+110, 300, 70);
        this.leaderboardButton.setContentAreaFilled(false);
        this.leaderboardButton.setBorder(null);
        leaderboardButton.addActionListener((E)->{
            contentPanel.setVisible(false);
            this.add(leaderBoardsFrame);
            new Thread(()->{
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SwingUtilities.invokeLater(()->{
                    leaderBoardsFrame.setVisible(true);
                });
            }).start();
                this.revalidate();
                this.repaint();
        });
    }

    private void StartGame() {
        this.game=new Content(currentPlayerName,frame,0,0,getWidth(),getHeight(),this);
        backGroundMusic.stop();
        frame.setContentPane(game);
        frame.revalidate();
        frame.repaint();
        game.requestFocusInWindow();
    }

    public void returnToPreviousPanel() {
        contentPanel.setVisible(true);
        instructions.setVisible(false);
            if (nameEntered) {
                // מציג רק את הכפתורים כי שם כבר הוזן
                contentPanel.add(instructionsButton);
                contentPanel.add(leaderboardButton);
                contentPanel.add(StartButton);

                if (enterName != null) enterName.setVisible(false);
                if (okButton != null) okButton.setVisible(false);
            } else {
                // לא הוזן שם עדיין – מציג שדה טקסט
                if (enterName != null) enterName.setVisible(true);
                if (okButton != null) okButton.setVisible(true);
            }
        if (backGroundMusic != null && !backGroundMusic.isRunning()) {
            backGroundMusic.playLoop();
        }
        this.revalidate();
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Gif.getImage(), 0, 0, getWidth(), getHeight(), this);
        g.drawImage(nameImages.getImage(),getWidth()/2 - nameImages.getIconWidth()/2,70,nameImages.getIconWidth(),nameImages.getIconHeight(),this);
    }

    private void textField() {
        if (nameEntered) return;
        enterName = new JTextField();
        enterName.setBounds(getWidth()/2 - 200, getHeight()/2, 300, 40);
        enterName.setFont(new Font("Aharoni", Font.PLAIN, 18));
        enterName.setForeground(Color.white);// צבע טקסט
        enterName.setOpaque(false);// צבע הרקע
        enterName.setHorizontalAlignment(JTextField.CENTER);
        contentPanel.add(enterName);
        okButton = new JButton("OK");
        okButton.setBounds(getWidth()/2+120, getHeight()/2, 80, 40);
        okButton.addActionListener(e -> {
            String name = enterName.getText();
            if (name == null || name.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "אנא הכנס שם לפני התחלה");
            } else {
                currentPlayerName = name.trim(); // StartScreen
                nameEntered = true;
                try {
                    FileWriter writer = new FileWriter("names.txt", true); // true = append mode
                    writer.write(name.trim() + "\n");
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "שגיאה בשמירת השם לקובץ.");
                }
                enterName.setVisible(false);
                okButton.setVisible(false);
                this.contentPanel.add(instructionsButton);
                this.contentPanel.add(leaderboardButton);
                this.contentPanel.add(StartButton);
            }
        });

        contentPanel.add(okButton);
    }
}
