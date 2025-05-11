package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Boost{
    private int x;
    private int y;
    private int width;
    private int height;
    private int type;
    private Image image;
    private Player player;
    public Boost(int x,int y,int type,Player player){
        this.player=player;
        this.type=type;
        meathelImages();
        this.width=40;
        this.height=40;
        this.x=x;
        this.y=y;
    }
    public void effect(){
        boolean[] whatsOn= player.getBoostsThatAreOn();
        if (whatsOn[type-1]){
            return;
        }
        switch (type){
            case 1: if (player.getMaxHp()!=player.getHp()) {
                player.setHp(player.getHp() + 1);
            }break;
            case 2: player.setBulletHeight(player.getBulletHeight()*2);
                    player.setBulletWidth(player.getBulletWidth()*2);
                    player.setBulletDamage(player.getBulletDamage()*2);
                    whatsOn[1]=true;break;
            case 3: player.setShieldOn(true);whatsOn[2]=true;break;
            case 4: player.setXP(player.getXP()*4);whatsOn[3]=true;break;
        }
        player.setBoostsThatAreOn(whatsOn);
        new Thread(()->{
            try{
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            switch (type){
                case 2: player.setBulletHeight(player.getBulletHeight()/2);
                    player.setBulletWidth(player.getBulletWidth()/2);
                    player.setBulletDamage(player.getBulletDamage()/2);
                    whatsOn[1]=false;break;
                case 3: player.setShieldOn(false);whatsOn[2]=false;break;
                case 4: player.setXP(player.getXP()/4);whatsOn[3]=false;break;
            }
        }).start();
        player.setBoostsThatAreOn(whatsOn);
    }
    private void meathelImages(){
        switch (type){
            case 1: this.image=new ImageIcon(getClass().getResource("/Images/minecraftFullHeart.png")).getImage();break;
            case 2:this.image=new ImageIcon(getClass().getResource("/Images/sword.png")).getImage();break;
            case 3: this.image=new ImageIcon(getClass().getResource("/Images/shield.png")).getImage();break;
            case 4: this.image=new ImageIcon(getClass().getResource("/Images/xpIcon.png")).getImage();break;
        }
    }
    public void draw(Graphics g){
        g.drawImage(image,x,y,width,height,null);
    }
    public void move(){
        this.y++;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
