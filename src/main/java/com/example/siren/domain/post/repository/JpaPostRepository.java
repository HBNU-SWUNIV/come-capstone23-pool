package com.example.siren.domain.post.repository;

import com.example.siren.domain.member.Member;
import com.example.siren.domain.post.Post;
import com.example.siren.domain.post.PostSearchCond;
import com.example.siren.domain.post.PostUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Transactional
@Repository
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

    public Optional<Post>findByWriterId(Long id){
        return repository.findByWriterIdLike(id);
    }

    @Override
    public List<Post> findAll(PostSearchCond cond) {
        String start = cond.getStart();
        String end = cond.getEnd();
        String time = cond.getTimes();

        if(StringUtils.hasText(start) && StringUtils.hasText(end)){
            return repository.findPosts("%"+start+"%","%"+end+"%","%"+time+"%");
        }else if(StringUtils.hasText(start)){
            return repository.findByStartLike("%"+start+"%");
        }else if(StringUtils.hasText(end)){
            return repository.findByEndLike("%"+end+"%");
        }else if(StringUtils.hasText(time)){
            return repository.findByTimesLike("%"+time+"%");
        }else {
            return repository.findAll();
        }
    }

 /*   public List<Post> findWithoutContent(){
        long count = repository.count();
        for(long i=0;i<count;i++){

        }

    }*/
}
