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
        g.drawImage(this.image,this.x,this.y,width,height,null);
    }

    public int getHeight() {
        return height;
    }

    public int getY() {
        return y;
    }

    public void move(){
        this.y-=10;
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }

    public boolean isOutOfbounds(){
        return this.y<0;
    }
}
