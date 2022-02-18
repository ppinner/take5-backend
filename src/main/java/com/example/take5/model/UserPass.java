package com.example.take5.model;

import org.springframework.data.annotation.Id;

public class UserPass {
    @Id
    private String username;
    private String password;

    public UserPass(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserPass() {
        super();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
