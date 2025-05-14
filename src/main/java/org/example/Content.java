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
    private ImageIcon pauseButtonImage, goBackImage;
    private boolean isGameOver, isPaused;
    private SoundPlayer gameOverSound, backGroundSound, bossTheme;
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
    private JButton goBackToMenu, pauseButton;
    private StartScreen menu;
    private JFrame frame;
    private Thread directionThread,scoreThread,actionThread;
    private List<Boost> boosts;
    private String playerName;
    private Leaderboard leaderboard;
    private boolean isSaved;
    private boolean bossActive;
    private int difficultyLevel=1;
    private Random random;




    //builders
    public Content(String playerName,JFrame frame, int x, int y, int width, int height, StartScreen parentPanel) {
        random=new Random();
        this.setBounds(x, y, width, height);
        this.playerName=playerName;
        imageSoundBuilder();
        this.frame = frame;

        this.menu = parentPanel;

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);
        this.setLayout(null);
        player = new Player(width / 2, height / 2, 80, 80);
        mobBuilder();
        scoreBuilder();
        leaderboardBuilder();
        gameCourse();
        goBackToMenuButtonBuilder(parentPanel);
        pauseButtonBuilder();
        this.add(pauseButton);
        backGroundSound.playLoop();
        leaderboard=new Leaderboard();
    }

    private void imageSoundBuilder(){
        this.background = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/backgroundGif.gif"))).getImage();
        this.heartFull=new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/minecraftFullHeart.png"))).getImage();
        this.heartEmpty=new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/minecraftEmptyHeart.png"))).getImage();
        this.gameOver=new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/gameOver.png"))).getImage();
        this.gameOverSound=new SoundPlayer("/Sounds/gameOver.wav");
        this.pauseButtonImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/pause button.png")));
        this.goBackImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("/Images/goBack.png")));
        this.backGroundSound = new SoundPlayer("/Sounds/starsWarsTheme.wav");
        this.bossTheme = new SoundPlayer("/Sounds/Star Wars- The Imperial March (Darth Vader's Theme).wav");
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

    private void leaderboardBuilder(){
        Leaderboard leaderboard = new Leaderboard(); // GameScreen
        leaderboard.loadFromFile();
        leaderboard.addScore(playerName, this.score);
    }

    private void goBackToMenuButtonBuilder(StartScreen parentPanel){
        this.goBackImage.setImage(this.goBackImage.getImage().getScaledInstance(300, 150, Image.SCALE_SMOOTH));
        this.goBackToMenu = new JButton(goBackImage);
        this.goBackToMenu.setBounds(this.getWidth()/2 - 140, this.getHeight() / 2 + 200 , 255, 100);
        this.goBackToMenu.setContentAreaFilled(false);
        this.goBackToMenu.setBorder(null);
        this.goBackToMenu.addActionListener((e) -> goBackToMenuAction());
    }

    private void pauseButtonBuilder(){
        this.pauseButtonImage.setImage(this.pauseButtonImage.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        this.pauseButton = new JButton(pauseButtonImage);
        this.pauseButton.setBounds(this.getWidth()-80, 20 , 36, 36);
        this.pauseButton.setContentAreaFilled(true);
        this.pauseButton.setBorder(null);
        this.pauseButton.addActionListener((e) -> pauseButtonAction());
    }



    //buttons actions
    private void pauseButtonAction(){
        this.isPaused = !isPaused;
        if (!isPaused) {
            requestFocusInWindow();
            if (directionThread == null || !directionThread.isAlive()) {
                allDirections();
            }
            if (scoreThread == null || !scoreThread.isAlive()) {
                infiniteScoreAdd();
            }
            if (actionThread == null || !actionThread.isAlive()) {
                action();
            }
        }
    }

    private void goBackToMenuAction(){
        frame.setContentPane(menu);
        menu.returnToPreviousPanel(); // מכין מחדש את הכפתורים והמצב
        backGroundSound.stop();
        bossTheme.stop();
        frame.revalidate();
        frame.repaint();
        menu.requestFocusInWindow();
    }



    //paintComponent
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        if (!this.isGameOver) {
            if (isPaused) {
                pausedPaintComponent(g);
                return;
            }
            else {
                this.remove(goBackToMenu);
            }
            gameRunningPaintComponent(g);
        }
        else {
            gameOverPaintComponent(g);
        }
        this.repaint();
    }

    private void pausedPaintComponent(Graphics g){
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setFont(Content.customFont.deriveFont(32f));
        g.setColor(Color.WHITE);
        String pausedText = "PAUSED";
        int textWidth = g.getFontMetrics().stringWidth(pausedText);
        g.drawString(pausedText, getWidth() / 2 - textWidth / 2, getHeight() / 2);
        this.add(goBackToMenu);
    }

    private void gameRunningPaintComponent(Graphics g){
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
        }
        for (EnemySpaceShip enemySpaceShip : enemySpaceShips) {
            enemySpaceShip.paint(g);
        }
        scoreLabel.setText("Score: " +score);
        for (Boost boost:boosts){
            boost.draw(g);
        }
        for (int i = 1; i < player.getBoostsThatAreOn().length; i++) {
            if (player.getBoostsThatAreOn()[i]){
                g.drawImage(new Boost(0,0,i+1,player).getImage(),20+(i-1)*40,100,30,30,null);
            }
        }
    }

    private void gameOverPaintComponent(Graphics g){
        g.drawImage(gameOver,this.getWidth()/2-250,this.getHeight()/2-350,500,500,this);
        g.setFont(Content.customFont.deriveFont(32f));
        g.setColor(Color.WHITE);
        String scoreText = "Your Score: " + score;
        int textWidth = g.getFontMetrics().stringWidth(scoreText);
        g.drawString(scoreText, this.getWidth() / 2 - textWidth / 2, this.getHeight() / 2 + 100);
        scoreLabel.setVisible(false);
        this.add(goBackToMenu);
        this.remove(pauseButton);
        backGroundSound.stop();

    }



    //keys
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

    private synchronized void allDirections(){
        directionThread = new  Thread(()->{
            while (!isGameOver){
                if(!isPaused){
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
                }
                try {
                    Thread.sleep(13);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });directionThread.start();
    }



    //updates
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
        if ((now - lastMeteorSpawnTime >= METEOR_SPAWN_DELAY)&&!bossActive) {
            createNewMeteor();
            lastMeteorSpawnTime = now;
        }
    }

    private void updateEnemySpaceShips(){
        int getToY=70;
        if (bossActive){
            getToY=140;
        }
        ArrayList<EnemySpaceShip> toRemove = new ArrayList<>();
        for (EnemySpaceShip enemySpaceShip : enemySpaceShips) {
            if (enemySpaceShip.getY() > getHeight()) {
                toRemove.add(enemySpaceShip);
            } else {
                if(enemySpaceShip.getY()<=getToY){
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

    private void updateExplosions(){
        explosions.removeIf(Explosion::isFinished);
        explosions.forEach(Explosion::update);

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



    //collisions
    private List<Mob> checkPlayerCollision(List<Mob> mobs){
        ArrayList<Mob> mobsToRemove = new ArrayList<>();
        if(player.isShieldOn()||player.isBoostShieldOn()) return mobsToRemove;
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
            if (mobRectangle.intersects(playerRectangle)) {
                player.setShieldOn(true);
                player.setHp(player.getHp() - 1);
                explosions.add(new Explosion(mob.getX(), mob.getY()));
                mobsToRemove.add(mob);
                if (player.getHp() <= 0) {
                    this.isGameOver = true;
                    gameOverSound.playSound();
                    if (!isSaved) {
                        this.leaderboard.addScore(this.playerName, this.score);
                        isSaved = true;
                    }
                }
                new Thread(() -> {
                    try {
                        if (player.isShieldOn()) {
                            Thread.sleep(3000);
                            player.setShieldOn(false);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
                break;
            }

        }
        return mobsToRemove;
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
                    mob.mobHit(player.getBulletDamage());
                    if(mob.getLife() <= 0){
                        score += (pointsPerHit*player.getXP());
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



    //game course
    private void infiniteScoreAdd(){
        scoreThread=new Thread(()->{
            while (!isGameOver) {
                if(!isPaused){
                    score++;
                }
                try {
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });scoreThread.start();
    }

    private synchronized void action() {
        final int[] enemyRespawn = {2000};
        actionThread = new Thread(() -> {
            while (!isGameOver) {
                if(!isPaused){
                    player.updateBullets();
                    updateExplosions();
                    updateMeteors();
                    updateEnemySpaceShips();
                    updateBoosts();
                    updateLevel();
                    this.meteors.removeAll(checkBulletsCollision(new ArrayList<Mob>(meteors),100,6));
                    this.meteors.removeAll(checkPlayerCollision(new ArrayList<Mob>(meteors)));
                    this.enemySpaceShips.removeAll(checkBulletsCollision(new ArrayList<Mob>(enemySpaceShips),200,2));
                    this.enemySpaceShips.removeAll(checkPlayerCollision(new ArrayList<Mob>(enemySpaceShips)));
                    checkPlayerBoostCollision();
                    for (EnemySpaceShip enemySpaceShip:enemySpaceShips){
                        ArrayList <EnemyBullets> newBullets = enemySpaceShip.getEnemyBullets();
                        newBullets.removeAll(checkPlayerCollision(new ArrayList<Mob>(newBullets)));
                        enemySpaceShip.setEnemyBullets(newBullets);
                    }
                    if (score > enemyRespawn[0]&&!bossActive) {
                        int num = random.nextInt(getWidth());
                        for (int i = 0; i < difficultyLevel; i++) {
                            enemySpaceShips.add(new EnemySpaceShip(num,-15));
                            int num2 = random.nextInt(getWidth());
                            while (Math.abs(num-num2)<player.getWidth()+15){
                                num2 = random.nextInt(getWidth());
                            }
                            num = num2;
                        }
                        enemyRespawn[0] +=(2000*difficultyLevel/2);
                    }
                    repaint();
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });actionThread.start();
    }

    private void gameCourse(){
        allDirections();
        infiniteScoreAdd();
        action();
        bossActivation();
    }

    private void bossActivation() {
        final int[] bossRespawn = {80000,new Boss(difficultyLevel,1, getWidth(), getHeight()).getLife()-50};
        new Thread(() -> {
            while (!isGameOver) {
                while (score > bossRespawn[0]) {
                    bossRespawn[0] += 80000;
                    if (bosses.isEmpty()) {
                        backGroundSound.pause();
                        bossTheme.playLoop();
                        bosses.add(new Boss(difficultyLevel,1, getWidth(), getHeight()));
                        bossActive=true;
                        meteors.clear();
                        enemySpaceShips.clear();
                        bossRespawn[1]=bosses.getFirst().getLife()-50;
                        break;
                    }
                }
                if (!bosses.isEmpty()) {
                    Boss boss = bosses.getFirst();
                    boss.moveSideways(player);
                    boss.updateBullets();
                    bosses.removeAll(checkBulletsCollision(new ArrayList<>(bosses), 1000, 2));
                    boss.getBullets().removeAll(checkPlayerCollision(new ArrayList<>(boss.getBullets())));
                    if(boss.getLife()<bossRespawn[1]){
                        bossRespawn[1]-=100;
                        int num = random.nextInt(getWidth());
                        for (int i = 0; i < difficultyLevel/2+1; i++) {
                            enemySpaceShips.add(new EnemySpaceShip(num,-15));
                            int num2 = random.nextInt(getWidth());
                            while (Math.abs(num-num2)<player.getWidth()+15){
                                num2 = random.nextInt(getWidth());
                            }
                            num = num2;
                        }
                    }
                    if (boss.getLife() <= 0) {
                        int bossX = boss.getX();
                        int bossY = boss.getY();
                        int minX = bossX - boss.getWidth() / 2;
                        int maxX = bossX + boss.getWidth();
                        boosts.add(new Boost(random.nextInt(minX, maxX), bossY, 1, player));
                        boosts.add(new Boost(random.nextInt(minX, maxX), bossY, 2, player));
                        bosses.clear();
                        bossTheme.stop();
                        backGroundSound.resume();
                        bossActive=false;
                    }
                }
                try {
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void updateLevel() {
        if (difficultyLevel <= 5 && score>5000) {
            difficultyLevel = score / 5000;
        }
    }
}
