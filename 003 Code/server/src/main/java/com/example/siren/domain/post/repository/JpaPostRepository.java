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
    public void update(PostUpdateDto updateParam) {
        Long postId = updateParam.getId();
        Post findPost = repository.findById(postId).orElseThrow();
        findPost.setPeople(updateParam.getPeople());
    }
    @Override
    public void updateInfo(PostUpdateDto updateParam) {
        Long postId = updateParam.getId();
        Post findPost = repository.findById(postId).orElseThrow();
        findPost.setInfo(updateParam.getInfo());
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
        String dow = cond.getDow();
        String gender = cond.getGender();
        String smoke = cond.getSmoke();
        String pet = cond.getPet();
        String child = cond.getChild();
        String baggage = cond.getBaggage();



        if(StringUtils.hasText(start) && StringUtils.hasText(end) && StringUtils.hasText(time) && StringUtils.hasText(dow) && StringUtils.hasText(gender) && StringUtils.hasText(smoke) && StringUtils.hasText(pet) && StringUtils.hasText(child) && StringUtils.hasText(baggage) ){
            return repository.findPosts("%"+start+"%","%"+end+"%","%"+time+"%","%"+dow+"%","%"+gender+"%","%"+smoke+"%","%"+pet+"%","%"+child+"%","%"+baggage+"%");
        }else if(StringUtils.hasText(start)){
            return repository.findByStartLike("%"+start+"%");
        }else if(StringUtils.hasText(end)){
            return repository.findByEndLike("%"+end+"%");
        }else if(StringUtils.hasText(time)){
            return repository.findByTimesLike("%"+time+"%");
        }
        else if(StringUtils.hasText(dow)){
            return repository.findByDowLike("%"+dow+"%");
        }
        else if(StringUtils.hasText(gender)){
            return repository.findByGenderLike("%"+gender+"%");
        }
        else if(StringUtils.hasText(smoke)){
            return repository.findBySmokeLike("%"+smoke+"%");
        }
        else if(StringUtils.hasText(pet)){
            return repository.findByPetLike("%"+pet+"%");
        }
        else if(StringUtils.hasText(child)){
            return repository.findByChildLike("%"+child+"%");
        }
        else if(StringUtils.hasText(baggage)){
            return repository.findByBaggageLike("%"+baggage+"%");
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
