package com.example.capston_pj.models;

import com.google.gson.annotations.SerializedName;

public class GetContent {
    @SerializedName("content")
    private String content;
    private String price;
    private String time;
    private String people;
    private String dow;

    private String gender;
    private String smoke;
    private String baggage;
    private String child;
    private String pet;
    private String weight;

    public GetContent(String content, String price, String time, String people, String dow, String gender, String smoke, String baggage, String child, String pet,String weight) {
        this.content = content;
        this.price = price;
        this.time = time;
        this.people = people;
        this.dow = dow;
        this.gender = gender;
        this.smoke = smoke;
        this.baggage = baggage;
        this.child = child;
        this.pet = pet;
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSmoke() {
        return smoke;
    }

    public void setSmoke(String smoke) {
        this.smoke = smoke;
    }

    public String getBaggage() {
        return baggage;
    }

    public void setBaggage(String baggage) {
        this.baggage = baggage;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getDow() {
        return dow;
    }

    public void setDow(String dow) {
        this.dow = dow;
    }
}
