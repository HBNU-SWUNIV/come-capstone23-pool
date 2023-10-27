package com.example.capston_pj.models.chattingRoom;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetRoom {
    @SerializedName("item")
    public List<Rooms> item;

    public String toString() {
        return "GetRooms{" +
                "item=" + item +
                '}';
    }
}
