package com.example.take5.model;

import java.util.Date;

public class ActivityLogEntry {
    Activity activity;
    String id;
    Date date;

    public ActivityLogEntry() {
        super();
    }

    public ActivityLogEntry(Activity activity, String id, Date date) {
        this.activity = activity;
        this.id = id;
        this.date = date;
    }

    public Activity getActivity() {
        return activity;
    }

    public String getId() {
        return id;
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
}
