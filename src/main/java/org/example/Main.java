package org.example;
import javax.swing.*;
public class Main {
    private static final int WINDOW_WIDTH=1000;
    private static final int WINDOW_HEIGHT=600;
    public static void main(String[] args) {
        JFrame window=new JFrame("space ni  gga");
        Content content=new Content(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
        window.setContentPane(content);
        window.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        window.setResizable(true);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLayout(null);
        window.setVisible(true);
    }
}