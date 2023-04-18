package com.example.siren.domain.post;

import lombok.Data;

@Data
public class PostUpdateDto {
    private Long id;
 /*   private String name;
    private String content;*/

    private int people;
    private String info;

    public PostUpdateDto() {
    }
/*
    public PostUpdateDto(String name, String content,int people) {
        this.name = name;
        this.content = content;
        this.people = people;
    }*/

    public PostUpdateDto(Long id, int people) {
        this.id = id;
        this.people = people;
    }

    public PostUpdateDto(Long id, String info) {
        this.id = id;
        this.info = info;
    }
}