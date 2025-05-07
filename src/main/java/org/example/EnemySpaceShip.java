package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class EnemySpaceShip extends Mob {
    ArrayList<EnemyBullets> enemyBullets = new ArrayList<>();
    private long lastShotTime = 0;
    private static final long SHOOT_SPAWN_DELAY = 1500;
    boolean movingRight = true;


    public EnemySpaceShip(int x, int y){
        setX(x);
        setY(y);
        setHeight(150);
        setWidth(150);
        setLife(5);
        try {
            ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/Enemy.png")));
            setImage(imageIcon.getImage());
        } catch (Exception e) {
            System.out.println("שגיאה בטעינת התמונה: " + e.getMessage());
            setImage(null);
        }
    }

    public void updateBullets(){
        long currentTime = System.currentTimeMillis();
        ArrayList<EnemyBullets> toRemove = new ArrayList<>();
        for (EnemyBullets enemyBullet : enemyBullets){
            enemyBullet.moveDown();
            if (enemyBullet.getY() >= 1000){
                toRemove.add(enemyBullet);
            }
        }
        enemyBullets.removeAll(toRemove);
        if (getY() > 100 && currentTime - lastShotTime >= SHOOT_SPAWN_DELAY){
            enemyBullets.add(new EnemyBullets(getX() + getWidth()/3 , getY()+ getHeight()/2));
            lastShotTime = currentTime;
        }
    }

    public void paint(Graphics graphics){
        if (getImage() != null) {
            graphics.drawImage(getImage(), getX(), getY(), getWidth(), getHeight(), null);
        } else {
            graphics.setColor(Color.RED);
            graphics.fillRect(getX(), getY(), getWidth(), getHeight());
            graphics.setColor(Color.BLACK);
            graphics.drawString("תמונה חסרה", getX() + 10, getY() + getHeight() / 2);
        }
        for (EnemyBullets enemyBullet : enemyBullets){
            enemyBullet.paint(graphics);
        }

    }

    public void moveDown() {
        setY(getY()+20);
    }

    public void moveSideways(int windowWidth) {
        if (movingRight) {
            setX(getX()+1);
            if (getX() >= windowWidth-100) {
                movingRight = false;
                setY(getY()+100);
            }
        } else {
            setX(getX()-1);;
            if (getX() <= 0) {
                movingRight = true;
                setY(getY()+100);
            }
        }
    }

    public ArrayList<EnemyBullets> getEnemyBullets() {
        return enemyBullets;
    }

    public void setEnemyBullets(ArrayList<EnemyBullets> enemyBullets) {
        this.enemyBullets = enemyBullets;
    }
}

