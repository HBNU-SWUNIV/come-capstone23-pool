package com.example.siren.domain.member;

import lombok.Data;

@Data
public class MemberUpdateDto {
    private Long id;
    private String Password;

    private String Name;

    private Long profile;

    private int money;

    private String sender;

    private String token;


    public MemberUpdateDto() {
    }

    public MemberUpdateDto(Long id, int money) {
        this.id = id;
        this.money = money;
    }

    public MemberUpdateDto(Long id, int money, String sender) {
        this.id = id;
        this.money = money;
        this.sender = sender;
    }

    public MemberUpdateDto(Long id) {//멤버 삭제시 사용
        this.id = id;
    }
    public MemberUpdateDto(Long id, Long profile) { //프로필 변경시 사용
        this.id = id;
        this.profile = profile;
    }

    public MemberUpdateDto(Long id, String password, String name) {
        this.id = id;
        Password = password;
        Name = name;
    }

    public MemberUpdateDto(Long id, String token) {
        this.id = id;
        this.token = token;
    }
}
