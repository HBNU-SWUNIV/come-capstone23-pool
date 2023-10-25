package com.example.siren.domain.chat;

import com.example.siren.domain.chat.repository.ChatMessageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final SpringDataJpaChatRepository repository;
    private final ObjectMapper objectMapper;
    private final ChatMessageRepository chatMessageRepository;

   // private Map<String, ChatRoom> chatRooms;

  /*  @PostConstruct
    private void init(){
        chatRooms = new LinkedHashMap<>();
        ChatRoom chatRoom = ChatRoom.builder()
                .roomId("test")
                .name("name")//채팅방 이름
                .build();
        chatRooms.put("test",chatRoom);
    }*/

    public List<ChatRoom> findAllRoom(){
        return repository.findAll();
    }

    public Optional <ChatRoom> findRoomById(String roomId){
        return repository.findById(roomId);
    }

    public List<ChatRoom> getRoomsByMemberId(Long memberId) {
        return repository.findByEnrollmentsMemberId(memberId);
    }

    public Map createRoom(String name, List<Long> memberIds){
        Long memberCount = Long.valueOf(memberIds.size());
        List<ChatRoom> chatRooms = repository.findChatRoomByMemberIds(memberIds, memberCount);
        if (chatRooms.isEmpty()) {
            String randomId = UUID.randomUUID().toString();
            ChatRoom chatRoom = ChatRoom.builder()
                    .roomId(randomId)
                    .name(name)//채팅방 이름
                    .build();
            // ChatRoom chatRoom = new ChatRoom(randomId,name);
            log.info("chatroom.roomId={},chatRoom.name={}",chatRoom.getRoomId(),chatRoom.getName());
            repository.save(chatRoom);
            Map<String,String>map = new HashMap<>();
            map.put("roomId",chatRoom.getRoomId());
            map.put("roomName",chatRoom.getName());
            map.put("state","new");

            return map;
        }
        else {
            ChatRoom getChatRoom = chatRooms.get(0);
            Map<String,String>map2 = new HashMap<>();
            map2.put("roomId",getChatRoom.getRoomId());
            map2.put("roomName",getChatRoom.getName());
            map2.put("state","old");

            return map2;
        }

    }
  /*  public String check(List<Long> memberIds){
        Long memberCount = Long.valueOf(memberIds.size());
        List<ChatRoom> chatRooms = repository.findChatRoomByMemberIds(memberIds, memberCount);
        if (chatRooms.isEmpty()) {
            return null;
        }
        else {
            for(ChatRoom i : chatRooms){
                return i.getRoomId().toString();
            }
        }
    }*/

    public <T> void sendMessage(WebSocketSession session,T message){
        try {
            log.info("서비스 메시지 = {}",message);
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        }catch (IOException e){
            log.error(e.getMessage(),e);
        }
    }
    public void saveChatMessage(ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessage> getRecord(String chatRoomId) {
        return chatMessageRepository.findByRoomId(chatRoomId);
    }
}
