package com.example.take5.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "activityLogs")
public class ActivityLogEntry {
    Activity activity;
    @Id
    String id;
    String reflection;
    Integer rating;   //0-5 based on emotion OR -2 to 2?
    Date date;
    String userId;

    public ActivityLogEntry() {
        super();
    }

    public ActivityLogEntry(Activity activity, String id, Date date, String reflection, Integer rating, String userId) {
        this.activity = activity;
        this.id = id;
        this.reflection = reflection;
        this.rating = rating;
        this.date = date;
        this.userId = userId;
    }

    public Activity getActivity() {
        return activity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getReflection() {
        return reflection;
    }

    public void setReflection(String reflection) {
        this.reflection = reflection;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
