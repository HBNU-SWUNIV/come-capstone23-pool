package com.example.capston_pj.models;

public class GetSchedule {
    private Long memberId;
    private String[] result;

    public GetSchedule(Long memberId) {
        this.memberId = memberId;
    }

    public GetSchedule(String[] result) {
        this.result = result;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String[] getResult() {
        return result;
    }

    public void setResult(String[] result) {
        this.result = result;
    }
}
