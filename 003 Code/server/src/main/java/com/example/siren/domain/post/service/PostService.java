package com.example.siren.domain.post.service;

import com.example.siren.domain.app.Application;
import com.example.siren.domain.member.Member;
import com.example.siren.domain.member.MemberUpdateDto;
import com.example.siren.domain.post.Post;
import com.example.siren.domain.post.PostSearchCond;
import com.example.siren.domain.post.PostUpdateDto;
import com.example.siren.web.post.PostReviewDto;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Post save(Post post);

    void update(PostUpdateDto updateParam);
    void updateInfo(PostUpdateDto updateParam);
    String updateReview(PostReviewDto dto);
    void deleteApp(PostUpdateDto updateParam);
    void application(Application param);
    Optional<Post> findById(Long id);
    Optional<Post>findByWriterId(Long id);

    List<Post> findItems(PostSearchCond cond);

    void updateDriver(Long id,Long id2);
}
