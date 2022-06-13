/*
Helper class for representing a single score

Author - Isaac Wu
*/
import java.io.Serializable;

public class Score implements Comparable, Serializable {
    public String name;
    public int score;

    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public int compareTo(Object other) {
        if(other instanceof Score) {
            Score o = (Score) other;
            return this.score - o.score;
        }
        return 0;
    }

    public boolean equals(Object other) {
        if(other instanceof Score) {
            Score o = (Score) other;
            return (this.name.equals(o.name) && this.score == o.score);
        }
        return false;
    }

    public String toString() {
        return name + "- " + score + " digs";
    }
}