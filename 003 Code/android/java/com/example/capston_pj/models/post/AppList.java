package com.example.capston_pj.models.post;

public class AppList {
    private Long id;
    private String name;
    private Long profile;
    private float score;

    private String dow;

    public AppList(Long id,String name, Long profile, float score,String dow) {
        this.id = id;
        this.name = name;
        this.profile = profile;
        this.score = score;
        this.dow = dow;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProfile() {
        return profile;
    }

    public void setProfile(Long profile) {
        this.profile = profile;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getDow() {
        return dow;
    }

    public void setDow(String dow) {
        this.dow = dow;
    }
}
