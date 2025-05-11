package org.example;

import java.awt.*;

public class Boost{
    private int x;
    private int y;
    private int width;
    private int height;
    private int type;
    private Image image;
    private Player player;
    public Boost(int type,Player player){
        this.player=player;
        this.type=type;
    }
    private void effect(){
        switch (type){
            case 1: player.setHp(player.getHp()+1);break;
            case 2: player.setBulletHeight(player.getBulletHeight()*2);
                    player.setBulletWidth(player.getBulletWidth()*2);
                    player.setBulletDamage(player.getBulletDamage()*2);
                    break;
            case 3: player.setShieldOn(true);

        }
    }
}
