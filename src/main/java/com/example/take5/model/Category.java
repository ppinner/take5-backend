package com.example.take5.model;

public enum Category {
    mindfulness("Mindfulness"),
    physicalActivity("Physical Activity"),
    learning("Learning"),
    connection("Connection"),
    giving("Giving");

    public final String label;

    Category(String label) {
        this.label = label;
    }

    public static Category valueOfLabel(String label) {
        for (Category e : values()) {
            if (e.label.equals(label)) {
                return e;
            }
        }
        return null;
    }
}
