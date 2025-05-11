package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Content extends JPanel implements KeyListener {
    private Image background, gameOver, heartFull, heartEmpty;
    private boolean isGameOver;
    private SoundPlayer gameOverSound;
    private int score;
    private Player player;
    private List<Meteor> meteors;
    private List<Explosion> explosions;
    private List<EnemySpaceShip> enemySpaceShips;
    private List<Boss> bosses;
    private long lastMeteorSpawnTime = 0;
    private boolean rightPressed,leftPressed,upPressed,downPressed,spacePressed;
    private static final long METEOR_SPAWN_DELAY = 1500;
    private JLabel scoreLabel;
    private static Font customFont;
    private List<Boost> boosts;
    private boolean powerOn,xpOn,shieldBoostOn;
//    private boolean boss1Defeated,boss3Defeated,boss2Defeated, bossActivated;


    public Content(int x, int y, int width, int height) {
        this.setBounds(x, y, width, height);
        imageSoundBuilder();
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        this.setLayout(null);
        player = new Player(width / 2, height / 2, 80, 80);
        mobBuilder();
        scoreBuilder();
        gameCourse();
    }


    private void imageSoundBuilder(){
        this.background = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/backgroundGif.gif"))).getImage();
        this.heartFull=new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/minecraftFullHeart.png"))).getImage();
        this.heartEmpty=new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/minecraftEmptyHeart.png"))).getImage();
        this.gameOver=new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/gameOver.png"))).getImage();
        this.gameOverSound=new SoundPlayer("/Sounds/gameOver.wav");
    }

    private void mobBuilder(){
        this.bosses = new CopyOnWriteArrayList<>();
        this.meteors = new CopyOnWriteArrayList<>();
        this.explosions = new CopyOnWriteArrayList<>();
        this.enemySpaceShips=new CopyOnWriteArrayList<>();
        this.boosts=new CopyOnWriteArrayList<>();
    }

    private void scoreBuilder(){
        this.score = 0;
        this.scoreLabel = new JLabel("Score: " + score);
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(getClass().getResourceAsStream("/fonts/PressStart2P-Regular.ttf"))).deriveFont(24f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            this.scoreLabel.setFont(customFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            customFont = new Font("Arial", Font.PLAIN, 24);
            this.scoreLabel.setFont(customFont);
        }
        this.scoreLabel.setForeground(Color.WHITE);
        this.scoreLabel.setBounds(20, 65, 500, 30);
        scoreLabel.setForeground(new Color(243, 202, 0));
        this.add(scoreLabel);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        if (!this.isGameOver) {
            this.player.paint(g);
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
            for (int i = 0; i < player.getMaxHp(); i++) {
                if (player.getHp()>i){
                    g.drawImage(heartFull,20+i*40,20,32,32,this);
                }
                else {
                    g.drawImage(heartEmpty,20+i*40,20,32,32,this);
                }
            }
            for (Boss boss : bosses) {
                boss.draw(g);
                boss.move();
            }
            for (EnemySpaceShip enemySpaceShip : enemySpaceShips) {
                enemySpaceShip.paint(g);
            }
            scoreLabel.setText("Score: " +score);

            for (Boost boost: boosts){
                boost.draw(g);
            }
        }
        else {
            g.drawImage(gameOver,this.getWidth()/2-250,this.getHeight()/2-250,500,500,this);
            g.setFont(Content.customFont.deriveFont(32f));
            g.setColor(Color.WHITE);
            String scoreText = "Your Score: " + score;
            int textWidth = g.getFontMetrics().stringWidth(scoreText);
            g.drawString(scoreText, this.getWidth() / 2 - textWidth / 2, this.getHeight() / 2 + 200);
            scoreLabel.setVisible(false);
        }
        for (int i = 1; i < player.getBoostsThatAreOn().length; i++) {
            if (player.getBoostsThatAreOn()[i]){
                g.drawImage(new Boost(0,0,i+1,player).getImage(),20+(i-1)*40,100,30,30,null);
            }
        }
        this.repaint();
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
            this.player.setSpacePressed(spacePressed);
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
            this.player.setSpacePressed(spacePressed);
        }
    }

    public synchronized void allDirections(){
        new Thread(()->{
            while (!isGameOver){
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
                    Thread.sleep(13);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void createNewMeteor(){
        Random random = new Random();
        int[] startPoint = new int[this.getWidth()/100 -1];
        int point = 85;
        for (int i = 0; i < startPoint.length; i++) {
            startPoint[i] = point;
            point+=100;
        }
        int x = startPoint[random.nextInt(startPoint.length)];
        int y = -50;
        int size = random.nextInt(90,101);
        Meteor meteor = new Meteor(x,y,size);
        this.meteors.add(meteor);
    }

    public void updateMeteors() {
        long now = System.currentTimeMillis();
        ArrayList<Meteor> meteorsToRemove = new ArrayList<>();
        for (Meteor meteor: this.meteors) {
            if (meteor.getY() > getHeight()) {
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

    private List<Mob> checkBulletsCollision(List<Mob> mobs, int pointsPerHit,int oneToHowMuchBoostChance){
        ArrayList<Mob> mobsToRemove = new ArrayList<>();
        ArrayList<Bullet> bulletsToRemove = new ArrayList<>();
        OuterLoop:
        for (Bullet bullet: player.getBullets()) {
            Random random=new Random();
            int num=random.nextInt(1,oneToHowMuchBoostChance);
            for (Mob mob:mobs) {
                Rectangle mobRectangle = new Rectangle(
                        mob.getX() - mob.getWidth() / 2,
                        mob.getY() - mob.getHeight() / 2,
                        mob.getWidth(),
                        mob.getHeight()-40
                );
                Rectangle bulletRectangle = new Rectangle(
                        bullet.getX(),
                        bullet.getY(),
                        bullet.getWidth(),
                        bullet.getHeight()
                );
                if (mobRectangle.intersects(bulletRectangle)) {
                    mob.setLife(mob.getLife()-player.getBulletDamage());
                    if(mob.getLife() <= 0){
                        score += pointsPerHit;
                        mobsToRemove.add(mob);
                        explosions.add(new Explosion(mob.getX(), mob.getY()));
                        if (num==1){
                            int type=random.nextInt(1,5);
                            Boost boost=new Boost(mob.getX(),mob.getY(),type,player);
                            boolean exists = false;
                            for (Boost b : boosts) {
                                if (b.getX() == mob.getX() && b.getY() == mob.getY()) {
                                    exists = true;
                                    break;
                                }
                            }
                            if (!exists&&!player.getBoostsThatAreOn()[type-1]) {
                                boosts.add(boost);
                            }
                        }
                    }
                    bulletsToRemove.add(bullet);
                    continue OuterLoop;
                }
            }
            if (bullet.getY()+bullet.getHeight()<0){
                bulletsToRemove.add(bullet);
            }

        }
        List<Bullet> newBullets = player.getBullets();
        newBullets.removeAll(bulletsToRemove);
        player.setBullets(newBullets);
        return mobsToRemove;
    }

    private void updateExplosions(){
        explosions.removeIf(Explosion::isFinished);
        explosions.forEach(Explosion::update);

    }

    private List<Mob> checkPlayerCollision(List<Mob> mobs){
        ArrayList<Mob> mobsToRemove = new ArrayList<>();
        Rectangle playerRectangle=new Rectangle(
                player.getX()+20,
                player.getY()+20,
                player.getWidth()-40,
                player.getHeight()-50
        );
        for (Mob mob:mobs) {
            Rectangle mobRectangle = new Rectangle(
                    mob.getX() - mob.getWidth()/2,
                    mob.getY() - mob.getHeight()/2,
                    mob.getWidth(),
                    mob.getHeight()
            );
            if (player.isShieldOn()){
                continue;
            }
            if (mobRectangle.intersects(playerRectangle)){
                player.setShieldOn(true);
                player.setHp(player.getHp()-1);
                explosions.add(new Explosion(mob.getX(),mob.getY()));
                mobsToRemove.add(mob);
            }
            if (player.getHp()<=0){
                this.isGameOver=true;
                gameOverSound.playSound();
            }
            new Thread(()->{
                try{
                    if (player.isShieldOn()){
                        Thread.sleep(3000);
                        player.setShieldOn(false);
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }).start();
        }
        return mobsToRemove;
    }

    private void updateEnemySpaceShips(){
        ArrayList<EnemySpaceShip> toRemove = new ArrayList<>();
        for (EnemySpaceShip enemySpaceShip : enemySpaceShips) {
            if (enemySpaceShip.getY() > getHeight()) {
                toRemove.add(enemySpaceShip);
            } else {
                if(enemySpaceShip.getY()<=70){
                    enemySpaceShip.moveDown();
                }
                else {
                    enemySpaceShip.updateBullets();
                    enemySpaceShip.moveSideways(getWidth());
                }
            }

        }
        enemySpaceShips.removeAll(toRemove);
    }

    private void infiniteScoreAdd(){
        new Thread(()->{
            while (!isGameOver) {
                score+=player.getXP();
                try {
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private synchronized void action() {
        final int[] enemyRespawn = {2000};
        new Thread(() -> {
            while (!isGameOver) {
                player.updateBullets();
                updateExplosions();
                updateMeteors();
                updateEnemySpaceShips();
                updateBoosts();
                this.meteors.removeAll(checkBulletsCollision(new ArrayList<Mob>(meteors),100,10));
                this.meteors.removeAll(checkPlayerCollision(new ArrayList<Mob>(meteors)));
                this.enemySpaceShips.removeAll(checkBulletsCollision(new ArrayList<Mob>(enemySpaceShips),200,4));
                this.enemySpaceShips.removeAll(checkPlayerCollision(new ArrayList<Mob>(enemySpaceShips)));
                checkPlayerBoostCollision();
                for (EnemySpaceShip enemySpaceShip:enemySpaceShips){
                    ArrayList <EnemyBullets> newBullets = enemySpaceShip.getEnemyBullets();
                    newBullets.removeAll(checkPlayerCollision(new ArrayList<Mob>(newBullets)));
                    enemySpaceShip.setEnemyBullets(newBullets);
                }
                if (score > enemyRespawn[0]) {
                    enemySpaceShips.add(new EnemySpaceShip(getWidth()/2,-15));
                    enemyRespawn[0] +=2000;
                }
                repaint();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private void gameCourse(){
        allDirections();
        infiniteScoreAdd();
        action();
//        BossActivation();
    }
    private void updateBoosts(){
        ArrayList<Boost> boostsToRemove = new ArrayList<>();
        for (Boost boost: this.boosts) {
            if (boost.getY() > getHeight()) {
                boostsToRemove.add(boost);
            }
            else {
                boost.move();
            }
        }
        boosts.removeAll(boostsToRemove);
    }

    private void checkPlayerBoostCollision(){
        Rectangle playerRectangle=new Rectangle(
                player.getX()+20,
                player.getY()+20,
                player.getWidth()-40,
                player.getHeight()-50
        );
        for (Boost boost:boosts) {
            Rectangle boostRectangle = new Rectangle(
                    boost.getX() - boost.getWidth()/2,
                    boost.getY() - boost.getHeight()/2,
                    boost.getWidth(),
                    boost.getHeight()
            );
            if (boostRectangle.intersects(playerRectangle)){
                boost.effect();
                boosts.remove(boost);
            }
//            new Thread(()->{
//                try{
//                    Thread.sleep(3000);
//                }
//                catch (InterruptedException e){
//                    e.printStackTrace();
//                }
//            }).start();
        }
    }

//    private void checkBulletBossCollision() {
//        if (bosses.isEmpty()) return;
//        Rectangle bossRect = bosses.get(0).getBounds();
//        List<Bullet> bulletsToRemove = new ArrayList<>();
//        for (Bullet b : bullets) {
//            Rectangle bulletRect = new Rectangle(b.getX(), b.getY(), b.getWidth(), b.getHeight());
//            if (bossRect.intersects(bulletRect) && !boss1Defeated) {
//                bosses.get(0).mobHit();
//                bulletsToRemove.add(b);
//                if (bosses.get(0).getLife() <= 0) {;
//                    explosions.add(new Explosion(bosses.get(0).getX(), bosses.get(0).getY()));
//                    bosses.remove(bosses.get(0));
//                    this.boss1Defeated = true;
//                    bossActivated = false;
//                }
//            }
//        }

//        bullets.removeAll(bulletsToRemove);

//    }
//    private void BossActivation(){
//        new Thread(()-> {
//            while (!isGameOver) {
//                checkBulletBossCollision();
//                if (score > 5000 && bosses.isEmpty() && !boss1Defeated) {
//                    bosses.add(new Boss(1));
//                    bossActivated = true;
//                    meteors.removeAll(meteors);
//                    enemySpaceShips.removeAll(enemySpaceShips);
//                }
//                try {
//                    Thread.sleep(60);
//                }catch (InterruptedException e){
//                    e.printStackTrace();
//                }
//            }
//        }).start();

//    }
}
