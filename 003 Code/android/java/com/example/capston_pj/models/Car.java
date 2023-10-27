package com.example.capston_pj.models;

public class Car {
    private Long id;
    private String num;
    private String maker;
    private String model;
    private String type;

    private Long image;

    public Car(Long id) {
        this.id = id;
    }

    public Car(Long id, String num, String maker, String model, String type,Long image) {
        this.id = id;
        this.num = num;
        this.maker = maker;
        this.model = model;
        this.type = type;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getImage() {
        return image;
    }

    public void setImage(Long image) {
        this.image = image;
    }
}
