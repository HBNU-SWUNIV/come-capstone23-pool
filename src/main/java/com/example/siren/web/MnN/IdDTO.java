package com.example.siren.web.MnN;

import lombok.Data;

@Data
public class IdDTO {
    private Long memberId;
    private String roomId;

    public IdDTO() {
    }

    public IdDTO(Long memberId, String roomId) {
        this.memberId = memberId;
        this.roomId = roomId;
    }
}
