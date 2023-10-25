package com.example.siren.domain.post.repository;

import com.example.siren.domain.app.Application;
import com.example.siren.domain.member.Member;
import com.example.siren.domain.post.Post;
import com.example.siren.domain.post.PostSearchCond;
import com.example.siren.domain.post.PostUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Transactional
@Repository
@RequiredArgsConstructor
@Slf4j
public class JpaPostRepository implements PostRepository{

    private final SpringDataJpaPostRepository repository;

    @Override
    public Post save(Post post) {
        log.info("savePost = {}",post.getPeople());
        return repository.save(post);
    }

    @Override
    public void update(PostUpdateDto updateParam) {
        Long postId = updateParam.getId();
        Post findPost = repository.findById(postId).orElseThrow();
        findPost.setPeople(updateParam.getInfo());
    }
    @Override
    public void updateInfo(PostUpdateDto updateParam) {
        Long postId = updateParam.getId();
        Post findPost = repository.findById(postId).orElseThrow();
        findPost.setInfo(updateParam.getInfo());
    }
    @Override
    public void updateDriver(Long id,Long driver) {
        Post findPost = repository.findById(id).orElseThrow();
        String original = findPost.getDriver();
        findPost.setDriver(original+","+String.valueOf(driver));
    }

    @Override
    public void updateReview(Long id, float updateReview,int updateRCount) {
        Post findPost = repository.findById(id).orElseThrow();
        findPost.setReview(updateReview);
        findPost.setRCount(updateRCount);
    }

    @Override
    public void application(Application param) {
        Post findPost = repository.findById(param.getPostId()).orElseThrow();
        String app = findPost.getApp();
        String memberId = String.valueOf(param.getMemberId());
        if(app.equals("x")){
            findPost.setApp(memberId);
        }else {
            findPost.setApp(app+","+memberId);
        }
    }

    @Override
    public void deleteApp(PostUpdateDto updateParam) {
        Post findPost = repository.findById(updateParam.getId()).orElseThrow();
        String app = findPost.getApp();
        String memberId = String.valueOf(updateParam.getMemberId());
        String newStr = ".";
        if(app.equals("x")){
            log.info("delete Nothing");
        }else {
            String arr[] = app.split(",");
            for(String i : arr){
                if(i.equals(memberId)){
                    log.info("delete");
                }else{
                    newStr += ","+i;
                    log.info("newStr={}",newStr);
                }
            }
            if(newStr.length()<3){
                newStr = "x";
            }else {
                newStr = newStr.substring(2);
            }
            findPost.setApp(newStr);
        }
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
    public List<Post> findAll2() {
        return repository.findAll();
    }
 /*   public List<Post> findWithoutContent(){
        long count = repository.count();
        for(long i=0;i<count;i++){

        }

    }*/
}
