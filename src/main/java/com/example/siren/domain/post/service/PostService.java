package com.example.siren.domain.post.service;

import com.example.siren.domain.member.Member;
import com.example.siren.domain.member.MemberUpdateDto;
import com.example.siren.domain.post.Post;
import com.example.siren.domain.post.PostUpdateDto;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Post save(Post post);

    void update(Long id, PostUpdateDto updateParam);

    Optional<Post> findById(Long id);

    List<Post> findItems();
}
