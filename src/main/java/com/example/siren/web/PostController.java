package com.example.siren.web;

import com.example.siren.domain.post.Post;
import com.example.siren.domain.post.service.PostService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @ResponseBody
    @PostMapping("/post-json")
    public Post requestBodyJson(@RequestBody Post post){
        log.info("name = {},loginId = {}, content ={}",post.getName(),post.getLoginId(),post.getContent());
        postService.save(post);
        return post;
    }

    @ResponseBody
    @GetMapping
    public String posts(){
        List<Post> postList= postService.findItems();
        JSONObject obj = new JSONObject();
        try {
            JSONArray jArray = new JSONArray();//배열이 필요할때
            for (int i = 0; i < postList.size(); i++)//배열
            {
                JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                sObject.put("loginId",postList.get(i).getLoginId());
                sObject.put("name", postList.get(i).getName());
                sObject.put("id",postList.get(i).getId());
                sObject.put("writerId",postList.get(i).getWriterId());
                sObject.put("start",postList.get(i).getStart());
                sObject.put("end",postList.get(i).getEnd());

                jArray.put(sObject);
            }
            obj.put("item", jArray);//배열을 넣음
            return obj.toString();

        } catch (JSONException e) {
          return "error";
        }
    }
    @ResponseBody
    @GetMapping("/content")
    public String findContent(@RequestParam Long id){
        Optional<Post> post = postService.findById(id);
        JSONObject obj = new JSONObject();
        obj.put("content", post.get().getContent());
        return obj.toString();
    }

}
