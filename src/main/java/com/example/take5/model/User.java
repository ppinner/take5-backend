package com.example.take5.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashMap;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private Date dob;
    private String name;
    private Personality personality;
    private Category focus;
    private Date focusStart;
    private HashMap<Date, Score> scores;

    public User() {
        super();
    }

    public User(String id, Date dob, String name, Personality personality, Category focus, HashMap<Date, Score> scores, Date focusStart) {
        this.id = id;
        this.dob = dob;
        this.name = name;
        this.personality = personality;
        this.focus = focus;
        this.focusStart = focusStart;
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

    public HashMap<Date, Score> getScores() {
        return scores;
    }

    public void setScores(HashMap<Date, Score> scores) {
        this.scores = scores;
    }

    public String getId() {
        return id;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getFocusStart() {
        return focusStart;
    }

    public void setFocusStart(Date focusStart) {
        this.focusStart = focusStart;
    }
}