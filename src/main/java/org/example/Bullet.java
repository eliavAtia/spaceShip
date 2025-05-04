package org.example;

import javax.swing.*;
import java.awt.*;

public class Bullet {
    private int x;
    private int y;
    private int width;
    private int height;
    private Image image;
    public Bullet(int x, int y, int width, int height){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.image= new ImageIcon(getClass().getResource("/Images/laser.png")).getImage();
    }
    public void draw(Graphics g){
        g.drawImage(this.image,this.x,this.y,null);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
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
    public boolean isOutOfScreen(int y){
        return this.y<0;
    }
    public void move(){
        this.y-=10;
    }
}
