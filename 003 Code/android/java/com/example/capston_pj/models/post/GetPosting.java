package com.example.capston_pj.models.post;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetPosting {
    @SerializedName("item")
    public List<Posting> item;

    @Override
    public String toString() {
        return "GetPosting{" +
                "item=" + item +
                '}';
    }
}
