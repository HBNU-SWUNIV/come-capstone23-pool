package com.example.capston_pj.models.post;

public class Posting {

    private Long id;
    private Long writerId;
    private String loginId;
    private String name;
    private String start;
    private String end;
    private String content;
    private String people;
    private String times;
    private String dow;
    private int price;
    private String info;

    private String gender;
    private String smoke;
    private String pet;
    private String child;
    private String baggage;

    private float review;
    private int rCount;
    private String mode;
    private String driver;
    private String app;
    private String weight;
    public Posting() {
    }


    public Posting(Long writerId, String loginId, String name, String start, String end, String content, String people, String times, String dow,int price,String info,String gender,String smoke,String pet,String child, String baggage, float review, int rCount,String mode,String driver,String app,String weight) {
        this.writerId = writerId;
        this.loginId = loginId;
        this.name = name;
        this.start = start;
        this.end = end;
        this.content = content;
        this.people = people;
        this.times = times;
        this.dow = dow;
        this.price = price;
        this.info = info;
        this.gender = gender;
        this.smoke = smoke;
        this.pet = pet;
        this.child = child;
        this.baggage =baggage;
        this.review = review;
        this.rCount = rCount;
        this.mode = mode;
        this.driver = driver;
        this.app = app;
        this.weight = weight;
    }

    public Posting(Long id, String times, String dow,String start) {
        this.id = id;
        this.times = times;
        this.dow = dow;
        this.start = start;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public Long getWriterId() {
        return writerId;
    }

    public void setWriterId(Long writerId) {
        this.writerId = writerId;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getDow() {
        return dow;
    }

    public void setDow(String dow) {
        this.dow = dow;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getBaggage() {
        return baggage;
    }

    public void setBaggage(String baggage) {
        this.baggage = baggage;
    }

    public float getReview() {
        return review;
    }

    public void setReview(float review) {
        this.review = review;
    }

    public int getrCount() {
        return rCount;
    }

    public void setrCount(int rCount) {
        this.rCount = rCount;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
