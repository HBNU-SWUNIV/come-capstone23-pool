package com.example.siren.domain.member;

import com.example.siren.domain.MnN.MemberAndChat;
import com.example.siren.domain.MnN.MemberAndPost;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "MEMBER")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //(jpa)PK(primary key)이면서->@ID / db 자동생성 -> @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Column(name = "login_id", length = 10)
    private String loginId;

    @NotEmpty
    private String password;

    @NotEmpty
    private String name;

    @OneToMany(mappedBy = "member",cascade = CascadeType.REMOVE)
    private Set<MemberAndChat> enrollments = new HashSet<>();

    @OneToMany(mappedBy = "postMember",cascade = CascadeType.REMOVE)
    private Set<MemberAndPost> postEnrollments = new HashSet<>();



    public Member() {// **JPA 에서는 빈 생성자가 필수 요소이다**
    }

    public Member(String loginId, String password,String name) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
    }
}
