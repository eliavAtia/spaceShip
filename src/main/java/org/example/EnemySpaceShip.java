package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class EnemySpaceShip extends Mob {
    ArrayList<EnemyBullets> enemyBullets = new ArrayList<>();
    private long lastShotTime = 0;
    private static final long SHOOT_SPAWN_DELAY = 1500;
    private boolean movingRight;
    private int maxHp;
    public ArrayList<EnemyBullets> getEnemyBullets() {
        return enemyBullets;
    }
    public void setEnemyBullets(ArrayList<EnemyBullets> enemyBullets) {
        this.enemyBullets = enemyBullets;
    }
    private ImageIcon bulletImage;
    int windowWidth;


    public EnemySpaceShip(int x, int y){
        setX(x);
        setY(y);
        setHeight(200);
        setWidth(200);
        this.maxHp = 30;
        setLife(maxHp);
        this.movingRight = x >= windowWidth / 2;
        try {
            ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/Enemy.png")));
            setImage(imageIcon.getImage());
        } catch (Exception e) {
            System.out.println("שגיאה בטעינת התמונה: " + e.getMessage());
            setImage(null);
        }
        bulletImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/cow.png")));
    }

    public void updateBullets(){
        long currentTime = System.currentTimeMillis();
        ArrayList<EnemyBullets> toRemove = new ArrayList<>();
        for (EnemyBullets enemyBullet : enemyBullets){
            enemyBullet.moveDown(4);
            if (enemyBullet.getY() >= 1000){
                toRemove.add(enemyBullet);
            }
        }
        enemyBullets.removeAll(toRemove);
        if (getY() > 70 && currentTime - lastShotTime >= SHOOT_SPAWN_DELAY){
            enemyBullets.add(new EnemyBullets(getX() , getY()+getHeight()/2-20,60,60, bulletImage.getImage()));
            lastShotTime = currentTime;
        }
    }

    public void paint(Graphics graphics){
        if(isShouldDrawMobImage()){
            graphics.drawImage(getImage(), getX() - getWidth()/2, getY() - getHeight()/2, getWidth(), getHeight(), null);
        }
        graphics.setColor(Color.GRAY);
        graphics.fillRect(getX() - getWidth()/2 , getY() - getHeight()/2+50, getWidth(), 10);
        graphics.setColor(Color.RED);
        graphics.fillRect(getX() - getWidth()/2 , getY() - getHeight()/2+50, (int) ((double) getLife() / maxHp * getWidth()), 10);
        graphics.setColor(Color.BLACK);
        for (EnemyBullets enemyBullet : enemyBullets){
            enemyBullet.paint(graphics);

        }
    }

    public void moveDown() {
        setY(getY()+1);
    }

    public void moveSideways(int windowWidth) {
        this.windowWidth=windowWidth;
        if (movingRight) {
            setX(getX()+1);
            if (getX() >= windowWidth-100) {
                movingRight = false;
                setY(getY()+100);
            }
        } else {
            setX(getX()-1);;
            if (getX() <= 100) {
                movingRight = true;
                setY(getY()+100);
            }
        }
    }



}

