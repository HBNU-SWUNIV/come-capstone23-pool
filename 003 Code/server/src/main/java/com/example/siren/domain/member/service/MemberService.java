package com.example.siren.domain.member.service;

import com.example.siren.domain.member.Member;
import com.example.siren.domain.member.MemberUpdateDto;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    Member save(Member member);

    void update(MemberUpdateDto updateParam);
    void updateProfile(MemberUpdateDto updateParam);
    void updateCar(MemberUpdateDto updateParam);

    void plusMoney(MemberUpdateDto updateDto);

    void minus(MemberUpdateDto updateDto);
    String minusMoney(MemberUpdateDto updateDto);

    String sendMoney(MemberUpdateDto updateDto);

    Optional<Member> findById(Long id);
    Optional<Member> findByNickname(String name);
    Optional<Member> findByLoginId(String name);

    List<Member> findItems();

    Member delete(long id);

    void token(MemberUpdateDto token);

    String checkForMap(String memberId, String dow,String times);
}
