package com.example.siren.domain.MnN;

import com.example.siren.domain.member.Member;
import com.example.siren.domain.post.Post;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "M_P")
public class MemberAndPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member postMember;

    @ManyToOne
    @JoinColumn(name="post_id")
    private Post post;

    private String dow;
    private String times;

    public MemberAndPost() {
    }

    public MemberAndPost(Member postMember, Post post,String dow, String times) {
        this.postMember = postMember;
        this.post = post;
        this.dow = dow;
        this.times = times;
    }
}