package com.example.capston_pj.models;

public class MemberId {
    private Long memberId;
    private String roomId;

    public MemberId(String roomId) {
        this.roomId = roomId;
    }

    public MemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
