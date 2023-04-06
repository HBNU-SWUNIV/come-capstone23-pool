package com.example.siren.domain.post.repository;

import com.example.siren.domain.member.Member;
import com.example.siren.domain.post.Post;
import com.example.siren.domain.post.PostSearchCond;
import com.example.siren.domain.post.PostUpdateDto;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(Post post);

    void update(Long id, PostUpdateDto updateParam);

    Optional<Post> findById(Long id);

    Optional<Post>findByWriterId(Long id);

    List<Post> findAll(PostSearchCond cond);

}
