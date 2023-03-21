package com.example.siren.domain.member;

import lombok.Data;

@Data
public class MemberUpdateDto {
    private Long id;
    private String Password;

    private String Name;


    public MemberUpdateDto() {
    }

    public MemberUpdateDto(Long id, String password, String name) {
        this.id = id;
        Password = password;
        Name = name;
    }
}
