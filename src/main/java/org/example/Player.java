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
    SoundPlayer lasers=new SoundPlayer("/Sounds/laserSound.wav");
    private boolean shieldOn;
    private boolean shouldDrawPlayerImage = true;
    private List<Bullet> bullets;
    private long lastBulletSpawnTime = 0,BULLET_SPAWN_DELAY = 300;
    private int bulletWidth,bulletHeight;
    private int bulletDamage;
    private int XP;


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
        return new Bullet(x+width -45,y,40,60,this.bulletDamage);

    }

    public Bullet shootLeft(){
        lasers.playSound();
        return new Bullet(x+8,y,40,60,this.bulletDamage);
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

    public long getBULLET_SPAWN_DELAY() {
        return BULLET_SPAWN_DELAY;
    }

    public void setBULLET_SPAWN_DELAY(long BULLET_SPAWN_DELAY) {
        this.BULLET_SPAWN_DELAY = BULLET_SPAWN_DELAY;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(List<Bullet> bullets) {
        this.bullets = bullets;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Image getImage1() {
        return image1;
    }

    public void setImage1(Image image1) {
        this.image1 = image1;
    }

    public Image getImage2() {
        return image2;
    }

    public void setImage2(Image image2) {
        this.image2 = image2;
    }

    public Image getImage3() {
        return image3;
    }

    public void setImage3(Image image3) {
        this.image3 = image3;
    }

    public SoundPlayer getLasers() {
        return lasers;
    }

    public void setLasers(SoundPlayer lasers) {
        this.lasers = lasers;
    }

    public long getLastBulletSpawnTime() {
        return lastBulletSpawnTime;
    }

    public void setLastBulletSpawnTime(long lastBulletSpawnTime) {
        this.lastBulletSpawnTime = lastBulletSpawnTime;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isShouldDrawPlayerImage() {
        return shouldDrawPlayerImage;
    }

    public void setShouldDrawPlayerImage(boolean shouldDrawPlayerImage) {
        this.shouldDrawPlayerImage = shouldDrawPlayerImage;
    }

    public boolean isSpacePressed() {
        return spacePressed;
    }

    public void setSpacePressed(boolean spacePressed) {
        this.spacePressed = spacePressed;
    }

    public boolean isUpPressed() {
        return upPressed;
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
}
