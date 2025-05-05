package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Explosion {
    private int x, y;
    private int frameIndex = 0;
    private static final int FRAME_DELAY = 5;
    private int frameCounter = 0;
    private static final ArrayList<BufferedImage> frames = new ArrayList<>();

    static {
        for (int i = 1; i <= 10; i++) {
            try {
                frames.add(ImageIO.read(Objects.requireNonNull(Explosion.class.getResource("/images/Explosion_" + i + ".png"))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Explosion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isFinished() {
        return frameIndex >= frames.size();
    }

    public void update() {
        frameCounter++;
        if (frameCounter >= FRAME_DELAY) {
            frameIndex++;
            frameCounter = 0;
        }
    }

    public void paint(Graphics g) {
        if (!isFinished() && frameIndex < frames.size()) {
            BufferedImage img = frames.get(frameIndex);
            int newWidth = img.getWidth() / 2;
            int newHeight = img.getHeight() / 2;
            g.drawImage(img, x - newWidth / 2, y - newHeight / 2, newWidth, newHeight, null);
        }
    }
}

