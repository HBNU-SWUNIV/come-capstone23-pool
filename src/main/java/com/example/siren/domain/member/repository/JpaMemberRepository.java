package com.example.siren.domain.member.repository;

import com.example.siren.domain.chat.ChatRoom;
import com.example.siren.domain.member.Member;
import com.example.siren.domain.member.MemberUpdateDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class JpaMemberRepository implements MemberRepository{
    
    private final SpringDataJpaMemberRepository repository;


    @Override
    public Member save(Member member) {
        if(findByLoginId(member.getLoginId()).isEmpty()) {
            log.info("비어있음");
            return repository.save(member);//중복검사
        }else {
            return null;
        }
    }
  /*  public List<Member> getMemberByChatRoomId(String chatRoomId) {
        return repository.findByChatRoomId(chatRoomId);
    }*/
    @Override
    public void update(MemberUpdateDto updateParam) {
        Long memberId = updateParam.getId();
        Member findMember = repository.findById(memberId).orElseThrow();
        findMember.setPassword(updateParam.getPassword());
        findMember.setName(updateParam.getName());
    }

    @Override
    public Optional<Member> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<Member> findByLoginId(String loginId){
        List<Member> all = findAll();
        for(Member m: all){
            if(m.getLoginId().equals(loginId))
                return Optional.of(m);
        }
        return Optional.empty();
    }

    @Override
    public List<Member> findAll() {
        return repository.findAll();
    }
}
