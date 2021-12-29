package com.example.take5.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="activities")
public class Activity {
    @Id
    private String id;

    private String name;
    private String description;
    private String[] category;

    public Activity(String id, String name, String description, String[] category) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public Activity(){
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getCategory() {
        return category;
    }

    public void setCategory(String[] category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return String.format(
                "Activity[id='%s', name='%s', category='%s']",
                id, name, category.toString());
    }
}