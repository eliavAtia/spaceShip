package org.example;
import javax.swing.*;
public class Main {
    private static final int WINDOW_WIDTH=1000;
    private static final int WINDOW_HEIGHT=700;
    public static void main(String[] args) {
        JFrame window=new JFrame("space nigga");
//        UserManager userManager= new UserManager(0,0,200,1000);
//        window.add(userManager);
        StartScreen startScreen = new StartScreen(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
        window.setContentPane(startScreen);
        window.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        window.setResizable(true);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon(Main.class.getResource("/Images/sSpaceShip.png"));
        window.setIconImage(icon.getImage());
        window.setLayout(null);
        window.setVisible(true);

//        window.setResizable(false);
    }
}