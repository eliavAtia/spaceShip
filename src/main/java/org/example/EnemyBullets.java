package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;


public class EnemyBullets extends Mob {

    public EnemyBullets(int x, int y,int width,int height, Image image) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        setImage(image);
        setLife(1);
    }

    public void paint(Graphics graphics) {
        if (getImage() != null) {
            graphics.drawImage(getImage(), getX()-getWidth()/2, getY()-getHeight()/2, getWidth(), getHeight(), null);
        } else {
            graphics.setColor(Color.RED);
            graphics.fillRect(getX(), getY(), getWidth(), getHeight());
            graphics.setColor(Color.BLACK);
            graphics.drawString("תמונה חסרה", getX() + 10, getY() + getHeight() / 2);
        }
    }

    public void moveDown(int speed){
        setY(getY()+speed);
    }

}


