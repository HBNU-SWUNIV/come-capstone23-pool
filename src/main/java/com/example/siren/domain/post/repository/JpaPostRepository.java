package com.example.siren.domain.post.repository;

import com.example.siren.domain.member.Member;
import com.example.siren.domain.post.Post;
import com.example.siren.domain.post.PostUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional
@RequiredArgsConstructor
public class JpaPostRepository implements PostRepository{

    private final SpringDataJpaPostRepository repository;

    @Override
    public Post save(Post post) {
        return repository.save(post);
    }

    @Override
    public void update(Long id, PostUpdateDto updateParam) {
        Post findPost = repository.findById(id).orElseThrow();
        findPost.setName(updateParam.getName());
        findPost.setContent(updateParam.getContent());
    }

    @Override
    public Optional<Post> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Post> findAll() {
        return repository.findAll();
    }

 /*   public List<Post> findWithoutContent(){
        long count = repository.count();
        for(long i=0;i<count;i++){

        }

    }*/
}
