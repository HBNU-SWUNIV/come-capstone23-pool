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

    public String checkForMap(String memberId, String dow, String times){
        Optional<Member> member= memberRepository.findByLoginId(memberId);
        String[] timeArr = times.split(",");
        String[] dows = dow.split(",");
        String memberDow []= member.get().getDow().split("/"); //0,0,0,0,0/0,0,0,0,0
        log.info("am={} pm={} ",memberDow[0],memberDow[1]);
        log.info("timeArr.length={} ",timeArr.length);
        String[] am = memberDow[0].split(",");
        String[] pm = memberDow[1].split(",");
        boolean check = false;
        if(timeArr.length>1){//am,pm
            log.info("am ={},pm={} ",am.length,pm.length);
            for(String i : dows) {
                log.info("i ={} ",i);
                if(am[Integer.parseInt(i) - 1].equals("1")||pm[Integer.parseInt(i)-1].equals("1")){
                    check=false;
                    break;
                }else{
                    am[Integer.parseInt(i) - 1] = "1";
                    pm[Integer.parseInt(i)-1] = "1";
                    check= true;
                }
            }
        } else if (timeArr.length==1) {//am or pm
            log.info("pm={} ",pm.length);
            if(timeArr[0].startsWith("A")){
                log.info("time={} ",timeArr[0]);
                for(String i : dows) {
                    if(am[Integer.parseInt(i) - 1].equals("1")){
                        check=false;
                        break;
                    }else{
                        am[Integer.parseInt(i) - 1] = "1";
                        check=true;
                    }
                }
            } else if (timeArr[0].startsWith("P")) {
                log.info("time={} ",timeArr[0]);
                for(String i : dows) {
                    if(pm[Integer.parseInt(i) - 1].equals("1")){
                        check=false;
                        break;
                    }else{
                        pm[Integer.parseInt(i) - 1] = "1";
                        check=true;
                    }
                }
            }
        }

        String dowResult = "" ;
        for(String i:am){
            dowResult+=i+",";
        }
        dowResult =dowResult.substring(0, dowResult.length() - 1);
        dowResult+="/";
        for(String i:pm){
            dowResult+=i+",";
        }
        dowResult =dowResult.substring(0, dowResult.length() - 1);
        log.info("dowResult = {}",dowResult);
        if(check){
            memberRepository.updateDow(member.get().getId(),dowResult);
            return "o";
        }else {
            return "x";
        }

    }

    public String ReverseDow(Long memberId, String dow, String times){
        Optional<Member> member= memberRepository.findById(memberId);
        String[] timeArr = times.split(",");
        String[] dows = dow.split(",");
        log.info("member ={}",memberId);
        log.info("member ={}",member.get().getId());
        String memberDow []= member.get().getDow().split("/"); //0,0,0,0,0/0,0,0,0,0
        log.info("am={} pm={} ",memberDow[0],memberDow[1]);
        log.info("timeArr.length={} ",timeArr.length);
        String[] am = memberDow[0].split(",");
        String[] pm = memberDow[1].split(",");
        if(timeArr.length>1){//am,pm
            log.info("am ={},pm={} ",am.length,pm.length);
            for(String i : dows) {
                log.info("i ={} ",i);
                if(am[Integer.parseInt(i) - 1].equals("1")||pm[Integer.parseInt(i)-1].equals("1")){
                    am[Integer.parseInt(i) - 1] = "0";
                    pm[Integer.parseInt(i)-1] = "0";
                    break;
                }
            }
        } else if (timeArr.length==1) {//am or pm
            log.info("pm={} ",pm.length);
            if(timeArr[0].startsWith("A")){
                log.info("time={} ",timeArr[0]);
                for(String i : dows) {
                    if(am[Integer.parseInt(i) - 1].equals("1")){
                        am[Integer.parseInt(i) - 1] = "0";
                    }
                }
            } else if (timeArr[0].startsWith("P")) {
                log.info("time={} ",timeArr[0]);
                for(String i : dows) {
                    if(pm[Integer.parseInt(i) - 1].equals("1")) {
                        pm[Integer.parseInt(i) - 1] = "0";
                    }
                }
            }
        }

        String dowResult = "" ;
        for(String i:am){
            dowResult+=i+",";
        }
        dowResult =dowResult.substring(0, dowResult.length() - 1);
        dowResult+="/";
        for(String i:pm){
            dowResult+=i+",";
        }
        dowResult =dowResult.substring(0, dowResult.length() - 1);
        log.info("dowResult = {}",dowResult);
        memberRepository.updateDow(member.get().getId(),dowResult);
        return dowResult;
    }
}
