package com.example.take5.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="activities")
public class Activity {
    @Id
    private String id;
    private String name;
    private String description;
    private Category[] category;

    public Activity(String id, String name, String description, Category[] categories) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = categories;
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

    public Category[] getCategory() {
        return category;
    }

    public void setCategory(Category[] category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format(
                "Activity[id='%s', name='%s', category='%s']",
                id, name, category.toString());
    }
}