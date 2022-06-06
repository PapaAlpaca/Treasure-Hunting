import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.TreeMap;

public class HighScores implements Serializable {
    private static TreeMap<Integer, String> scores = new TreeMap<>();

    public String toString() {
        if(scores.size() <= 0) {
            return "No scores have been recorded";
        }
        int lastKey = 1;
        String out = "";
        while(scores.higherKey(lastKey) != null) {
            out += scores.get(scores.higherKey(lastKey))+" - "+scores.higherKey(lastKey)+" digs\n";
            lastKey = scores.higherKey(lastKey);
        }
        return out;
    }

    public static void save(Score s) {
        scores.put(s.score, s.name);
        try {
            FileOutputStream fos = new FileOutputStream("HighScores.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(scores);
            oos.close();
            fos.close();
        } catch(IOException e) {
            System.out.println(e);
        }
    }

    public static void restore() {
        try {
            FileInputStream fis = new FileInputStream("HighScores.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            scores = (TreeMap<Integer, String>) ois.readObject();
            ois.close();
            fis.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}