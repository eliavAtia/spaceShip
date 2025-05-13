package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class Boss extends  Mob {
    private int speed = 1;
    private int direction = 1;
    private int type;
    private int maxHp;
    private boolean movingRight = true;
    private ArrayList<BossBullets> bullets = new ArrayList<>();
    private long lastTime = 0;
    private static final long SHOOT_SPAWN_DELAY = 3000;

    public Boss(int type) {
        this.type = type;
        setByType();
    }

    private void setByType(){
        switch (this.type){
            case 1:
                ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/Boss1.png")));
                setImage(icon.getImage());
                setWidth(icon.getIconWidth()*2);
                setHeight(icon.getIconHeight()*2);
                setX(getWidth()/2);
                setY(-getHeight());
                this.maxHp=70;
                setLife(maxHp);
        }

    }

    public void shoot() {
        long now = System.currentTimeMillis();
        if (now - lastTime > SHOOT_SPAWN_DELAY) {
            bullets.add(new BossBullets(getX() - 50,getY() - 20));
            lastTime = now;
        }
    }

    public void updateBullets(){
        long currentTime = System.currentTimeMillis();
        ArrayList<BossBullets> toRemove = new ArrayList<>();
        for (BossBullets bossBullets : bullets){
            bossBullets.moveDown();
            if (bossBullets.getY() >= 1000){
                toRemove.add(bossBullets);
            }
        }
        bullets.removeAll(toRemove);
        if (getY() > 70 && currentTime - lastTime >= SHOOT_SPAWN_DELAY){
            bullets.add(new BossBullets(getX() - getWidth()/2,getY() - getHeight()/2));
            lastTime = currentTime;
        }
    }




    public void draw(Graphics g) {
        g.drawImage(getImage(), getX() - getWidth()/2, getY() - getHeight()/2, getWidth(), getHeight(), null);
        g.fillRect((getX() - getHeight()/2) - 130, getY() - getHeight()/2-20, getWidth(), 10);
        g.setColor(Color.RED);
        g.fillRect((getX() - getHeight()/2) - 130, getY() - getHeight()/2-20, (int) ((double) getLife() / maxHp * getWidth()), 10);

        for (BossBullets bossBullets : bullets){
            g.drawImage(bossBullets.getImage(),bossBullets.getX() ,bossBullets.getY() ,150,300,null);
        }
    }



    public void moveDownWhenComing() {
        if (getY() < getHeight()/2 +40) {
            setY(getY()+1);
        }
    }


    public void moveSideways(int windowWidth,Player player) {
        if (player.getX()>getX()) {
            if(getX()<windowWidth-getWidth()/2+player.getWidth()/2){
                setX(getX()+1);
            }
        } else if (player.getX()<getX()){
            if (getX()>getWidth()){
                setX(getX()-1);;
            }
        }
    }

    public void moveDown() {
        setY(getY()+1);
    }


}

