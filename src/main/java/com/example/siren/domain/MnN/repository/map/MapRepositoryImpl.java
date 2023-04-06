package com.example.siren.domain.MnN.repository.map;

import com.example.siren.domain.MnN.MemberAndPost;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<MemberAndPost> findByMemberId(Long memberId){
        return repository.findByPostMemberId(memberId);
    }
    public List<MemberAndPost> findByPostId(Long postId){
        return repository.findByPostIdLike(postId);
    }
}
