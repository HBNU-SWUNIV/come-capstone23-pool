package com.example.siren.domain.MnN.repository.map;

import com.example.siren.domain.MnN.MemberAndPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataJpaMapRepository extends JpaRepository<MemberAndPost,Long> {
    List<MemberAndPost> findByPostMemberId(Long memberId);

    List<MemberAndPost> findByPostIdLike(Long postId);

}
