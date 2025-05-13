package org.example;
import java.io.Serializable;

public class PlayerScore implements Comparable<PlayerScore>, Serializable {
    private static final long serialVersionUID = 1L;

    private String playerName;
    private int score;

    public PlayerScore(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(PlayerScore other) {
        return Integer.compare(other.score, this.score); // מהגבוה לנמוך
    }

    @Override
    public String toString() {
        return playerName + ":" + score;
    }
}


