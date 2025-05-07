package org.example;

import javax.swing.*;
import java.awt.*;

public class Boss extends  Mob {
    private int speed = 1; // מהירות הבוס
    private int direction = 1;
    private int maxHp = 150;
    private boolean active;

    public Boss(int x, int y, int width, int height) {
        setX(x);
        setY(y);
        setHeight(height);
        setWidth(width);
        setLife(maxHp);
        setImage(new ImageIcon(getClass().getResource("/Images/Boss1.png")).getImage());
    }

    public void draw(Graphics g) {
        g.drawImage(getImage(), getX(), getY(), getWidth(), getHeight(), null);
        g.setColor(Color.BLACK);
        g.fillRect(getX(), getY() - 20, getWidth(), 10);
        g.setColor(Color.RED);
        g.fillRect(getX(), getY() - 20, (int) ((double) getLife() / maxHp * getWidth()), 10);
    }

    public Rectangle getBounds() {
        return new Rectangle(getX(), getY() - getHeight() / 2, getWidth(), getHeight());
    }

    public void move() {
        setX(getX() + speed * direction);
        if (getX() + getWidth() >= 1420) {
            direction = -1;
        } else if (getX() <= 0) {
            direction = 1;
        }
    }

    public void hit() {
        super.mobHit();
    }

    public boolean isDead() {
        return getLife() <= 0;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }
}
