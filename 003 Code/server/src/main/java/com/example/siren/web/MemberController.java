package com.example.siren.web;

import com.example.siren.domain.MnN.MemberAndChat;
import com.example.siren.domain.MnN.MemberAndPost;
import com.example.siren.domain.MnN.repository.mac.MacRepositoryImpl;
import com.example.siren.domain.MnN.repository.map.MapRepositoryImpl;
import com.example.siren.domain.MnN.service.MacService;
import com.example.siren.domain.member.Member;
import com.example.siren.domain.member.MemberUpdateDto;
import com.example.siren.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 회원가입
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final MacRepositoryImpl macService;
    private final MapRepositoryImpl mapRepository;

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
    @ResponseBody
    @PostMapping("/delete")
    public Member delete(@RequestBody MemberUpdateDto updateParam){
        log.info("id={}",updateParam.getId());
        List<MemberAndChat> mac = macService.findByMemberId(updateParam.getId());
        for(MemberAndChat i : mac) {
            macService.delete(i);
        }
        List<MemberAndPost> map = mapRepository.findByMemberId(updateParam.getId());
        for(MemberAndPost i : map) {
            mapRepository.delete(i);
        }

        return memberService.delete(updateParam.getId());
    }

}
