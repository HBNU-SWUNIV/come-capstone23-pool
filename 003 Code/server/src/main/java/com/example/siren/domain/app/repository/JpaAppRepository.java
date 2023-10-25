package com.example.siren.domain.app.repository;

import com.example.siren.domain.app.Application;
import com.example.siren.domain.post.PostUpdateDto;
import com.example.siren.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
@RequiredArgsConstructor
@Slf4j
public class JpaAppRepository implements AppRepository {
    private final SpringDataJpaAppRepository repository;
    private final PostService postService;
    @Override
    public Application save(Application app){
        List<Application> apps = repository.findByMemberIdLike(app.getMemberId());
        boolean state = true;
        for(Application i : apps) {
            if (i.getPostId().equals(app.getPostId())) {
               state = false;
            }
        }
        if(state == true){
            postService.application(app);
            return repository.save(app);
        }else {
            return null;
        }

    }
    @Override
    public Application findByMemberId(PostUpdateDto param){
       List<Application> apps = repository.findByMemberIdLike(param.getMemberId());
       for(Application i : apps) {
           if (i.getPostId().equals(param.getId())) {
               return i;
           }
       }
       return apps.get(0);
    }

    @Override
    public void delete(PostUpdateDto param){
        List<Application> apps = repository.findByMemberIdLike(param.getMemberId());
        for(Application i : apps) {
            if (i.getPostId().equals(param.getId())) {
                repository.delete(i);
            }
        }

    }
}
