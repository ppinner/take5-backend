package com.example.take5.model;

public class ActivityLogEntry {
    Activity activity;
    String id;

    public ActivityLogEntry() {
        super();
    }

    public ActivityLogEntry(Activity activity, String id) {
        this.activity = activity;
        this.id = id;
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
}
