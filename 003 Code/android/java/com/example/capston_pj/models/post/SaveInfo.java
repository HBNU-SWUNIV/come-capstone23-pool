package com.example.capston_pj.models.post;

public class SaveInfo {
    private Long writerId;
    private String info;

    public SaveInfo(Long writerId, String info) {
        this.writerId = writerId;
        this.info = info;
    }

    public Long getWriterId() {
        return writerId;
    }

    public void setWriterId(Long writerId) {
        this.writerId = writerId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
