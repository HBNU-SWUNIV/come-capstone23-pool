package com.example.siren.domain.car;

import com.example.siren.domain.member.Member;

import java.util.Optional;

public interface CarService {
    Car save(Car car);
    void update(Car car);
    Optional<Car> findById(Long id);
}
