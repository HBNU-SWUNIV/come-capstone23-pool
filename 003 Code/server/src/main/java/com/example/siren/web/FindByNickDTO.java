package com.example.siren.web;

import lombok.Data;

@Data
public class FindByNickDTO {
    private String loginId;
    private String name;
    private String gender;

    public FindByNickDTO() {
    }

    public FindByNickDTO(String name, String gender) {
        this.name = name;
        this.gender = gender;
    }

    public FindByNickDTO(String loginId, String name, String gender) {
        this.loginId = loginId;
        this.name = name;
        this.gender = gender;
    }
}
