package com.example.take5.model;

public class Personality {
    private Integer openness;
    private Integer conscientiousness;
    private Integer extroversion;
    private Integer agreeableness;
    private Integer neuroticism;

    public Personality(Integer openness, Integer conscientiousness, Integer extroversion, Integer agreeableness, Integer neuroticism) {
        this.openness = openness;
        this.conscientiousness = conscientiousness;
        this.extroversion = extroversion;
        this.agreeableness = agreeableness;
        this.neuroticism = neuroticism;
    }

    public Personality() {
        super();
    }

    public Integer getOpenness() {
        return openness;
    }

    public void setOpenness(Integer openness) {
        this.openness = openness;
    }

    public Integer getConscientiousness() {
        return conscientiousness;
    }

    public void setConscientiousness(Integer conscientiousness) {
        this.conscientiousness = conscientiousness;
    }

    public Integer getExtroversion() {
        return extroversion;
    }

    public void setExtroversion(Integer extroversion) {
        this.extroversion = extroversion;
    }

    public Integer getAgreeableness() {
        return agreeableness;
    }

    public void setAgreeableness(Integer agreeableness) {
        this.agreeableness = agreeableness;
    }

    public Integer getNeuroticism() {
        return neuroticism;
    }

    public void setNeuroticism(Integer neuroticism) {
        this.neuroticism = neuroticism;
    }
}
