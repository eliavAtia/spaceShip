package org.example;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager extends JPanel {
    private static final String USER_FILE = "usernames.txt";
    private List<String> userName;
    private String currentName;

    public UserManager(int x,int y,int width,int high) {
        this.setBounds(x,y,width,high);
      userName =loadUsernames();
      currentName= askUserName();
      if ((currentName != null && !currentName.trim().isEmpty())){
          userName.add(currentName.trim());
          saveUsernames();
      }

    }

    private String askUserName() {
        return JOptionPane.showInputDialog(null, "Enter Username: ", JOptionPane.QUESTION_MESSAGE);// יוצר תיבה שבה מכניס את השם
    }

    private void saveUsernames() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE))) {  /*תכתוב לי בקובץ הזה*/
            for (String Username : userName) {
                writer.write(Username);
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    private List<String> loadUsernames(){
        List<String> loadedUsernames = new ArrayList<>();
        File file = new File( USER_FILE);
        if (!file.exists()) return loadedUsernames;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line= reader.readLine()) !=null){
                loadedUsernames.add(line.trim());
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return loadedUsernames;
    }

    public String getCurrentName(){
        return currentName;
    }
    public  List<String> getALLusername(){
        return userName;
    }

}