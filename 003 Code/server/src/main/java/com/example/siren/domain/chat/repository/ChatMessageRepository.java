package com.example.siren.domain.chat.repository;

import com.example.siren.domain.chat.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage,String> {
    List<ChatMessage> findByRoomId(String chatRoomId);
}
