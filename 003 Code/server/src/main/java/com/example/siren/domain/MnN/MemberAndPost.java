package com.example.siren.domain.MnN;

import com.example.siren.domain.member.Member;
import com.example.siren.domain.post.Post;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;

import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Data
@Entity
@Table(name = "M_P")
public class MemberAndPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id")
    @OnDelete(action = CASCADE)
    private Member postMember;

    @ManyToOne
    @JoinColumn(name="post_id")
    @OnDelete(action = CASCADE)
    private Post post;

    private String dow;
    private String times;

    private boolean review;

    public MemberAndPost() {
    }

    public MemberAndPost(Member postMember, Post post,String dow, String times, boolean review) {
        this.postMember = postMember;
        this.post = post;
        this.dow = dow;
        this.times = times;
        this.review = review;
    }
}
