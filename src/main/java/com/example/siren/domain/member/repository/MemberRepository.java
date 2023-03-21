package com.example.siren.domain.member.repository;

import com.example.siren.domain.member.Member;
import com.example.siren.domain.member.MemberUpdateDto;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    void update(MemberUpdateDto updateParam);

    Optional<Member> findById(Long id);

    Optional<Member> findByLoginId(String loginId);

    List<Member> findAll();


}
