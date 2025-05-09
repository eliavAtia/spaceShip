package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;


public class Meteor extends Mob{
    private BufferedImage image;
    private int rotationAngle = 0;


    public Meteor (int x, int y, int size) {
        setX(x);
        setY(y);
        setHeight(size);
        setWidth(size);
        setLife(3);
        try {
            this.image = ImageIO.read(Objects.requireNonNull(getClass().getResource("/images/img.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void paint(Graphics g) {
        if(isShouldDrawMobImage())
        if (image != null) {
            g.drawImage(image, getX() - getWidth()/2, getY() - getHeight()/2, getWidth(), getHeight(), null);
        } else {
            g.fillOval(getX() - getWidth()/2, getY() - getHeight()/2, getWidth(), getHeight());
        }
    }

    public void moveDown() {
        setY(getY()+1);
    }

    public void rotate(){
        if (this.rotationAngle+2 >= 360) {
            this.rotationAngle = 0;
        }
        else {
            this.rotationAngle+=2;
        }
    }

    public int getRotationAngle() {
        return this.rotationAngle;
    }

}

