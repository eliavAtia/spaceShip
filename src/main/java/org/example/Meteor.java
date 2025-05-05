package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;


public class Meteor {
    private final int x;
    private int y;
    private final int width;
    private final int height;
    private BufferedImage image;
    private int rotationAngle = 0;
    private int life = 0;


    public Meteor (int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.width = size;
        this.height = size;
        this.life = 3;
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/img.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void paint(Graphics g) {
        if (image != null) {
            g.drawImage(image, this.x - this.width/2, this.y - this.height/2, this.width, this.height, null);
        } else {
            g.fillOval(this.x - this.width/2, this.y - this.height/2, this.width, this.height);
        }
    }

    public void moveDown() {
        this.y++;
    }

    public void rotate(){
        if (this.rotationAngle+2 >= 360) {
            this.rotationAngle = 0;
        }
        else {
            this.rotationAngle+=2;
        }
    }

    public void meteorHit(){
        this.life--;
    }

    public int getRotationAngle() {
        return this.rotationAngle;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getLife() {
        return life;
    }
}

