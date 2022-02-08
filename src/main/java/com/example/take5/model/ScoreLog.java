package com.example.take5.model;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class ScoreLog {
    @Id
    Date date;
    Score score;

    public ScoreLog(Score score, Date date) {
        this.score = score;
        this.date = date;
    }

    public ScoreLog() {super();}

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
