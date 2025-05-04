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
    SoundPlayer lasers=new SoundPlayer("/Sounds/laserSound.wav");
    public Player(int x,int y,int width,int height){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.image1=new ImageIcon(getClass().getResource("/Images/spaceShip.png")).getImage();
        this.image2=new ImageIcon(getClass().getResource("/Images/sSpaceShip.png")).getImage();
        this.image3=new ImageIcon(getClass().getResource("/Images/bSpaceShip.png")).getImage();
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

    public void setHeight(int height) {
        this.height = height;
    }

    public void setImage1(Image image1) {
        this.image1 = image1;
    }

    public void setImage2(Image image2) {
        this.image2 = image2;
    }

    public void setImage3(Image image3) {
        this.image3 = image3;
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

    public void setWidth(int width) {
        this.width = width;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public int getHeight() {
        return height;
    }

    public Image getImage1() {
        return image1;
    }

    public Image getImage2() {
        return image2;
    }

    public Image getImage3() {
        return image3;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isUpPressed() {
        return upPressed;
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
    public void setSpacePressed(boolean spacePressed){
        this.spacePressed=spacePressed;
    }
    public Bullet shootRight(){
        lasers.playSound();
        return new Bullet(x+width -45,y,8,20);

    }
    public Bullet shootLeft(){
        lasers.playSound();
        return new Bullet(x+8,y,8,20);
    }
}
