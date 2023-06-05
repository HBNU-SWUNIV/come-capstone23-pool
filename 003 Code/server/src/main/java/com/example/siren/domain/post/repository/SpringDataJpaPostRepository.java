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
    List<Post> findByDowLike(String dow);
    List<Post> findByPeopleLike(int people);
    List<Post> findByPriceLike(int price);
    List<Post> findByGenderLike(String gender);
    List<Post> findBySmokeLike(String smoke);
    List<Post> findByPetLike(String pet);
    List<Post> findByChildLike(String child);
    List<Post> findByBaggageLike(String baggage);



    @Query("select p from Post p where p.start like :start and p.end like :end and p.times like :times and p.dow like :dow and p.gender like :gender and p.smoke like :smoke and p.pet like :pet and p.child like :child and p.baggage like :baggage")
    List<Post> findPosts(@Param("start")String start,@Param("end")String end,@Param("times")String times ,@Param("dow")String dow,@Param("gender")String gender,@Param("smoke")String smoke,@Param("pet")String pet,@Param("child")String child,@Param("baggage")String baggage);

}
