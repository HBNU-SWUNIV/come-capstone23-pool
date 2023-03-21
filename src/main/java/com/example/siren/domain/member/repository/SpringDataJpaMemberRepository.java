package com.example.siren.domain.member.repository;

import com.example.siren.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member,Long> {
    //List<Member>findByEnrollmentsRoomId(String chatId);
}
