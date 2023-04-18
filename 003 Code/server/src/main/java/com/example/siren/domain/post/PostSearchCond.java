package com.example.siren.domain.post;

import lombok.Data;

@Data
public class PostSearchCond {
    private String start;
    private String end;
    private String times;

    public PostSearchCond() {
    }

    public PostSearchCond(String start, String end, String times) {
        this.start = start;
        this.end = end;
        this.times = times;
    }
}
