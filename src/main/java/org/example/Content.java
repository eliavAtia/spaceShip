package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Content extends JPanel implements KeyListener {
    private Player player;
    private Image background;
    private boolean rightPressed,leftPressed,upPressed,downPressed,spacePressed;
    private  List<Bullet> bullets=new ArrayList<>();
    private ArrayList<Meteor> meteors;
    private ArrayList<Explosion> explosions = new ArrayList<>();
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 600;
    private static final long METEOR_SPAWN_DELAY = 3000;
    private long lastMeteorSpawnTime = 0;

    public Content(int x, int y, int width, int height) {
        this.setBounds(x, y, width, height);
        player = new Player(width / 2, height / 2, 100, 100);
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/space1.png"));
        this.background = icon.getImage();
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        this.meteors = new ArrayList<>();
        allDirections();
        bulletShoot();
        action();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        this.player.paint(g);
        for (Bullet b: bullets){
            b.draw(g);
        }
        for (Explosion explosion : explosions) {
            explosion.paint(g);
        }
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (Meteor meteor : meteors){
            AffineTransform oldTransform = g2d.getTransform();
            g2d.rotate(Math.toRadians(meteor.getRotationAngle()), meteor.getX(), meteor.getY());
            meteor.paint(g2d);
            g2d.setTransform(oldTransform);
            meteor.rotate();
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

    public synchronized void allDirections(){
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

    public synchronized void bulletShoot() {
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
                  synchronized (bullets){
                      for (Bullet b : bullets) {
                          b.move();
                      }
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

    private void createNewMeteor(){
        Random random = new Random();
        int[] startPoint = new int[9];
        int point = 85;
        for (int i = 0; i < startPoint.length; i++) {
            startPoint[i] = point;
            point+=100;
        }
        int x = startPoint[random.nextInt(9)];
        int y = -50;
        int size = random.nextInt(90,101);
        Meteor meteor = new Meteor(x,y,size);
        this.meteors.add(meteor);
    }

    public void updateMeteors() {
        long now = System.currentTimeMillis();
        ArrayList<Meteor> meteorsToRemove = new ArrayList<>();
        for (int i = 0; i < meteors.size(); i++) {
            Meteor meteor = meteors.get(i);
            if (meteor.getY() > WINDOW_HEIGHT) {
                meteorsToRemove.add(meteor);
            }
            else {
                meteor.moveDown();
            }
        }
        meteors.removeAll(meteorsToRemove);
        if (now - lastMeteorSpawnTime >= METEOR_SPAWN_DELAY) {
            createNewMeteor();
            lastMeteorSpawnTime = now;
        }
    }

    private void checkCollisionMeteorsBullets(){
        ArrayList<Meteor> meteorsToRemove = new ArrayList<>();
        ArrayList<Bullet> ballsToRemove = new ArrayList<>();
        OuterLoop:
        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < meteors.size(); j++) {
                Rectangle meteorRectangle = new Rectangle(
                        meteors.get(j).getX() - meteors.get(j).getWidth() / 2,
                        meteors.get(j).getY() - meteors.get(j).getHeight() / 2,
                        meteors.get(j).getWidth(),
                        meteors.get(j).getHeight()
                );
                Rectangle ballRectangle = new Rectangle(bullets.get(i).getX(), bullets.get(i).getY(), bullets.get(i).getWidth(), bullets.get(i).getHeight());
                if (meteorRectangle.intersects(ballRectangle)){
                    meteors.get(j).meteorHit();
                    ballsToRemove.add(this.bullets.get(i));
                    continue OuterLoop;
                }
                if(meteors.get(j).getLife()<=0){
                    meteorsToRemove.add(meteors.get(j));
                    explosions.add(new Explosion(meteors.get(j).getX(), meteors.get(j).getY()));
                }
            }
        }
        explosions.removeIf(Explosion::isFinished);
        explosions.forEach(Explosion::update);
        meteors.removeAll(meteorsToRemove);
        bullets.removeAll(ballsToRemove);
        bullets.removeIf(b -> b.getY() + b.getHeight() < 0);
    }

    private synchronized void action() {
        new Thread(() -> {
            while (true) {
                updateMeteors();
                checkCollisionMeteorsBullets();
                repaint();
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

}
