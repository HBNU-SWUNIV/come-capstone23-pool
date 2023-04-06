package com.example.siren.domain.MnN.repository.mac;

import com.example.siren.domain.MnN.MemberAndChat;
import com.example.siren.domain.chat.ChatRoom;
import com.example.siren.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SpringDataJpaMacRepository extends JpaRepository<MemberAndChat,Long >{
    List<MemberAndChat> findByMemberId(Long memberId);
    List<MemberAndChat> findByChatRoomRoomId(String chatId);

}
