package com.example.siren.domain.MnN.repository;

import com.example.siren.domain.MnN.MemberAndChat;
import com.example.siren.domain.chat.ChatRoom;
import com.example.siren.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class MacRepositoryImpl implements MacRepository {

    private final SpringDataJpaMacRepository repository;

    @Override
    public MemberAndChat save(MemberAndChat mac) {
        return repository.save(mac);
    }

    @Override
    public MemberAndChat delete(MemberAndChat mac) {
        repository.delete(mac);
        return mac;
    }

    public List<MemberAndChat> findByMemberId(Long memberId){
        return repository.findByMemberId(memberId);
    }

    public List<MemberAndChat> findByChatRoomId(String chatId){
        return repository.findByChatRoomRoomId(chatId);
    }

}
