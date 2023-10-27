package com.example.capston_pj.models.crew;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCrew {
    @SerializedName("item")
    public List<Crew> item;

    public String toString() {
        return "GetRooms{" +
                "item=" + item +
                '}';
    }
}
