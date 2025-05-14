package org.example;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundPlayer {
    private Clip clip ;
    private int pausedFramePosition = 0;


    public SoundPlayer(String soundPath) {
        try {
            URL url = getClass().getResource(soundPath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playSound() {
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop();
            }
            pausedFramePosition = 0;
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void playLoop() {
        if (clip != null) {
            pausedFramePosition = 0;
            clip.setFramePosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        }

    }

    public boolean isRunning() {
        return clip != null && clip.isRunning();
    }

    public void pause() {
        if (clip != null && clip.isRunning()) {
            pausedFramePosition = clip.getFramePosition();
            clip.stop();
        }
    }

    public void resume() {
        if (clip != null && !clip.isRunning()) {
            clip.setFramePosition(pausedFramePosition);
            clip.start();
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

}
