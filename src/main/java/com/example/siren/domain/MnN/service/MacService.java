package com.example.siren.domain.MnN.service;

import com.example.siren.domain.MnN.MemberAndChat;
import com.example.siren.domain.MnN.repository.MacRepository;
import com.example.siren.domain.MnN.repository.MacRepositoryImpl;
import com.example.siren.domain.MnN.repository.SpringDataJpaMacRepository;
import com.example.siren.domain.chat.ChatRoom;
import com.example.siren.domain.chat.ChatService;
import com.example.siren.domain.member.Member;
import com.example.siren.domain.member.repository.JpaMemberRepository;
import com.example.siren.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MacService {
    private final MacRepositoryImpl repository;
    private final ChatService chatService;
    private final JpaMemberRepository memberRepository;

    public MemberAndChat save(Long memberId,String chatId) {
        Optional<Member> member = memberRepository.findById(memberId);
        Optional<ChatRoom> chat = chatService.findRoomById(chatId);
        MemberAndChat mac = new MemberAndChat(member.get(),chat.get());
        return repository.save(mac);
    }

    public MemberAndChat delete(Long memberId,String chatId) {
        Optional<Member> member = memberRepository.findById(memberId);
        Optional<ChatRoom> chat = chatService.findRoomById(chatId);
        MemberAndChat mac = new MemberAndChat(member.get(),chat.get());
        repository.delete(mac);
        return mac;
    }

    public List<MemberAndChat> findByMemberId(Long memberId){
        return repository.findByMemberId(memberId);
    }

    public List<MemberAndChat> findByChatRoomId(String chatId){
        return repository.findByChatRoomId(chatId);
    }


}
