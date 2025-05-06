package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Boss1 {
    private int x, y, width, height, hp, maxHp;
    private Image bossImage;
    private int speed = 1; // מהירות הבוס
    private int direction = 1;
    private int WINDOW_WIDTH;

    public Boss1(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.maxHp = 150;
        this.hp = maxHp;
        this.bossImage = new ImageIcon(getClass().getResource("/Images/Boss1.png")).getImage();
    }

    public void draw(Graphics g) {
        g.drawImage(bossImage, x, y, width, height, null);
        g.setColor(Color.BLACK);
        g.fillRect(x, y -20, width, 10);
        g.setColor(Color.RED);
        g.fillRect(x, y - 20, (int)((double)hp / maxHp * width), 10);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y-height/2, width, height);
    }

    public void move() {
        x += speed * direction;
        if (x + width >= 1420) {
            direction = -1;
        }
        else if (x <= 0) {
            direction = 1;
        }
    }

    public void hit() {
        hp --;
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Image getBossImage() {
        return bossImage;
    }

    public void setBossImage(Image bossImage) {
        this.bossImage = bossImage;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
