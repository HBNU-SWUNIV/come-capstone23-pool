package com.example.siren.config;

import com.example.siren.domain.member.repository.JpaMemberRepository;
import com.example.siren.domain.member.repository.MemberRepository;
import com.example.siren.domain.member.repository.SpringDataJpaMemberRepository;
import com.example.siren.domain.member.service.JpaMemberService;
import com.example.siren.domain.member.service.MemberService;
import com.example.siren.domain.post.repository.JpaPostRepository;
import com.example.siren.domain.post.repository.PostRepository;
import com.example.siren.domain.post.repository.SpringDataJpaPostRepository;
import com.example.siren.domain.post.service.JpaPostService;
import com.example.siren.domain.post.service.PostService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JpaConfig {
    private final SpringDataJpaMemberRepository springDataJpaMemberRepository;
    private final SpringDataJpaPostRepository springDataJpaPostRepository;

    @Bean
    public MemberService memberService() {
        return new JpaMemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new JpaMemberRepository(springDataJpaMemberRepository);
    }
    @Bean
    public PostService postService() {
        return new JpaPostService(postRepository());
    }

    @Bean
    public PostRepository postRepository() {
        return new JpaPostRepository(springDataJpaPostRepository);
    }
}
