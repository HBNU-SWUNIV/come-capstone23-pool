package com.example.siren.domain.MnN.repository.map;

import com.example.siren.domain.MnN.MemberAndPost;
import com.example.siren.domain.member.service.JpaMemberService;
import com.example.siren.web.post.PostReviewDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class MapRepositoryImpl implements MapRepository {

    private final SpringDataJpaMapRepository repository;

    @Override
    public MemberAndPost save(MemberAndPost memberAndPost) {
        return repository.save(memberAndPost);
    }

    @Override
    public MemberAndPost delete(MemberAndPost memberAndPost) {
        repository.delete(memberAndPost);
        return memberAndPost;
    }
    public String reviewUpdate(PostReviewDto reviewDto){
        List<MemberAndPost> maps = repository.findByPostMemberId(reviewDto.getMemberId());
        String result ="x";
        for(MemberAndPost i : maps){
            Long id = i.getPost().getId();
            if(id.equals(reviewDto.getId())){
                log.info("reviewPostId = {}",reviewDto.getId());
                Optional<MemberAndPost> map = repository.findById(i.getId());
                log.info("mapId = {}",map.get().getId());
                if(map.get().getReview().equals("x")){
                    log.info("리뷰 등록");
                    map.get().setReview(String.valueOf(reviewDto.getReview()));

                    result= "o";
                }
            }
        }
        log.info("mapReviewResult = {}",result);
        return result;
    }
    @Override
    public List<MemberAndPost> findAll(){
        return repository.findAll();
    }
    public List<MemberAndPost> findByMemberId(Long memberId){
        return repository.findByPostMemberId(memberId);
    }
    public List<MemberAndPost> findByPostId(Long postId){
        return repository.findByPostIdLike(postId);
    }
}
