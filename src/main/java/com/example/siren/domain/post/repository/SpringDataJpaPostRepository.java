package com.example.siren.domain.post.repository;


import com.example.siren.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataJpaPostRepository extends JpaRepository<Post,Long> {
   // List<Post> findByItemNameLike(String name); //게시물 이름으로 검색

}
