package com.example.siren.web.post;

import lombok.Data;

@Data
public class PostReviewDto {
    private Long id;
    private float review;
    private Long memberId;

    private String result;


    public PostReviewDto() {
    }

    public PostReviewDto(String result) {
        this.result = result;
    }

    public PostReviewDto(Long id, float review, Long memberId) {
        this.id = id;
        this.review = review;
        this.memberId = memberId;
    }
}
