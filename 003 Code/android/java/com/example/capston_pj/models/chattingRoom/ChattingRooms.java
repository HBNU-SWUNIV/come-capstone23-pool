package com.example.capston_pj.models.chattingRoom;
//임시 DTO
public class ChattingRooms {
    private String sender;
    private String lastMessage;
    private String roomCode;

    private String time;

    public ChattingRooms() {
    }

    public ChattingRooms(String sender, String lastMessage, String roomCode,String time) {
        this.sender = sender;
        this.lastMessage = lastMessage;
        this.roomCode = roomCode;
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
