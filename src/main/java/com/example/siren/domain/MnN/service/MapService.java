package com.example.siren.domain.MnN.service;

import com.example.siren.domain.MnN.Crew;
import com.example.siren.domain.MnN.MemberAndPost;
import com.example.siren.domain.MnN.repository.map.MapRepositoryImpl;
import com.example.siren.domain.member.Member;
import com.example.siren.domain.member.repository.JpaMemberRepository;
import com.example.siren.domain.post.Post;
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
        MemberAndPost map = new MemberAndPost(member.get(),post.get(),dow,times);
        repository.save(map);
        return map;
    }
    public List<MemberAndPost> findByMemberId(Long memberId){
        return repository.findByMemberId(memberId);
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
