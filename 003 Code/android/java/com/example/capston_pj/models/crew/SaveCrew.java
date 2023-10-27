package com.example.capston_pj.models.crew;

public class SaveCrew {
    private String loginId;
    private Long postId;
    private String dow;
    private String times;


    public SaveCrew(String loginId, Long postId, String dow, String times) {
        this.loginId = loginId;
        this.postId = postId;
        this.dow = dow;
        this.times = times;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        loginId = loginId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getDow() {
        return dow;
    }

    public void setDow(String dow) {
        this.dow = dow;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }
}
