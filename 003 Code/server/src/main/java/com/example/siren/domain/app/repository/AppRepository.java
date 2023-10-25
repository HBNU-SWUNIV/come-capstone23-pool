package com.example.siren.domain.app.repository;

import com.example.siren.domain.app.Application;
import com.example.siren.domain.member.Member;
import com.example.siren.domain.member.MemberUpdateDto;
import com.example.siren.domain.post.PostUpdateDto;

import java.util.Optional;

public interface AppRepository {
    Application save(Application app);
    Application findByMemberId(PostUpdateDto param);
    public void delete(PostUpdateDto param);
}
