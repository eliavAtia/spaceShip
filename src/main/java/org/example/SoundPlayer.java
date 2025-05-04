package org.example;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundPlayer {
    private Clip clip;
    public SoundPlayer(String soundPath){
        try{
            URL url=getClass().getResource(soundPath);
            AudioInputStream audioInputStream= AudioSystem.getAudioInputStream(url);
            clip=AudioSystem.getClip();
            clip.open(audioInputStream);
        }catch (UnsupportedAudioFileException| IOException| LineUnavailableException e){
            e.printStackTrace();
        }
    }
    public void playSound(){
        if (clip!=null){
            if (clip.isRunning()){
                clip.stop();
            }
            clip.setFramePosition(0);
            clip.start();
        }
    }
}
