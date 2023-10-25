package com.example.siren.domain.chat;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "chat_messages")
public class ChatMessage {
    public enum MessageType{
        ENTER,TALK
    }

    private MessageType type;
    @Id
    private String roomId;
    private String sender;
    private String message;

    public ChatMessage() {
    }

    public ChatMessage(String roomId, String sender, String message) {
        this.roomId = roomId;
        this.sender = sender;
        this.message = message;
    }
}
