package com.example.take5.model;

public class RecommendationRating {
    private String userId;
    private String activityId;
    private Integer rating;

    public RecommendationRating(String userId, String activityId, Integer rating) {
        this.userId = userId;
        this.activityId = activityId;
        this.rating = rating;
    }
}


