package com.example.siren.web.post;

import lombok.Data;

@Data
public class PostDto {
    private Long writerId;

    public PostDto() {
    }

    public PostDto(Long writerId) {
        this.writerId = writerId;
    }
}
