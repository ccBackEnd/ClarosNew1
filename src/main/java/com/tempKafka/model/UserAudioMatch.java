package com.tempKafka.model;

import org.springframework.data.elasticsearch.annotations.Field;

public class UserAudioMatch {
    @Field(name="user_id")
    private String userId;
    private String name;
    private double match_score;

    public UserAudioMatch() {
        super();
    }

    public double getMatch_score() {
        return match_score;
    }

    public void setMatch_score(double match_score) {
        this.match_score = match_score;
    }

    // Constructor
    public UserAudioMatch(String userId, String name, double matchScore) {
        this.userId = userId;
        this.name = name;
        this.match_score = matchScore;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}



