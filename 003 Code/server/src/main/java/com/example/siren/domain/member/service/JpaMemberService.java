package com.example.siren.domain.member.service;

import com.example.siren.domain.member.Member;
import com.example.siren.domain.member.MemberUpdateDto;
import com.example.siren.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
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

    public void updateProfile(MemberUpdateDto updateParam) {
        memberRepository.updateProfile(updateParam);
    }
    public void updateCar(MemberUpdateDto updateParam) {
        memberRepository.updateCar(updateParam);
    }

    @Override
    public void plusMoney(MemberUpdateDto updateDto) {
        Optional<Member> member = memberRepository.findById(updateDto.getId());
        int money = member.get().getMoney() + updateDto.getMoney();
        memberRepository.updateMoney(updateDto.getId(),money);
    }

    @Override
    public String minusMoney(MemberUpdateDto updateDto) {
        Optional<Member> member = memberRepository.findById(updateDto.getId());
        int money = member.get().getMoney() - updateDto.getMoney();
        if(money<0){
            return "x";
        }else {
            memberRepository.updateMoney(updateDto.getId(),money);
            return "o";
        }
    }

    @Override
    public String sendMoney(MemberUpdateDto updateDto) {
        Optional<Member> member = memberRepository.findById(updateDto.getId());
        Optional<Member> sender = memberRepository.findByNickname(updateDto.getSender());
        log.info("senderName={} senderMoney={}",sender.get().getName(),sender.get().getMoney());

        int money = member.get().getMoney() - updateDto.getMoney();

        if(money<0){
            return "x";
        }else {
            int sMoney = sender.get().getMoney() +updateDto.getMoney();
            memberRepository.updateMoney(updateDto.getId(),money);
            memberRepository.updateMoney(sender.get().getId(),sMoney);
            return "o";
        }
    }

    public void minus(MemberUpdateDto updateDto) {
        memberRepository.minus(updateDto);
    }

    public void token(MemberUpdateDto updateDto) {
        memberRepository.token(updateDto);
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }
    @Override
    public Optional<Member> findByNickname(String name) {
        return memberRepository.findByNickname(name);
    }
    @Override
    public Optional<Member> findByLoginId(String name) {
        return memberRepository.findByLoginId(name);
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
