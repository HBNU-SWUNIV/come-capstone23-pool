package com.example.capston_pj.models.post;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetApp {
    @SerializedName("item")
    public List<AppList> item;

    @Override
    public String toString() {
        return "GetApp{" +
                "item=" + item +
                '}';
    }
}
