package com.example.siren.web.post;

import com.example.siren.domain.MnN.service.MapService;
import com.example.siren.domain.app.Application;
import com.example.siren.domain.app.repository.AppRepository;
import com.example.siren.domain.member.Member;
import com.example.siren.domain.member.repository.MemberRepository;
import com.example.siren.domain.member.service.MemberService;
import com.example.siren.domain.post.Post;
import com.example.siren.domain.post.PostSearchCond;
import com.example.siren.domain.post.PostUpdateDto;
import com.example.siren.domain.post.service.PostService;
import com.example.siren.web.error.ErrorResult;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final MemberService memberService;

    private final AppRepository appRepository;

    private final MapService mapService;

    @ResponseBody
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResult NoSuchExHandler(NoSuchElementException e){
        log.error("[exHandler] ex",e);
        return new ErrorResult("No-Value",e.getMessage());
    }

    @ResponseBody
    @PostMapping("/post-json")
    public Post requestBodyJson(@RequestBody Post post){
        log.info("name = {},loginId = {}, people ={}",post.getName(),post.getLoginId(),post.getPeople());
        Optional<Member> writer = memberService.findById(post.getWriterId());
        log.info("writer = {}",writer.get().getLoginId());

        String s = String.valueOf(post.getPeople());
        s.replace("x","1");
        log.info("replace = {}",s);

        post.setPeople(s);

        String check = memberService.checkForMap(writer.get().getLoginId(), post.getDow(), post.getTimes());
        if(check.equals("o")){
            Post sPost = postService.save(post);
            mapService.saveForDriver(writer.get().getLoginId(),sPost.getId(),sPost.getDow(),sPost.getTimes());
            post.setDow("o");
        }else {
            log.info("저장실패");
            post.setDow("x");
        }
        return post;
    }
    @ResponseBody
    @PostMapping("/writerId")
    public Post getPostFromWriterId(@RequestBody PostDto postDto){
        long id = postDto.getWriterId();
        log.info("writerId = {}",id);
        Post post = postService.findByWriterId(id).get();
        return post;
    }
    @ResponseBody
    @PostMapping("/info")
    public String info(@RequestBody PostDto postDto){
        long id = postDto.getWriterId();
        String info = postDto.getInfo();
        log.info("writerId = {}",id);
        PostUpdateDto pUD = new PostUpdateDto(id,info);
        postService.updateInfo(pUD);
        return "ok";
    }
    @ResponseBody
    @PostMapping("/driver")
    public String driver(@RequestBody PostDto postDto){
        Optional<Member> driver = memberService.findByLoginId(postDto.getInfo());
        postService.updateDriver(postDto.getWriterId(),driver.get().getId());
        return "ok";
    }

    @ResponseBody
    @PostMapping("/review")
    public PostReviewDto review(@RequestBody PostReviewDto reviewDto){
        String mapCheck = mapService.updateReview(reviewDto);
        if(mapCheck.equals("o")){
            postService.updateReview(reviewDto);
            reviewDto.setResult("o");
        }else {
            reviewDto.setResult("x");
        }
        return reviewDto;
    }
    @ResponseBody
    @GetMapping
    public String posts(@ModelAttribute("postSearch")PostSearchCond cond){

        log.info("cond.start={}",cond.getStart());
        List<Post> postList= postService.findItems(cond);
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
                sObject.put("people",postList.get(i).getPeople());
                sObject.put("price",postList.get(i).getPrice());
                sObject.put("times",postList.get(i).getTimes());
                sObject.put("dow",postList.get(i).getDow());
                sObject.put("review",postList.get(i).getReview());
                sObject.put("rCount",postList.get(i).getRCount());
                sObject.put("mode",postList.get(i).getMode());
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
        obj.put("people",post.get().getPeople());
        obj.put("price",post.get().getPrice());
        obj.put("time",post.get().getTimes());
        obj.put("dow",post.get().getDow());
        obj.put("gender",post.get().getGender());
        obj.put("smoke",post.get().getSmoke());
        obj.put("pet",post.get().getPet());
        obj.put("child",post.get().getChild());
        obj.put("baggage",post.get().getBaggage());
        obj.put("weight",post.get().getWeight());

        return obj.toString();
    }
    @ResponseBody
    @PostMapping("/app")
    public String requestBodyJson(@RequestBody Application app){
        log.info("appMemberID={}, dow={}",app.getMemberId(),app.getDow());
        Application save = appRepository.save(app);
        if(save==null){
            return "x";
        }else {
            return "o";
        }
    }
    @ResponseBody
    @GetMapping("/appList")
    public String appList(@RequestParam Long postId){
        Optional<Post> post = postService.findById(postId);
        String app = post.get().getApp();
        String apps[] = app.split(",");
        JSONObject obj = new JSONObject();
        try {
            JSONArray jArray = new JSONArray();//배열이 필요할때
            for (int i = 0; i < apps.length; i++)//배열
            {
                long id = Long.parseLong(apps[i]);
                PostUpdateDto pd = new PostUpdateDto(postId,id);
                Application application = appRepository.findByMemberId(pd);
                Optional<Member> member = memberService.findById(id);
                JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
                sObject.put("id",id);
                sObject.put("name", member.get().getName());
                sObject.put("profile",member.get().getProfile());
                sObject.put("score",member.get().getScore());
                sObject.put("dow", application.getDow());
                jArray.put(sObject);
            }
            obj.put("item", jArray);//배열을 넣음
            return obj.toString();

        } catch (JSONException e) {
            return "error";
        }
    }

    @ResponseBody
    @PostMapping("/appDelete")
    public String appDelete(@RequestBody PostUpdateDto param){
        postService.deleteApp(param);
        appRepository.delete(param);
        return "ok";
    }

}
