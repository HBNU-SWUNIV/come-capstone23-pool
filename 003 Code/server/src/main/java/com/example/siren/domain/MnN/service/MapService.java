package com.example.siren.domain.MnN.service;

import com.example.siren.domain.MnN.Crew;
import com.example.siren.domain.MnN.MemberAndPost;
import com.example.siren.domain.MnN.repository.map.MapRepositoryImpl;
import com.example.siren.domain.member.Member;
import com.example.siren.domain.member.repository.JpaMemberRepository;
import com.example.siren.domain.member.service.JpaMemberService;
import com.example.siren.domain.post.Post;
import com.example.siren.domain.post.PostUpdateDto;
import com.example.siren.domain.post.repository.JpaPostRepository;
import com.example.siren.web.post.PostReviewDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    private final JpaMemberService memberService;


    public MemberAndPost save(String memberId, Long postId, String dow, String times) {
        Optional<Member> member = memberRepository.findByLoginId(memberId);
        Optional<Post> post = postRepository.findById(postId);
        log.info("mapMember={},mapPost={},mapPostPeople={}", member.get().getName(), post.get().getContent(),post.get().getPeople());
        MemberAndPost map = new MemberAndPost(member.get(), post.get(), dow, times, "x");
        String[] people = String.valueOf(post.get().getPeople()).split("");
        String[] dows = dow.split(",");
        for (String i : dows) {
            if (Integer.parseInt(people[Integer.parseInt(i) - 1]) - 1 >= 0) {
                people[Integer.parseInt(i) - 1] = String.valueOf(Integer.parseInt(people[Integer.parseInt(i) - 1]) - 1);
            } else {
                break;
            }
        }
        String peopleNumD = "";
        for (int i = 0; i < people.length; i++) {
            peopleNumD += String.valueOf(people[i]);
        }
        post.get().setPeople(peopleNumD);
        log.info("peopleNumD : {}",peopleNumD);
        PostUpdateDto pUD = new PostUpdateDto(postId, peopleNumD);
        postRepository.update(pUD);
        repository.save(map);
        return map;
    }

    public void delete(String memberId, Long postId) {
        Optional<Member> member = memberRepository.findByLoginId(memberId);
        Optional<Post> post = postRepository.findById(postId);
        log.info("mapMember={},mapPost={},mapPostPeople={}", member.get().getName(), post.get().getContent(),post.get().getPeople());

        List<MemberAndPost> maps = repository.findByMemberId(member.get().getId());
        for (MemberAndPost i : maps){
            Long id = i.getPost().getId();
            if(id == postId){
                memberService.ReverseDow(member.get().getId(),i.getDow(),i.getTimes());
                repository.delete(i);

                String[] people = String.valueOf(post.get().getPeople()).split("");
                log.info("oldDow = {}",i.getDow());
                String[] dows = i.getDow().split(",");
                for (String j : dows) {
                    people[Integer.parseInt(j) - 1] = String.valueOf(Integer.parseInt(people[Integer.parseInt(j) - 1]) + 1);
                }
                String peopleNumD = "";
                for (int j = 0; j < people.length; j++) {
                    peopleNumD += String.valueOf(people[j]);
                }
                post.get().setPeople(peopleNumD);
                log.info("peopleNumD : {}",peopleNumD);
                PostUpdateDto pUD = new PostUpdateDto(postId, peopleNumD);
                postRepository.update(pUD);
            }
        }
    }

    public MemberAndPost saveForDriver(String memberId, Long postId, String dow, String times) {
        Optional<Member> member = memberRepository.findByLoginId(memberId);
        Optional<Post> post = postRepository.findById(postId);
        MemberAndPost map = new MemberAndPost(member.get(), post.get(), dow, times, "x");
        return repository.save(map);
    }

    public List<Post> findByMemberId(Long memberId) {
        List<MemberAndPost> maps = repository.findByMemberId(memberId);
        List<Post> posts = new ArrayList<>();
        for (MemberAndPost i : maps) {
            posts.add(i.getPost());
        }
        return posts;
    }

    public boolean saveCheck(Long memberId,Long postId){
        List<MemberAndPost> maps = repository.findByMemberId(memberId);
        Optional<Post> post = postRepository.findById(postId);
        boolean result = true;
        for(MemberAndPost i: maps){
            String[] map = i.getDow().split(",");
            String[] postD = post.get().getDow().split(",");

            for(int j=0;j<5;j++){
                if(Integer.parseInt(map[j])+Integer.parseInt(postD[j])>1){
                    result = false;
                }
            }
        }
        return result;
    }


    public List<Crew> findByPostId(Long postId) {
        List<MemberAndPost> maps = repository.findByPostId(postId);
        Optional<Post> post = postRepository.findById(postId);
        String driver = post.get().getDriver();
        String[] drivers = driver.split(",");
        List<Crew> crews = new ArrayList<>();
        for (MemberAndPost i : maps) {
            String tDriver = "x";
            for(String j : drivers){
                if(Long.parseLong(j) == i.getPostMember().getId()){
                    tDriver ="o";
                }
            }
            Crew crew = new Crew(i.getPostMember().getId(), i.getPostMember().getName(), i.getDow(), i.getTimes(),tDriver);
            crews.add(crew);
        }
        return crews;
    }

    public String updateReview(PostReviewDto reviewDto) {
        return repository.reviewUpdate(reviewDto);
    }

}


