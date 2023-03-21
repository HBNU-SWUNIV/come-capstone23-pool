package com.example.siren.domain.post;

import lombok.Data;

@Data
public class PostUpdateDto {
    private String name;
    private String content;

    public PostUpdateDto() {
    }

    public PostUpdateDto(String name, String content) {
        this.name = name;
        this.content = content;
    }
}
