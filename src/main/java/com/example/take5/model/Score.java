package com.example.take5.model;

public class Score {
    private Double mindfulness;
    private Double connection;
    private Double physicalActivity;
    private Double giving;
    private Double learning;

    public Score(Double mindfulness, Double connection, Double physicalActivity, Double giving, Double learning) {
        this.mindfulness = mindfulness;
        this.connection = connection;
        this.physicalActivity = physicalActivity;
        this.giving = giving;
        this.learning = learning;
    }

    public Score() {
        super();
    }

    public Double getConnection() {
        return connection;
    }

    public void setConnection(Double connection) {
        this.connection = connection;
    }

    public Double getPhysicalActivity() {
        return physicalActivity;
    }

    public void setPhysicalActivity(Double physicalActivity) {
        this.physicalActivity = physicalActivity;
    }

    public Double getGiving() {
        return giving;
    }

    public void setGiving(Double giving) {
        this.giving = giving;
    }

    public Double getLearning() {
        return learning;
    }

    public void setLearning(Double learning) {
        this.learning = learning;
    }

    public Double getMindfulness() {
        return mindfulness;
    }

    public void setMindfulness(Double mindfulness) {
        this.mindfulness = mindfulness;
    }
}
