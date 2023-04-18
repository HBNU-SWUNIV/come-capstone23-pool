package com.example.siren.domain.chat;

import com.example.siren.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SpringDataJpaChatRepository extends JpaRepository<ChatRoom,String> {
    List<ChatRoom> findByEnrollmentsMemberId(Long memberId);

    @Query("SELECT c FROM ChatRoom c JOIN c.enrollments me JOIN me.member m WHERE m.id IN :memberIds GROUP BY c.id HAVING COUNT(DISTINCT m) = :memberCount")
    List<ChatRoom> findChatRoomByMemberIds(@Param("memberIds") List<Long> memberIds, @Param("memberCount") Long memberCount);

}
