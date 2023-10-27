package com.example.capston_pj.models;

public class FindLoginId {

    private String loginId;
   private String name;
   private String gender;

   private String result;

   private Long memberId;
   private String password;
    public FindLoginId(String result) {
        this.result = result;
    }
    public FindLoginId( Long memberId,String name,String password) {
        this.name = name;
        this.memberId = memberId;
        this.password = password;
    }

    public FindLoginId(String name, String gender) {
        this.name = name;
        this.gender = gender;
    }

    public FindLoginId(String loginId,String name, String gender) {
        this.name = name;
        this.gender = gender;
        this.loginId = loginId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
