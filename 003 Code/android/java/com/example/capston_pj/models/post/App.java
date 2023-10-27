package com.example.capston_pj.models.post;

public class App {
    Long id;
    private String dow;
    Long memberId;
    private Long postId;

    public App(Long id, Long memberId) {
        this.id = id;
        this.memberId = memberId;
    }

    public App( String dow,Long memberId, Long postId) {
        this.dow = dow;
        this.memberId = memberId;
        this.postId = postId;
    }

    public String getDow() {
        return dow;
    }

    public void setDow(String dow) {
        this.dow = dow;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
