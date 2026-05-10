package edu.hitsz.dao;
import java.io.Serializable;

public class Record implements Serializable {
    private String name;
    private int score;
    private String time;

    public Record(String name, int score, String time) {
        this.name = name;
        this.score = score;
        this.time = time;
    }
    public String getName() { return name; }
    public int getScore() { return score; }
    public String getTime() { return time; }
}


