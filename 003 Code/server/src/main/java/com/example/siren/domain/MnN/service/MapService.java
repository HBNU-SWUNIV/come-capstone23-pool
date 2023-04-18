package com.example.siren.domain.MnN.service;

import com.example.siren.domain.MnN.Crew;
import com.example.siren.domain.MnN.MemberAndPost;
import com.example.siren.domain.MnN.repository.map.MapRepositoryImpl;
import com.example.siren.domain.member.Member;
import com.example.siren.domain.member.repository.JpaMemberRepository;
import com.example.siren.domain.post.Post;
import com.example.siren.domain.post.PostUpdateDto;
import com.example.siren.domain.post.repository.JpaPostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor

public class MapService {
    private final MapRepositoryImpl repository;
    private final JpaMemberRepository memberRepository;
    private final JpaPostRepository postRepository;

    public MemberAndPost save(String memberId,Long postId,String dow,String times){
        Optional<Member> member = memberRepository.findByLoginId(memberId);
        Optional<Post> post = postRepository.findById(postId);
        log.info("mapMember={},mapPost={}",member.get().getName(),post.get().getContent());
        MemberAndPost map = new MemberAndPost(member.get(),post.get(),dow,times);
        String[] people = String.valueOf(post.get().getPeople()).split("");
        String[] dows  =dow.split(",");
        for(String i : dows){
            if(Integer.parseInt(people[Integer.parseInt(i)-1])-1>=0) {
                people[Integer.parseInt(i) - 1] = String.valueOf(Integer.parseInt(people[Integer.parseInt(i) - 1]) - 1);
            }else{
                break;
            }
        }
        String peopleNumD="";
        for(int i=0;i<people.length;i++){
            peopleNumD += String.valueOf(people[i]);
        }
        post.get().setPeople(Integer.parseInt(peopleNumD));
        PostUpdateDto pUD = new PostUpdateDto(postId,Integer.parseInt(peopleNumD));
        postRepository.update(pUD);
        repository.save(map);
        return map;
    }
    public List<Post> findByMemberId(Long memberId){
        List<MemberAndPost> maps = repository.findByMemberId(memberId);
        List<Post> posts = new ArrayList<>();
        for (MemberAndPost i : maps){
            posts.add(i.getPost());
        }
        return posts;
    }
    public List<Crew> findByPostId(Long postId){
        List <MemberAndPost> maps = repository.findByPostId(postId);
        List<Crew> crews = new ArrayList<>();
        for(MemberAndPost i :maps){
            Crew crew = new Crew(i.getPostMember().getId(),i.getPostMember().getName(),i.getDow(),i.getTimes());
            crews.add(crew);
        }
        return crews;
    }
}
