package com.example.siren.web.chat;

import com.example.siren.domain.chat.ChatMessage;
import com.example.siren.domain.chat.ChatRoom;
import com.example.siren.domain.chat.ChatService;
import com.example.siren.domain.member.Member;
import com.example.siren.web.MnN.IdDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public Map chatRoom(@RequestBody ChatDto chatDto){//채팅방 생성

        log.info("chatDto.getIds = {},chatDto.getRoomName={}",chatDto.getIds(),chatDto.getRoomName());
        return chatService.createRoom(chatDto.getRoomName(),chatDto.getIds());
    }
    @GetMapping
    public List<ChatRoom> findAllRoom(){
        return chatService.findAllRoom();
    }


    @ResponseBody
    @PostMapping("/record")
    public String record(@RequestBody IdDTO param){//채팅방 생성
        List<ChatMessage> record = chatService.getRecord(param.getRoomId());
        JSONObject obj = new JSONObject();
        try {
            JSONArray jArray = new JSONArray();//배열이 필요할때
            for (int i = 0; i < record.size(); i++)//배열
            {
                JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                sObject.put("sender",record.get(i).getSender());
                sObject.put("message", record.get(i).getMessage());
                jArray.put(sObject);
            }
            obj.put("item", jArray);//배열을 넣음
            return obj.toString();

        } catch (JSONException e) {
            return "error";
        }

    }

}
