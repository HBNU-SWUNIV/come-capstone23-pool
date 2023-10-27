package com.example.capston_pj.models.chattingRoom;

public class Rooms {
    private String roomId;
    private String roomName;

    private String names;
    private String ids;

    public Rooms(String roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
    }

    public Rooms(String roomId, String roomName, String names, String ids) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.names = names;
        this.ids = ids;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
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
}
