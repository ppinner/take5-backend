package com.example.take5.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "users")
public class User {
    @Id
    private String id;

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    private Date dob;
    private String name;
    private Personality personality;
    private Category focus;
    private List<ActivityLogEntry> activityLog;
    private Score scores;

    public User() {
        super();
    }

    public User(String id, Date dob, String name, Personality personality, Category focus, List<ActivityLogEntry> activityLog, Score scores) {
        this.id = id;
        this.dob = dob;
        this.name = name;
        this.personality = personality;
        this.focus = focus;
        this.activityLog = activityLog;
        this.scores = scores;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Personality getPersonality() {
        return personality;
    }

    public void setPersonality(Personality personality) {
        this.personality = personality;
    }

    public Category getFocus() {
        return focus;
    }

    public void setFocus(Category focus) {
        this.focus = focus;
    }

    public List<ActivityLogEntry> getActivityLog() {
        return activityLog;
    }

    public void setActivityLog(List<ActivityLogEntry> activityLog) {
        this.activityLog = activityLog;
    }

    public Score getScores() {
        return scores;
    }

    public void setScores(Score scores) {
        this.scores = scores;
    }
}