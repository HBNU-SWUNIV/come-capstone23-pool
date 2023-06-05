package com.example.siren.domain.post;

import lombok.Data;

@Data
public class PostSearchCond {
    private String start;
    private String end;
    private String times;
    private String dow;
    private String gender;
    private String smoke;
    private String pet;
    private String child;
    private String baggage;

    public PostSearchCond() {
    }

    public PostSearchCond(String start, String end, String times, String dow, String gender, String smoke, String pet, String child, String baggage) {
        this.start = start;
        this.end = end;
        this.times = times;
        this.dow = dow;
        this.gender = gender;
        this.smoke = smoke;
        this.pet = pet;
        this.child = child;
        this.baggage = baggage;
    }

}
