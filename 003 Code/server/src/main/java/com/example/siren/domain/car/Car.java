package com.example.siren.domain.car;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "CAR")
public class Car {
    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "CAR_NUM")
    private String num;
    @Column(name = "CAR_MAKER")
    private String maker;
    @Column(name = "CAR_MODEL")
    private String model;
    @Column(name = "CAR_TYPE")
    private String type;
    @Column(name = "IMAGE")
    private Long image;

    public Car() {
    }

    public Car(String num) {
        this.num = num;
    }

    public Car(Long id, String num, String maker, String model, String type,Long image) {
        this.id = id;
        this.num = num;
        this.maker = maker;
        this.model = model;
        this.type = type;
        this.image = image;
    }
}
