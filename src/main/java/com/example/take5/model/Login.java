package com.example.take5.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "logins")
public class Login {
    @Id
    private String id;
    private String password;
    private String userId;

    public Login(String id, String password, String userId) {
        this.id = id;
        this.password = password;
        this.userId = userId;
    }

    public Login() {
        super();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}