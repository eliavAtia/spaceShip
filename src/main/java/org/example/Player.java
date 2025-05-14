package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class Player  {
    private int x;
    private int y;
    private int width;
    private int height;
    private Image image1,image2,image3;
    private boolean rightPressed,leftPressed,upPressed,downPressed,spacePressed;
    private int hp;
    private SoundPlayer lasers=new SoundPlayer("/Sounds/laserSound.wav");
    private boolean shieldOn;
    private boolean shouldDrawPlayerImage = true;
    private List<Bullet> bullets;
    private long lastBulletSpawnTime = 0,BULLET_SPAWN_DELAY = 300;
    private int bulletWidth,bulletHeight;
    private int bulletDamage;
    private int XP;
    private boolean[] boostsThatAreOn;
    private boolean boostShieldOn;

    public Player(int x,int y,int width,int height){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.image1=new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/spaceShip.png"))).getImage();
        this.image2=new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/sSpaceShip.png"))).getImage();
        this.image3=new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/bSpaceShip.png"))).getImage();
        this.hp=5;
        this.shieldOn = false;
        this.bullets = new CopyOnWriteArrayList<>();
        this.bulletWidth=40;
        this.bulletHeight=60;
        this.bulletDamage=1;
        this.XP=1;
        this.boostsThatAreOn=new boolean[4];
    }

    public void paint(Graphics graphics){
        if(shouldDrawPlayerImage){
            if (upPressed){
                graphics.drawImage(this.image3,this.x,this.y,this.width,this.height,null);
            } else if (downPressed) {
                graphics.drawImage(this.image2,this.x,this.y,this.width,this.height,null);
            }
            else {
                graphics.drawImage(this.image1,this.x,this.y,this.width,this.height,null);
            }
        }
        for (Bullet b: bullets){
            b.draw(graphics);
        }
    }

    public void startFlashingEffect() {
        new Thread(() -> {
            while (shieldOn) {
                shouldDrawPlayerImage = !shouldDrawPlayerImage;
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            shouldDrawPlayerImage = true;
        }).start();
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
        return new Bullet(x+width -45,y,bulletWidth,bulletHeight,this.bulletDamage);

    }

    public Bullet shootLeft(){
        lasers.playSound();
        return new Bullet(x+3,y,bulletWidth,bulletHeight,this.bulletDamage);
    }

    public int getHp(){
        return hp;
    }

    public void setHp(int hp){
        this.hp=hp;
    }

    public int getMaxHp() {
        int maxHp = 5;
        return maxHp;
    }

    public boolean isShieldOn() {
        return shieldOn;
    }

    public void setShieldOn(boolean shieldOn) {
        this.shieldOn = shieldOn;
        if (shieldOn) {
            startFlashingEffect();
        } else {
            shouldDrawPlayerImage = true;
        }
    }

    public void updateBullets(){
        long now = System.currentTimeMillis();
        ArrayList<Bullet> bulletsToRemove = new ArrayList<>();
        for (Bullet b:bullets) {
            if (b.getY() < 0) {
                bulletsToRemove.add(b);
            }
            else {
                b.move();
            }
        }
        bullets.removeAll(bulletsToRemove);
        if (spacePressed &&( now - lastBulletSpawnTime >= BULLET_SPAWN_DELAY)) {
            bullets.add(this.shootRight());
            bullets.add(this.shootLeft());
            lastBulletSpawnTime = now;
        }
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(List<Bullet> bullets) {
        this.bullets = bullets;
    }

    public void setSpacePressed(boolean spacePressed) {
        this.spacePressed = spacePressed;
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

    public int getBulletHeight() {
        return bulletHeight;
    }

    public void setBulletHeight(int bulletHeight) {
        this.bulletHeight = bulletHeight;
    }

    public int getBulletWidth() {
        return bulletWidth;
    }

    public void setBulletWidth(int bulletWidth) {
        this.bulletWidth = bulletWidth;
    }

    public int getBulletDamage() {
        return bulletDamage;
    }

    public void setBulletDamage(int bulletDamage) {
        this.bulletDamage = bulletDamage;
    }

    public int getXP() {
        return XP;
    }

    public void setXP(int XP) {
        this.XP = XP;
    }

    public boolean[] getBoostsThatAreOn() {
        return boostsThatAreOn;
    }

    public void setBoostsThatAreOn(boolean[] boostsThatAreOn) {
        this.boostsThatAreOn = boostsThatAreOn;
    }

    public boolean isBoostShieldOn() {
        return boostShieldOn;
    }

    public void setBoostShieldOn(boolean boostShieldOn) {
        this.boostShieldOn = boostShieldOn;
    }
}
