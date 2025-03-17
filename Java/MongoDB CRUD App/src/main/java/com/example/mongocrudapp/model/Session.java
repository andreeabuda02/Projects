package com.example.mongocrudapp.model;

import org.bson.types.ObjectId;

public class Session {
    private ObjectId id;
    private String userId;
    private String jwt;

    public Session() {
    }

    public Session(ObjectId id, String userId, String jwt) {
        this.id = id;
        this.userId = userId;
        this.jwt = jwt;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

}