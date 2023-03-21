package com.example.siren.web.chat;

import com.example.siren.domain.chat.ChatRoom;
import com.example.siren.domain.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public Map chatRoom(@RequestBody ChatDto chatDto){//채팅방 생성
        //log.info("name={}",name);
       // JSONObject jsonObject = new JSONObject(text);
        //String name = jsonObject.getString("name");
        log.info("chatDto.getIds = {},chatDto.getRoomName={}",chatDto.getIds(),chatDto.getRoomName());
        return chatService.createRoom(chatDto.getRoomName(),chatDto.getIds());
    }
    @GetMapping
    public List<ChatRoom> findAllRoom(){
        return chatService.findAllRoom();
    }
 /*   @PostMapping("/check")
    public void check(@RequestBody List<Long> id){
        chatService.check(id);
    }*/

}
