package org.example;

import javax.swing.*;
import java.awt.*;

public class Picture extends JPanel {
    private Image pic;
    private Menu menu;
    public Picture(String imagePath){
        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
        pic = icon.getImage();
        if (icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.out.println("❌ התמונה לא נטענה!");
        } else {
            System.out.println("✅ התמונה נטענה בהצלחה!");
            }
        menu=new Menu(0,0,1000,100);
        this.add(menu);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (pic != null) {
            g.drawImage(pic, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

