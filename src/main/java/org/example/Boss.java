package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class Boss extends  Mob {
    private int type;
    private int maxHp;
    private ArrayList<BossBullets> bullets = new ArrayList<>();
    private long lastTime = 0;
    private static final long SHOOT_SPAWN_DELAY = 3000;
    private int windowWidth;

    public Boss(int type,int windowWidth) {
        this.type = type;
        this.windowWidth = windowWidth;
        setByType();
    }

    private void setByType(){
        switch (this.type){
            case 1:
                ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/Martian_Saucer.gif")));
                setImage(icon.getImage());
                setWidth(icon.getIconWidth()*2);
                setHeight(icon.getIconHeight()*2);
                setX(windowWidth/2);
                setY(getHeight()/2);
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
        if (getY() > getHeight()/2+40 && currentTime - lastTime >= SHOOT_SPAWN_DELAY){
            shootLeft();
            shootRight();
            lastTime = currentTime;
        }
    }

    private void shootLeft(){
        bullets.add(new BossBullets(getX() - getWidth()/2-15,getY() + getHeight()/2));
    }

    private void shootRight(){
        bullets.add(new BossBullets(getX() - getWidth()/2+15,getY() + getHeight()/2));
    }




    public void draw(Graphics g) {
        if(isShouldDrawMobImage()){
            g.drawImage(getImage(), getX() - getWidth()/2, getY() - getHeight()/2, getWidth(), getHeight(), null);
        }
        g.fillRect((getX() - getHeight()/2) - 130, getY() - getHeight()/2-20, getWidth(), 10);
        g.setColor(Color.RED);
        g.fillRect((getX() - getHeight()/2) - 130, getY() - getHeight()/2-20, (int) ((double) getLife() / maxHp * getWidth()), 10);
        for (BossBullets bossBullets : bullets){
            g.drawImage(bossBullets.getImage(),bossBullets.getX() ,bossBullets.getY() ,150,300,null);
        }
    }




    public void moveSideways(Player player) {
        if (getY() < getHeight()/2+40) {
            setY(getY()+2);
        }
        else {
            if ((player.getX()+player.getWidth()/2)>getX()) {
                if(getX()<windowWidth-getWidth()/2+player.getWidth()/2){
                    setX(getX()+1);
                }
            } else if ((player.getX()+player.getWidth()/2)<getX()){
                if (getX()>getWidth()){
                    setX(getX()-1);;
                }
            }
        }
    }


    public ArrayList<BossBullets> getBullets() {
        return bullets;
    }
}

