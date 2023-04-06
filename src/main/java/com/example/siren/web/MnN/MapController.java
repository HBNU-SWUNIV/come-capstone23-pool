package com.example.siren.web.MnN;

import com.example.siren.domain.MnN.Crew;
import com.example.siren.domain.MnN.MemberAndPost;
import com.example.siren.domain.MnN.service.MapService;
import com.example.siren.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/map")
public class MapController {

    private final MapService mapService;

    @ResponseBody
    @PostMapping("/save")
    public MemberAndPost save(@RequestBody IdDTO2 idDTO2){
        log.info("loginId={},postID ={},dow={},times={}",idDTO2.getLoginId(),idDTO2.getPostId(),idDTO2.getDow(),idDTO2.getTimes());
        return mapService.save(idDTO2.getLoginId(),idDTO2.getPostId(),idDTO2.getDow(),idDTO2.getTimes());
    }
    @ResponseBody
    @PostMapping("/member>>post")
    public List<MemberAndPost> findByMemberId(@RequestBody IdDTO2 idDTO2){
        log.info("memberId={}",idDTO2.getMemberId());
        return mapService.findByMemberId(idDTO2.getMemberId());
    }
    @ResponseBody
    @PostMapping("/post>>member")
    public String findByPostId(@RequestBody IdDTO2 idDTO2){
        log.info("postId={}",idDTO2.getPostId());
        List<Crew> crews = mapService.findByPostId(idDTO2.getPostId());
        JSONObject obj = new JSONObject();
        try {
            JSONArray jArray = new JSONArray();//배열이 필요할때
            for (int i = 0; i < crews.size(); i++)//배열
            {
                JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                sObject.put("memberId",crews.get(i).getMemberId());
                sObject.put("memberName",crews.get(i).getMemberName());
                sObject.put("dow",crews.get(i).getDow());
                sObject.put("times",crews.get(i).getTimes());
                jArray.put(sObject);
            }
            obj.put("item", jArray);//배열을 넣음
            return obj.toString();

        } catch (JSONException e) {
            return "error";
        }
    }
}
