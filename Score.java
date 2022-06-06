/*
Helper class for representing a single score
*/
public class Score {
    public String name;
    public int score;

    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String toString() {
        return name + "- " + score + " digs";
    }
}