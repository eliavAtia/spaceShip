package org.example;

import javax.swing.*;
import java.util.ArrayList;

public class BossBullets extends EnemyBullets {


    public BossBullets(int x, int y) {
        super(x, y);
        setImage(new ImageIcon(getClass().getResource("/Images/Boss1bullets.png")).getImage());
    }



}

