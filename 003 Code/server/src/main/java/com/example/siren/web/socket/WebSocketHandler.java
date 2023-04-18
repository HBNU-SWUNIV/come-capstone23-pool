package com.example.siren.web.socket;

import com.example.siren.domain.chat.ChatMessage;
import com.example.siren.domain.chat.ChatRoom;
import com.example.siren.domain.chat.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
@Transactional
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("입력{}",payload);
        ChatMessage chatMessage = objectMapper.readValue(payload,ChatMessage.class);
        log.info("message={}",chatMessage.getMessage());
        //String a = String.valueOf(chatService.findRoomById(chatMessage.getRoomId()).get());
        log.info("roomId{}");
        Optional<ChatRoom> chatRoom = chatService.findRoomById(chatMessage.getRoomId());
        log.info("chatRoom = {}",chatRoom.get());
        chatRoom.get().handlerActions(session,chatMessage,chatService);


    }
}
