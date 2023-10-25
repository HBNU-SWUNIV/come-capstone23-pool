package com.example.siren.domain.app;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "APP")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dow;

    @Column(name = "MEMBER_ID")
    private Long memberId;

    @Column(name = "POST_ID")
    private Long postId;

    public Application() {
    }

    public Application(String dow, Long memberId,Long postId) {
        this.dow = dow;
        this.memberId = memberId;
        this.postId = postId;
    }
}
