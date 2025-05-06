package org.example;

import javax.swing.*;
import java.awt.*;

public class Player  {
    private int x;
    private int y;
    private int width;
    private int height;
    private Image image1,image2,image3;
    private boolean rightPressed,leftPressed,upPressed,downPressed,spacePressed;
    private int hp;
    private final int maxHp=5;
    SoundPlayer lasers=new SoundPlayer("/Sounds/laserSound.wav");


    public Player(int x,int y,int width,int height){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.image1=new ImageIcon(getClass().getResource("/Images/spaceShip.png")).getImage();
        this.image2=new ImageIcon(getClass().getResource("/Images/sSpaceShip.png")).getImage();
        this.image3=new ImageIcon(getClass().getResource("/Images/bSpaceShip.png")).getImage();
        this.hp=5;
    }


    public void paint(Graphics graphics){
        if (upPressed){
            graphics.drawImage(this.image3,this.x,this.y,this.width,this.height,null);
        } else if (downPressed) {
            graphics.drawImage(this.image2,this.x,this.y,this.width,this.height,null);
        }
        else {
            graphics.drawImage(this.image1,this.x,this.y,this.width,this.height,null);
        }
    }


    public void playerMove(int vertical,int horizontal){
        x+=horizontal;
        y+=vertical;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Bullet shootRight(){
        lasers.playSound();
        return new Bullet(x+width -45,y,12,20);

    }

    public Bullet shootLeft(){
        lasers.playSound();
        return new Bullet(x+8,y,12,20);
    }
    public int getHp(){
        return hp;
    }
    public void setHp(int hp){
        this.hp=hp;
    }

    public int getMaxHp() {
        return maxHp;
    }
}
