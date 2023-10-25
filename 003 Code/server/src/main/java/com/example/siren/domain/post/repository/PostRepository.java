package com.example.siren.domain.post.repository;

import com.example.siren.domain.app.Application;
import com.example.siren.domain.member.Member;
import com.example.siren.domain.post.Post;
import com.example.siren.domain.post.PostSearchCond;
import com.example.siren.domain.post.PostUpdateDto;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(Post post);

    void update(PostUpdateDto updateParam);
    void updateInfo(PostUpdateDto updateParam);
    void updateDriver(Long id,Long id2);
    void updateReview(Long id, float review,int rCount);
    void deleteApp(PostUpdateDto updateParam);
    void application(Application param);

    Optional<Post> findById(Long id);

    Optional<Post>findByWriterId(Long id);

    List<Post> findAll(PostSearchCond cond);

    List<Post> findAll2();

}
