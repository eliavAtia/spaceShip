package org.example;

import java.awt.*;

public class Mob {
    private int x;
    private int y;
    private int width;
    private int height;
    private int life = 0;
    private Image image;
    private boolean gotHit;
    private boolean shouldDrawMobImage=true;

    public void startFlashingEffect() {
        new Thread(() -> {
            if(gotHit) {
                shouldDrawMobImage = false;
                try {
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            shouldDrawMobImage = true;
            gotHit=false;
        }).start();
    }

    public boolean isShouldDrawMobImage() {
        return shouldDrawMobImage;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getLife() {
        return life;
    }

    public Image getImage() {
        return image;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void mobHit(int damage){
        this.life-=damage;
        this.gotHit=true;
        startFlashingEffect();
    }

    public void setGotHit(boolean gotHit) {
        this.gotHit = gotHit;
    }
}