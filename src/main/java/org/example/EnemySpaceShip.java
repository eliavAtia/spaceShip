package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class EnemySpaceShip {
    private int x;
    private int y;
    private int width;
    private int height;
    private Image image;
    ArrayList<EnemyBullets> enemyBullets = new ArrayList<>();
    private long lastShotTime = 0;
    private final long shotCooldown = 750;



    public EnemySpaceShip(int x, int y){
        this.x = x;
        this.y = y;
        this.width = 200;
        this.height = 175;
        try {
            ImageIcon imageIcon = new ImageIcon(getClass().getResource("/Images/Enemy.png"));
            this.image = imageIcon.getImage();
        } catch (Exception e) {
            System.out.println("שגיאה בטעינת התמונה: " + e.getMessage());
            this.image = null;
        }
    }

    public void shoot(){
        long currentTime = System.currentTimeMillis();
        if (this.y > 100 && currentTime - lastShotTime >= shotCooldown){
            enemyBullets.add(new EnemyBullets(this.x + this.width/3 , this.y+ this.height/2));
            lastShotTime = currentTime;
        }
    }

    public void bulletsMove(){
        ArrayList<EnemyBullets> toRemove = new ArrayList<>();
        for (EnemyBullets enemyBullet : enemyBullets){
            enemyBullet.moveDown();
            if (enemyBullet.getY() >= 1000){
                toRemove.add(enemyBullet);
            }
        }
        enemyBullets.removeAll(toRemove);
    }

    public void paint(Graphics graphics){
        if (this.image != null) {
            graphics.drawImage(this.image, this.x, this.y, this.width, this.height, null);
        } else {
            graphics.setColor(Color.RED);
            graphics.fillRect(this.x, this.y, this.width, this.height);
            graphics.setColor(Color.BLACK);
            graphics.drawString("תמונה חסרה", this.x + 10, this.y + this.height / 2);
        }
        for (EnemyBullets enemyBullet : enemyBullets){
            enemyBullet.paint(graphics);
        }

    }

    public void moveDown() {
        this.y += 13;
    }

    public void moveLeft(){
        this.x--;
    }

    public void moveRight(){
        this.x++;
    }

    boolean movingRight = true;
    public void moveSideways() {
        if (movingRight) {
            x += 2;
            if (x >= 800) {
                movingRight = false;
                this.y+=100;
            }
        } else {
            x -= 2;
            if (x <= 0) {
                movingRight = true;
                this.y+=100;
            }
        }
    }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public Image getImage() { return image; }
    public void setImage(Image image) { this.image = image; }
}

