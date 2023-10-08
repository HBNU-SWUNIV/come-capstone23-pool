package com.example.siren.domain.MnN.repository.map;

import com.example.siren.domain.MnN.MemberAndPost;
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

    public String reviewUpdate(Long postId,Long memberId){
        List<MemberAndPost> maps = repository.findByPostMemberId(memberId);
        String result ="x";
        for(MemberAndPost i : maps){
            Long id = i.getPost().getId();
            if(id.equals(postId)){
                log.info("mapId = {}",postId);
                Optional<MemberAndPost> map = repository.findById(i.getId());
                if(map.get().isReview()==false){
                    map.get().setReview(true);
                    result= "o";
                }
            }
        }
        log.info("mapReviewResult = {}",result);
      return result;
    }


    public List<MemberAndPost> findByMemberId(Long memberId){
        return repository.findByPostMemberId(memberId);
    }
    public List<MemberAndPost> findByPostId(Long postId){
        return repository.findByPostIdLike(postId);
    }
}
