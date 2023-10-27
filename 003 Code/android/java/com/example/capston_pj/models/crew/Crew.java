package com.example.capston_pj.models.crew;

public class Crew {
    private Long memberId;
    private String memberName;

    private String dow;
    private String times;

    private String driver;

    public Crew(Long memberId, String memberName, String dow, String times,String driver) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.dow = dow;
        this.times = times;
        this.driver = driver;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getDow() {
        return dow;
    }

    public void setDow(String dow) {
        this.dow = dow;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }
}
