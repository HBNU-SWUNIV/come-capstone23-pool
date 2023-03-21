package com.example.siren.domain.post;

import jakarta.persistence.*;
import lombok.Data;


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

    public Post() {
    }

    public Post(Long writerId, String name, String loginId, String start, String end, String content) {
        this.writerId = writerId;
        this.name = name;
        this.loginId = loginId;
        this.start = start;
        this.end = end;
        this.content = content;
    }

}
