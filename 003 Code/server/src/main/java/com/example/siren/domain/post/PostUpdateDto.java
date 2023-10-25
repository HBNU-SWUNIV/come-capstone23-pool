package com.example.siren.domain.post;

import lombok.Data;

@Data
public class PostUpdateDto {
    private Long id;
 /*   private String name;
    private String content;*/

    private String people;
    private String info;

    private Long memberId;

    public PostUpdateDto() {
    }
/*
    public PostUpdateDto(String name, String content,int people) {
        this.name = name;
        this.content = content;
        this.people = people;
    }*/

    public PostUpdateDto(Long id, Long memberId) {
        this.id = id;
        this.memberId = memberId;
    }

 /*   public PostUpdateDto(Long id, int people) {
        this.id = id;
        this.people = people;
    }*/

    public PostUpdateDto(Long id, String info) {
        this.id = id;
        this.info = info;
    }
}
