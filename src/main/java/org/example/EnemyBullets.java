package org.example;

import javax.swing.*;
import java.awt.*;


public class EnemyBullets {
    private int x;
    private int y;
    private int width;
    private int height;
    private Image image;


    public EnemyBullets(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 60;
        this.height = 60;
        try {
            ImageIcon imageIcon = new ImageIcon(getClass().getResource("/cow.png"));
            this.image = imageIcon.getImage();
        } catch (Exception e) {
            System.out.println("שגיאה בטעינת התמונה: " + e.getMessage());
            this.image = null;
        }
    }

    public void paint(Graphics graphics) {
        if (this.image != null) {
            graphics.drawImage(this.image, this.x, this.y, this.width, this.height, null);
        } else {
            graphics.setColor(Color.RED);
            graphics.fillRect(this.x, this.y, this.width, this.height);
            graphics.setColor(Color.BLACK);
            graphics.drawString("תמונה חסרה", this.x + 10, this.y + this.height / 2);
        }
    }

    public void moveDown(){
        this.y+=8;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}


