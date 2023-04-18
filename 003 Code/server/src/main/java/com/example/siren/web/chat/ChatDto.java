package com.example.siren.web.chat;

import lombok.Data;

import java.util.List;
@Data
public class ChatDto {

    private String roomName;
    private List<Long>ids;

    public ChatDto() {
    }

    public ChatDto(String roomName, List<Long> ids) {
        this.roomName = roomName;
        this.ids = ids;
    }
}
