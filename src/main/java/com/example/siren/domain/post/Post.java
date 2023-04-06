package com.example.siren.domain.post;

import com.example.siren.domain.MnN.MemberAndPost;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@Entity
@Table(name = "POST")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "writer_id",length = 10)
    private Long writerId;
    private String name;
    @Column(name = "login_id", length = 10)
    private String loginId;
    private String start;
    @Column(name = "endp")
    private String end;
    private String content;
    private int people;
    private String times;
    private String dow;
    private int price;

    @OneToMany(mappedBy = "post")
    private Set<MemberAndPost> postEnrollments = new HashSet<>();

    public Post() {
    }

 /*   public Post(Long writerId, String name, String loginId, String start, String end, String content) {
        this.writerId = writerId;
        this.name = name;
        this.loginId = loginId;
        this.start = start;
        this.end = end;
        this.content = content;
    }*/
    public Post(Long writerId, String name, String loginId, String start, String end, String content, int people, String times, String dow, int price) {
        this.writerId = writerId;
        this.name = name;
        this.loginId = loginId;
        this.start = start;
        this.end = end;
        this.content = content;
        this.people = people;
        this.times = times;
        this.dow = dow;
        this.price = price;
    }

}
