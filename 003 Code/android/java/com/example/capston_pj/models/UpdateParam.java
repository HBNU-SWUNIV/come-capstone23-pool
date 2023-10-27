package com.example.capston_pj.models;

public class UpdateParam {

    private Long id;
    private String password;
    private String name;

    private int money;

    private String sender;

    private String result;

    private String token;

    public UpdateParam(Long id, String token) {
        this.id = id;
        this.token = token;
    }

    public UpdateParam(String result) {
        this.result = result;
    }

    public UpdateParam(Long id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }

    public UpdateParam(Long id, int money) {
        this.id = id;
        this.money = money;
    }

    public UpdateParam(Long id, int money, String sender) {
        this.id = id;
        this.money = money;
        this.sender = sender;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public UpdateParam(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
