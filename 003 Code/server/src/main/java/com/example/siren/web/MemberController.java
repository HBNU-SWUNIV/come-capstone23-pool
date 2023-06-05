package com.example.siren.web;

import com.example.siren.domain.MnN.MemberAndChat;
import com.example.siren.domain.MnN.MemberAndPost;
import com.example.siren.domain.MnN.repository.mac.MacRepositoryImpl;
import com.example.siren.domain.MnN.repository.map.MapRepositoryImpl;
import com.example.siren.domain.MnN.service.MacService;
import com.example.siren.domain.member.Member;
import com.example.siren.domain.member.MemberUpdateDto;
import com.example.siren.domain.member.repository.MemberRepository;
import com.example.siren.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * 회원가입
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    private  final MemberRepository memberRepository;
    private final MacRepositoryImpl macService;
    private final MapRepositoryImpl mapRepository;

    @ResponseBody
    @PostMapping("/join-json")
    public Member requestBodyJson(@RequestBody Member member){
        log.info("loginId={}, name={},password={}",member.getLoginId(),member.getName(),member.getPassword());
        member.setProfile(1L);
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
    @ResponseBody
    @PostMapping("/plusMoney")
    public Member plusMoney(@RequestBody MemberUpdateDto updateDto){
        memberService.plusMoney(updateDto);
        Optional<Member> member = memberService.findById(updateDto.getId());
        return member.get();
    }
    @ResponseBody
    @PostMapping("/minusMoney")
    public String minusMoney(@RequestBody MemberUpdateDto updateDto){
        String result = memberService.minusMoney(updateDto);
        JSONObject sObject = new JSONObject();
        if (result.equals("x")){
            sObject.put("result","x");
        }else {
            sObject.put("result","o");
        }
        return sObject.toString();
    }
    @ResponseBody
    @PostMapping("/sendMoney")
    public String sendMoney(@RequestBody MemberUpdateDto updateDto){
        String result = memberService.sendMoney(updateDto);
        JSONObject sObject = new JSONObject();
        if (result.equals("x")){
            sObject.put("result","x");
        }else {
            sObject.put("result","o");
        }
        return sObject.toString();
    }


    @ResponseBody
    @PostMapping("/findLoginId")
    public String findByNickname(@RequestBody FindByNickDTO updateParam){
        Optional<Member> member = memberService.findByNickname(updateParam.getName());
        log.info("gender={}paramGender={}",member.get().getGender(),updateParam.getGender());
        JSONObject sObject = new JSONObject();
        if(member.get().getGender().equals(updateParam.getGender())){
            sObject.put("result",member.get().getLoginId());
        } else{
            sObject.put("result","x");
        }
        return sObject.toString();
    }
    @ResponseBody
    @PostMapping("/findPw")
    public String findPw(@RequestBody FindByNickDTO updateParam){
        Optional<Member> member = memberService.findByNickname(updateParam.getName());
        log.info("gender={}paramGender={}",member.get().getGender(),updateParam.getGender());
        JSONObject sObject = new JSONObject();
        if(member.get().getGender().equals(updateParam.getGender())&&member.get().getLoginId().equals(updateParam.getLoginId())){
            sObject.put("memberId",member.get().getId());
            sObject.put("password",member.get().getPassword());
            sObject.put("name",member.get().getName());
            sObject.put("result","o");

        } else{
            sObject.put("result","x");
        }
        return sObject.toString();
    }

    @ResponseBody
    @GetMapping("/check")
    public String check(@RequestParam String memberId){
        Optional<Member> getMember = memberRepository.findByLoginId(memberId);
        JSONObject obj = new JSONObject();

        if(getMember.isEmpty()){
            obj.put("result","y");
        }else {
            obj.put("result","n");
        }
        return obj.toString();
    }

}
