package org.example;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("DriftZone");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        window.setSize(screenSize.width, screenSize.height);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(Main.class.getResource("/Images/sSpaceShip.png")));
        window.setIconImage(icon.getImage());
        window.setLayout(null);
        StartScreen startScreen = new StartScreen(window, 0, 0, screenSize.width, screenSize.height);
        window.setContentPane(startScreen);
        window.setVisible(true);
    }
}