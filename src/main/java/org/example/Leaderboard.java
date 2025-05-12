package org.example;
import java.io.*;
import java.util.*;

public class Leaderboard {
    private List<PlayerScore> scores = new ArrayList<>();
    private static final String FILE_NAME = "leaderboard.dat";

    public void addScore(String name, int score) {
        scores.add(new PlayerScore(name, score));
        saveToFile(); // שומר אחרי כל הוספה
    }

    public void printLeaderboard() {
        Collections.sort(scores);
        for (int i = 0; i < scores.size(); i++) {
            System.out.println((i + 1) + ". " + scores.get(i));
        }
    }

    public void loadFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            scores = (List<PlayerScore>) in.readObject();
        } catch (Exception e) {
            System.out.println("לא נטען לוח ניקוד - נתחיל חדש.");
        }
    }

    private void saveToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(scores);
        } catch (IOException e) {
            System.out.println("שגיאה בשמירה: " + e.getMessage());
        }
    }
    public List<PlayerScore> getSortedScores() {
        Collections.sort(scores);
        return new ArrayList<>(scores); // מחזיר עותק ממויין
    }

}
