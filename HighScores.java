/*
An ArrayList representation of the high score list
Yes, I know this is not a HashMap, but this works out better because then duplicate entries are allowed. Hope that's okay.
Saves to HighScores.ser file

Author - Isaac Wu
*/
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;

public class HighScores implements Serializable {
    private static final long serialVersionUID = 1234567890987654321L;
    private ArrayList<Score> scores = new ArrayList<>();

    public String toString() {
        if(scores.size() <= 0) {
            return "No scores have been recorded";
        }
        String out = " ".repeat(12)+"---HIGH SCORES---"+" ".repeat(12)+"\nPLAYER"+" ".repeat(31)+"DIGS\n";
        for(Score s : scores) {
            out += String.format("%-20s", s.name) + String.format("%21s", s.score) + "\n";
        }
        return out;
    }

    public void save(Score s) {
        scores.add(s);
        scores.sort(null);
        try {
            FileOutputStream fos = new FileOutputStream("HighScores.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
            fos.close();
        } catch(IOException e) {
            System.out.println(e);
        }
    }

    public static HighScores restore() {
        try {
            FileInputStream fis = new FileInputStream("HighScores.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            try {
                return (HighScores) ois.readObject();
            } catch(ClassCastException e) {}
            ois.close();
            fis.close();
        } catch(Exception e) {
            System.out.println(e);
        }
        return null;
    }
}