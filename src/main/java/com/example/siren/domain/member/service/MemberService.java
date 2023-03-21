package com.example.siren.domain.member.service;

import com.example.siren.domain.member.Member;
import com.example.siren.domain.member.MemberUpdateDto;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    Member save(Member member);

    void update(MemberUpdateDto updateParam);

    Optional<Member> findById(Long id);

    List<Member> findItems();
}
