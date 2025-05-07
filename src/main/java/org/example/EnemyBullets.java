package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;


public class EnemyBullets extends Mob {

    public EnemyBullets(int x, int y) {
        setX(x);
        setY(y);
        setWidth(40);
        setHeight(40);
        setLife(1);
        try {
            ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/cow.png")));
            setImage(imageIcon.getImage());
        } catch (Exception e) {
            System.out.println("שגיאה בטעינת התמונה: " + e.getMessage());
            setImage(null);
        }
    }

    public void paint(Graphics graphics) {
        if (getImage() != null) {
            graphics.drawImage(getImage(), getX(), getY(), getWidth(), getHeight(), null);
        } else {
            graphics.setColor(Color.RED);
            graphics.fillRect(getX(), getY(), getWidth(), getHeight());
            graphics.setColor(Color.BLACK);
            graphics.drawString("תמונה חסרה", getX() + 10, getY() + getHeight() / 2);
        }
    }

    public void moveDown(){
        setY(getY()+8);
    }

}


