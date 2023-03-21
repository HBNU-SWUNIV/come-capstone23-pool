package com.example.siren.domain.login;

import com.example.siren.domain.member.Member;
import com.example.siren.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String loginId,String password){
        Optional<Member> findMemberOptional = memberRepository.findByLoginId(loginId);
        Member member = findMemberOptional.get();
        if(member.getPassword().equals(password)){
            return member;
        }else{
            return null;
        }
    }
}
