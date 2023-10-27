package com.example.capston_pj.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Member implements Serializable {
    @SerializedName("id")
    private Long Id;
    @SerializedName("loginId")
    private String loginId;
    @SerializedName("password")
    private String password;
    @SerializedName("name")
    private String name;

    private Long profile;
    private String gender;
    private Long car;

    private float score;
    private int late;
    private String home;
    private String dow;

    private int money;

    public Member(String loginId, String password, String name, Long profile, String gender, Long car,int money,float score, int late,String home,String dow) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.profile = profile;
        this.gender = gender;
        this.car = car;
        this.money = money;
        this.score = score;
        this.late = late;
        this.home = home;
        this.dow = dow;
    }

    public Member(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    public Member(String name) {
        this.name = name;
    }

    public String getHome() {
        return home;
    }

    public String getDow() {
        return dow;
    }

    public void setDow(String dow) {
        this.dow = dow;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public long getId() {
        return Id;
    }

    public void setUserId(long userId) {
        this.Id = userId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProfile() {
        return profile;
    }

    public void setProfile(Long profile) {
        this.profile = profile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getCar() {
        return car;
    }

    public void setCar(Long car) {
        this.car = car;
    }
}
