package org.example;

import javax.swing.*;
import java.awt.*;

public class Menu extends JPanel {
    JLabel title;
    public Menu(int x,int y,int width,int height){
        this.setBounds(x,y,width,height);
        this.setBackground(Color.cyan);
        title =new JLabel("welcome to the game");
        title.setBounds(0,0,400,80);
        title.setFont(new Font("David",Font.BOLD,24));
        title.setLayout(null);
        this.setLayout(new FlowLayout(FlowLayout.CENTER,0,10));
        this.add(title);
    }
}
