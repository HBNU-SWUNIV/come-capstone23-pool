package com.example.siren.web;

import com.example.siren.domain.member.Member;
import com.example.siren.domain.member.MemberUpdateDto;
import com.example.siren.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 회원가입
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @ResponseBody
    @PostMapping("/join-json")
    public Member requestBodyJson(@RequestBody Member member){
        log.info("loginId={}, name={},password={}",member.getLoginId(),member.getName(),member.getPassword());
        Member save = memberService.save(member);
        if(save == null){
            return null; //id중복 검사
        }
        return member;
    }
    @ResponseBody
    @PostMapping("/update")
    public Member update(@RequestBody MemberUpdateDto updateParam){
        log.info("Password={}, Name={}",updateParam.getPassword(),updateParam.getName());
        memberService.update(updateParam);
        Member member = memberService.findById(updateParam.getId()).get();
        return member;
    }


}
