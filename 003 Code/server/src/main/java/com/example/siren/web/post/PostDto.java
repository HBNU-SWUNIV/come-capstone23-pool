package com.example.siren.web.post;

import lombok.Data;

@Data
public class PostDto {
    private Long writerId;
    private String info;

    public PostDto() {
    }

    public PostDto(Long writerId, String info) {//이경우는 postId로 쓰임
        this.writerId = writerId;
        this.info = info;
    }

    public PostDto(Long writerId) {
        this.writerId = writerId;
    }
}
