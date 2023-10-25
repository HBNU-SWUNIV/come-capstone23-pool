package com.example.siren.web.MnN;

import com.example.siren.domain.MnN.MemberAndChat;
import com.example.siren.domain.MnN.service.MacService;
import com.example.siren.domain.chat.ChatRoom;
import com.example.siren.domain.chat.ChatService;
import com.example.siren.domain.member.Member;
import com.example.siren.domain.member.repository.JpaMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/mac")
public class MacController {
    private final MacService macService;
    private final ChatService chatService;

    //private final JpaMemberRepository repository;

    @ResponseBody
    @PostMapping("/save")
    public MemberAndChat save(@RequestBody IdDTO idDTO){
        log.info("idDto.Member={},idDto.getRoom ={}",idDTO.getMemberId(),idDTO.getRoomId());
        return macService.save(idDTO.getMemberId(),idDTO.getRoomId());
    }

    @ResponseBody
    @PostMapping("/member>>chatRoom")
    public String chatRooms(@RequestBody IdDTO idDTO){
        List<ChatRoom> chatRooms = chatService.getRoomsByMemberId(idDTO.getMemberId());
        JSONObject obj = new JSONObject();
        try {
            JSONArray jArray = new JSONArray();//배열이 필요할때
            for (int i = 0; i < chatRooms.size(); i++)//배열
            {
                JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                sObject.put("roomId",chatRooms.get(i).getRoomId());
                sObject.put("roomName", chatRooms.get(i).getName());
                jArray.put(sObject);
            }
            obj.put("item", jArray);//배열을 넣음
            return obj.toString();

        } catch (JSONException e) {
            return "error";
        }
    }

}
