package com.example.siren.domain.member.service;

import com.example.siren.domain.member.Member;
import com.example.siren.domain.member.MemberUpdateDto;
import com.example.siren.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Transactional
public class JpaMemberService implements MemberService{
    private final MemberRepository memberRepository;
    @Override
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public void update(MemberUpdateDto updateParam) {
        memberRepository.update(updateParam);
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public List<Member> findItems() {
        return memberRepository.findAll();
    }

    @Override
    public Member delete(long id) {
        Optional<Member> member = memberRepository.findById(id);
        return memberRepository.delete(member.get());
    }
}
