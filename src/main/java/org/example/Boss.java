package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Boss extends  Mob {
    private int speed = 1;
    private int direction = 1;
    private int type;
    private int maxHp;

    public Boss(int type) {
        this.type = type;
        setByType();
    }

    private void setByType(){
        switch (this.type){
            case 1:
                setX(getWidth()/2);
                setY(50);
                setHeight(350);
                setWidth(700);
                this.maxHp=30;
                setLife(maxHp);
                setImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/Boss1.png"))).getImage());
        }

    }

    public void draw(Graphics g) {
        g.drawImage(getImage(), getX() - getWidth()/2, getY() - getHeight()/2, getWidth(), getHeight(), null);
        g.setColor(Color.BLACK);
        g.fillRect(getX(), getY() - 20, getWidth(), 10);
        g.setColor(Color.RED);
        g.fillRect(getX(), getY() - 20, (int) ((double) getLife() / maxHp * getWidth()), 10);
    }

    public void move() {
        setX(getX() + speed * direction);
        if (getX() + getWidth() >= 1420) {
            direction = -1;
        } else if (getX() <= 0) {
            direction = 1;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(getX(), getY() - getHeight() / 2, getWidth(), getHeight());
    }

}
