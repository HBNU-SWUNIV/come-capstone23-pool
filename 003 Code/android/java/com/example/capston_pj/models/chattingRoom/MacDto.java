package com.example.capston_pj.models.chattingRoom;

public class MacDto {
    private Long memberId;
    private String roomId;

    public MacDto(Long memberId, String roomId) {
        this.memberId = memberId;
        this.roomId = roomId;
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
