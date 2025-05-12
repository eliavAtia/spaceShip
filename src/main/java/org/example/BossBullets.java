package org.example;

import javax.swing.*;
import java.util.ArrayList;

public class BossBullets extends EnemyBullets {


    public BossBullets(int x, int y) {
        super(x, y);
        setImage(new ImageIcon(getClass().getResource("/Images/Boss1bullets.png")).getImage());
    }



}
//private void bossActivation(){
//    new Thread(()-> {
//        while (!isGameOver) {
//            this.bosses.removeAll(checkBulletsCollision(new ArrayList<Mob>(bosses),1000,2));
//            if (score > 400 && bosses.isEmpty() && !boss1Defeated) {
//                bosses.add(new Boss(1));
//                bossActivated = true;
//                meteors.removeAll(meteors);
//                enemySpaceShips.removeAll(enemySpaceShips);
//            }
//            try {
//                Thread.sleep(60);
//            }catch (InterruptedException e){
//                e.printStackTrace();
//            }
//        }
//
//    }).start();
//
//}
//boss.moveDownWhenComing();
//            boss.moveSideways(getWidth());
//        if (boss.getY() > 100){
//        boss.shoot();
//                boss.updateBullets();
//            }
