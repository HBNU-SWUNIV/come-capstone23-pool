package com.example.siren.domain.MnN.repository.map;

import com.example.siren.domain.MnN.MemberAndPost;

public interface MapRepository {

    MemberAndPost save(MemberAndPost memberAndPost);

    MemberAndPost delete(MemberAndPost memberAndPost);
}
