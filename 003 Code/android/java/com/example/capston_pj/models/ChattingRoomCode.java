package com.example.capston_pj.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ChattingRoomCode implements Serializable {
    @SerializedName("roomId")
    private String roomId;
    @SerializedName("roomName")
    private String roomName;
    private List<Long> ids;
    private String state;


    public ChattingRoomCode(List<Long>ids,String roomName)  {
        this.roomName = roomName;
        this.ids = ids;
    }

    public ChattingRoomCode(String roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
