package com.example.capston_pj.models.chattingRoom;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Record implements Serializable {
    @SerializedName("sender")
    private String sender;
    @SerializedName("message")
    private String message;

    private int viewType;

    public Record(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public Record(String sender, String message, int viewType) {
        this.sender = sender;
        this.message = message;
        this.viewType = viewType;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
