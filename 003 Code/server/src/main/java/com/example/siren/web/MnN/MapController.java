package com.example.siren.web.MnN;

import com.example.siren.domain.MnN.Crew;
import com.example.siren.domain.MnN.MemberAndPost;
import com.example.siren.domain.MnN.repository.map.MapRepository;
import com.example.siren.domain.MnN.repository.map.MapRepositoryImpl;
import com.example.siren.domain.MnN.service.MapService;
import com.example.siren.domain.member.Member;
import com.example.siren.domain.post.Post;
import com.example.siren.web.FindByNickDTO;
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
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/map")
public class MapController {

    private final MapService mapService;
    private final MapRepositoryImpl mapRepository;

    @ResponseBody
    @PostMapping("/save")
    public MemberAndPost save(@RequestBody IdDTO2 idDTO2){
        log.info("loginId={},postID ={},dow={},times={}",idDTO2.getLoginId(),idDTO2.getPostId(),idDTO2.getDow(),idDTO2.getTimes());
        return mapService.save(idDTO2.getLoginId(),idDTO2.getPostId(),idDTO2.getDow(),idDTO2.getTimes());
    }

    @ResponseBody
    @PostMapping("/m_schedule")
    public String findByNickname(@RequestBody IdDTO idDTO){
        List<MemberAndPost> findMap = mapRepository.findByMemberId(idDTO.getMemberId());

        String [] check = {"x","x","x","x","x","x","x"};
        for(MemberAndPost i : findMap){
            String [] arr = i.getDow().split(",");
            String arr2 = i.getTimes();
            for(String j:arr){
                log.info("j={}",j);
                String input = "";
                input = input+arr2;
                check[Integer.parseInt(j)-1] = input;
            }
        }
        JSONObject obj = new JSONObject();
        obj.put("result",check);
        return obj.toString();
    }
/*    @ResponseBody
    @PostMapping("/saveCheck")
    public String saveCheck(@RequestBody IdDTO idDTO){
        List<MemberAndPost> findMap = mapRepository.findByMemberId(idDTO.getMemberId());

        String [] check = {"x","x","x","x","x","x","x"};
        for(MemberAndPost i : findMap){
            String [] arr = i.getDow().split(",");
            String [] arr2 = i.getTimes().split(",");
            for(String j:arr){
                log.info("j={}",j);
                String input = "";
                for(String k : arr2 ){
                    log.info("k={}",k);
                    input = input+String.valueOf(k.charAt(0));
                }
                check[Integer.parseInt(j)-1] = input;
            }
        }
        JSONObject obj = new JSONObject();
        obj.put("result",check);
        return obj.toString();
    }*/

    @ResponseBody
    @PostMapping("/member>>post")
    public String findByMemberId(@RequestBody IdDTO2 idDTO2){
        log.info("memberId={}",idDTO2.getMemberId());
        List<Post>posts = mapService.findByMemberId(idDTO2.getMemberId());
        JSONObject obj = new JSONObject();
        try {
            JSONArray jArray = new JSONArray();//배열이 필요할때
            for (int i = 0; i < posts.size(); i++)//배열
            {
                JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                sObject.put("loginId",posts.get(i).getLoginId());
                sObject.put("name", posts.get(i).getName());
                sObject.put("id",posts.get(i).getId());
                sObject.put("writerId",posts.get(i).getWriterId());
                sObject.put("start",posts.get(i).getStart());
                sObject.put("end",posts.get(i).getEnd());
                sObject.put("people",posts.get(i).getPeople());
                sObject.put("price",posts.get(i).getPrice());
                sObject.put("times",posts.get(i).getTimes());
                sObject.put("dow",posts.get(i).getDow());
                sObject.put("content",posts.get(i).getContent());
                sObject.put("info",posts.get(i).getInfo());
                jArray.put(sObject);
            }
            obj.put("item", jArray);//배열을 넣음
            return obj.toString();

        } catch (JSONException e) {
            return "error";
        }
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
