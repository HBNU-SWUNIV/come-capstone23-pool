package com.example.siren.domain.chat;

import com.example.siren.domain.MnN.MemberAndChat;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
@Entity
@Data
@Table(name = "CHAT")
@Slf4j
public class ChatRoom {

    @Id
    @Column(name = "id")
    private String roomId;
    private String name;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "chatRoom")//fetch타입을 Lazy로 했을때 오류남 성능 생각하면 lazy 써야함
    private Set<MemberAndChat> enrollments = new HashSet<>();

    @Transient
    private static Set<WebSocketSession> sessions = new HashSet<>();

    public ChatRoom() {
    }

    @Builder
    public ChatRoom(String roomId,String name){
        this.roomId = roomId;
        this.name = name;
    }

    public void handlerActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService){
        if(chatMessage.getType().equals(ChatMessage.MessageType.ENTER)){
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender()+"님이 입장했습니다.");
        }
        log.info("세션 : {}",sessions);
        log.info("message는 {}",chatMessage.getMessage());
        sendMessage(chatMessage,chatService);

    }

    private <T>void sendMessage(T message, ChatService chatService) {
        log.info("세션 : {}",sessions);
        log.info("sendmessage는 {}",message);
        sessions.parallelStream()
                .forEach(session -> chatService.sendMessage(session,message));
    }
}
