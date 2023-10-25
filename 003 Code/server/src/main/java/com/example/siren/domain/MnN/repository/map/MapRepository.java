package com.example.siren.domain.MnN.repository.map;

import com.example.siren.domain.MnN.MemberAndPost;
import com.example.siren.web.post.PostReviewDto;

import java.util.List;

public interface MapRepository {

    MemberAndPost save(MemberAndPost memberAndPost);

    MemberAndPost delete(MemberAndPost memberAndPost);
    List<MemberAndPost> findAll();

    String reviewUpdate(PostReviewDto reviewDto);
}
