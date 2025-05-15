package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class Boss extends  Mob {
    private int type;
    private int maxHp;
    private List<EnemyBullets> bullets;
    private long lastTime = 0;
    private long SHOOT_SPAWN_DELAY;
    private int windowWidth;
    private int windowHeight;
    private ImageIcon bulletImage;
    private SoundPlayer bossShot;
    private int speed;

    public Boss(int level,int type,int windowWidth,int windowHeight) {
        this.bullets = new CopyOnWriteArrayList<EnemyBullets>();
        this.type = type;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.maxHp=180+20*level;
        this.SHOOT_SPAWN_DELAY=1500-(level-1)*75;
        this.speed=10+(level-1);
        setByType();
    }

    @Override
    public void mobHit(int damage) {
        setLife(getLife()-damage);
        setGotHit(true);
    }

    private void setByType(){
        switch (this.type){
            case 1:
                this.bossShot = new SoundPlayer("/Sounds/laser-zap-90575.wav");
                ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/Martian_Saucer.gif")));
                bulletImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/ChatGPT Image May 14, 2025, 03_48_44 PM.png")));
                setImage(icon.getImage());
                setWidth(icon.getIconWidth()*2);
                setHeight(icon.getIconHeight()*2);
                setX(windowWidth/2);
                setY(getHeight()/2);
                setLife(maxHp);
        }

    }


    public void updateBullets(){
        long currentTime = System.currentTimeMillis();
        ArrayList<EnemyBullets> toRemove = new ArrayList<>();
        for (EnemyBullets bossBullets : bullets){
            bossBullets.moveDown(10);
            if (bossBullets.getY() >= windowHeight){
                toRemove.add(bossBullets);
            }
        }
        bullets.removeAll(toRemove);
        if ( (currentTime - lastTime >= SHOOT_SPAWN_DELAY)){
            bossShot.playSound();
            bullets.add(new EnemyBullets(getX() +110 , getY()+getHeight()/2+10,20,104, bulletImage.getImage()));
            bullets.add(new EnemyBullets(getX() -110 , getY()+getHeight()/2+10,20,104, bulletImage.getImage()));
            bullets.add(new EnemyBullets(getX() , getY()+getHeight()/2+10,20,104, bulletImage.getImage()));
            lastTime = currentTime;
        }
    }


    public void draw(Graphics g) {
        if(isShouldDrawMobImage()){
            g.drawImage(getImage(), getX() - getWidth()/2, getY() - getHeight()/2, getWidth(), getHeight(), null);
        }
        g.fillRect((getX() - getHeight()/2) - 130, getY() - getHeight()/2-20, getWidth(), 10);
        g.setColor(Color.RED);
        g.fillRect((getX() - getHeight()/2) - 130, getY() - getHeight()/2-20, (int) ((double) getLife() / maxHp * getWidth()), 10);
        for (EnemyBullets enemyBullets:bullets){
            enemyBullets.paint(g);
        }
    }


    public void moveSideways(Player player) {
        if (getY() < getHeight()/2+40) {
            setY(getY()+5);
        }
        else {
            if ((player.getX()+player.getWidth()/2)>getX()+1) {
                if(getX()<windowWidth-getWidth()/2+player.getWidth()/2){
                    setX(getX()+speed);
                }
            } else if ((player.getX()+player.getWidth()/2)<getX()-1){
                if (getX()>getWidth()/2-player.getWidth()/2){
                    setX(getX()-speed);;
                }
            }
        }
    }


    public java.util.List<EnemyBullets> getBullets() {
        return bullets;
    }
}

