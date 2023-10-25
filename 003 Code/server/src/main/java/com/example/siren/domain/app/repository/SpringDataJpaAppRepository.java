package com.example.siren.domain.app.repository;

import com.example.siren.domain.app.Application;
import com.example.siren.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataJpaAppRepository extends JpaRepository<Application,Long> {
    List<Application> findByMemberIdLike(Long id);
}
