package com.example.siren.web.MnN;

import lombok.Data;

@Data
public class IdDTO2 {
    private Long postId,memberId;
    private String loginId;
    private String dow;
    private String times;


    public IdDTO2() {
    }

    public IdDTO2(String loginId, Long postId,String dow,String times) {
        this.loginId = loginId;
        this.postId = postId;
        this.dow = dow;
        this.times = times;
    }
}
