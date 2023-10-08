package com.example.siren.domain.post.service;

import com.example.siren.domain.member.Member;
import com.example.siren.domain.member.MemberUpdateDto;
import com.example.siren.domain.member.repository.MemberRepository;
import com.example.siren.domain.post.Post;
import com.example.siren.domain.post.PostSearchCond;
import com.example.siren.domain.post.PostUpdateDto;
import com.example.siren.domain.post.repository.PostRepository;
import com.example.siren.web.post.PostReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Transactional
public class JpaPostService implements PostService {

    private final PostRepository repository;

    @Override
    public Post save(Post post) {
        return repository.save(post);
    }

    @Override
    public void update(PostUpdateDto updateParam) {
        repository.update(updateParam);
    }
    @Override
    public void updateInfo(PostUpdateDto updateParam) {
        repository.updateInfo(updateParam);
    }

    @Override
    public void application(PostUpdateDto updateParam) {
        repository.application(updateParam);
    }
    @Override
    public void deleteApp(PostUpdateDto updateParam) {
        repository.deleteApp(updateParam);
    }
    @Override
    public void updateDriver(Long id, Long driver) {
        repository.updateDriver(id,driver);
    }

    @Override
    public String updateReview(PostReviewDto dto) {
        Post findPost = repository.findById(dto.getId()).orElseThrow();
        float oReview = findPost.getReview();
        int rCount = findPost.getRCount();
        float origin = oReview * rCount;
        rCount++;
        float nReview = origin + dto.getReview()/rCount;
        repository.updateReview(dto.getId(),nReview,rCount);
        return "ok";
    }


    @Override
    public Optional<Post> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Post> findByWriterId(Long id) {
        return repository.findByWriterId(id);
    }

    @Override
    public List<Post> findItems(PostSearchCond cond) {
        return repository.findAll(cond);
    }
}
