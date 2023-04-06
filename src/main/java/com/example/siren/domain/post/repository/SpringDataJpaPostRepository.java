package com.example.siren.domain.post.repository;


import com.example.siren.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SpringDataJpaPostRepository extends JpaRepository<Post,Long> {
    Optional<Post> findByWriterIdLike(Long id);
    List<Post> findByStartLike(String start); //게시물 이름으로 검색
    List<Post> findByEndLike(String end);
    List<Post> findByTimesLike(String times);

    @Query("select p from Post p where p.start like :start and p.end like :end and p.times like :times")
    List<Post> findPosts(@Param("start")String start,@Param("end")String end,@Param("times")String times);

}
