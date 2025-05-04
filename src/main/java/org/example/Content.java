package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class Content extends JPanel implements KeyListener {
    private Player player;
    private Image background;
    private boolean rightPressed,leftPressed,upPressed,downPressed,spacePressed;
    private  List<Bullet> bullets=new ArrayList<>();

    public Content(int x, int y, int width, int height) {
        this.setBounds(x, y, width, height);
        player = new Player(width / 2, height / 2, 100, 100);
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/space1.png"));
         this.background = icon.getImage();
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        allDirections();
        bulletShoot();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        this.player.paint(g);
        for (Bullet b: bullets){
            b.draw(g);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_D){
            rightPressed=true;
            this.player.setRightPressed(rightPressed);
        }
        if (e.getKeyCode()==KeyEvent.VK_A){
            leftPressed=true;
            this.player.setLeftPressed(leftPressed);
        }
        if (e.getKeyCode()==KeyEvent.VK_W){
            upPressed=true;
            this.player.setUpPressed(upPressed);
        }
        if (e.getKeyCode()==KeyEvent.VK_S){
            downPressed=true;
            this.player.setDownPressed(downPressed);
        }
        if (e.getKeyCode()==KeyEvent.VK_SPACE){
            spacePressed=true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_D){
            rightPressed=false;
            this.player.setRightPressed(rightPressed);
        }
        if (e.getKeyCode()==KeyEvent.VK_A){
            leftPressed=false;
            this.player.setLeftPressed(leftPressed);
        }
        if (e.getKeyCode()==KeyEvent.VK_W){
            upPressed=false;
            this.player.setUpPressed(upPressed);
        }
        if (e.getKeyCode()==KeyEvent.VK_S){
            downPressed=false;
            this.player.setDownPressed(downPressed);
        }
        if (e.getKeyCode()==KeyEvent.VK_SPACE){
            spacePressed=false;
        }
    }
    public void allDirections(){
        new Thread(()->{
            while (true){
                int vertical=0;
                int horizontal=0;
                if (rightPressed&&this.player.getX()<=getWidth()- player.getWidth()){
                    horizontal=5;
                }
                if (leftPressed&&this.player.getX()>=0){
                    horizontal=-5;
                }
                if (upPressed&&this.player.getY()>=0){
                    vertical=-4 ;
                }
                if (downPressed&&this.player.getY()<=getHeight()-player.getHeight()){
                    vertical=4;
                }
                player.playerMove(vertical,horizontal);
                repaint();
                try {
                    Thread.sleep(18);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }
    public void bulletShoot() {
        new Thread(() -> {
            long lastShot = System.currentTimeMillis();
            final int SHOOT_DELAY = 300;
            while (true) {
                long now = System.currentTimeMillis();
                if (spacePressed &&( now - lastShot > SHOOT_DELAY)) {
                    synchronized (bullets) {
                        bullets.add(player.shootRight());
                        bullets.add(player.shootLeft());
                    }
                }
                try {
                    Thread.sleep(SHOOT_DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            final int BULLET_SPEED = 10;
            while (true) {
                synchronized (bullets) {
                    for (Bullet b : bullets) {
                    b.move();
                }
                bullets.removeIf(b -> b.getY() + b.getHeight() < 0);
                }
                repaint();
                try {
                    Thread.sleep(100 / BULLET_SPEED);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
