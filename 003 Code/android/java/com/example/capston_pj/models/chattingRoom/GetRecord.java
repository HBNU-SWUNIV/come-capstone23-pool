package com.example.capston_pj.models.chattingRoom;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetRecord {
    @SerializedName("item")
    public List<Record> item;

    public String toString() {
        return "GetRooms{" +
                "item=" + item +
                '}';
    }
}
