package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class EnemySpaceShip extends Mob {
    ArrayList<EnemyBullets> enemyBullets = new ArrayList<>();
    private long lastShotTime = 0;
    private static final long SHOOT_SPAWN_DELAY = 1500;
    private boolean movingRight = true;
    private int maxHp;
    public ArrayList<EnemyBullets> getEnemyBullets() {
        return enemyBullets;
    }
    public void setEnemyBullets(ArrayList<EnemyBullets> enemyBullets) {
        this.enemyBullets = enemyBullets;
    }



    public EnemySpaceShip(int x, int y){
        setX(x);
        setY(y);
        setHeight(200);
        setWidth(200);
        this.maxHp = 30;
        setLife(maxHp);
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
        if (getY() > 70 && currentTime - lastShotTime >= SHOOT_SPAWN_DELAY){
            enemyBullets.add(new EnemyBullets(getX() , getY()+10));
            lastShotTime = currentTime;
        }
    }

    public void paint(Graphics graphics){
        if(isShouldDrawMobImage()){
            graphics.drawImage(getImage(), getX() - getWidth()/2, getY() - getHeight()/2, getWidth(), getHeight(), null);
        }
        graphics.fillRect(getX() - getWidth()/2 , getY() - getHeight()/2+50, getWidth(), 10);
        graphics.setColor(Color.RED);
        graphics.fillRect(getX() - getWidth()/2 , getY() - getHeight()/2+50, (int) ((double) getLife() / maxHp * getWidth()), 10);
        for (EnemyBullets enemyBullet : enemyBullets){
            enemyBullet.paint(graphics);
        }
    }

    public void moveDown() {
        setY(getY()+1);
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



}

