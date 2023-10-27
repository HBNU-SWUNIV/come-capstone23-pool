package com.example.capston_pj.models.post;

public class PostReviewDTO {

    private Long id;
    private float review;
    private Long memberId;
    private String result;

    public PostReviewDTO(String result) {
        this.result = result;
    }

    public PostReviewDTO(Long id, float review, Long memberId) {
        this.id = id;
        this.review = review;
        this.memberId = memberId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getReview() {
        return review;
    }

    public void setReview(float review) {
        this.review = review;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
