package com.example.siren.domain.car;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CarServiceImpl implements CarService{

    private final SpringDataJpaCarRepository repository;

    @Override
    public Car save(Car car) {
        return repository.save(car);
    }

    @Override
    public void update(Car car) {
        Long carId = car.getId();
        Optional<Car> getCar = repository.findById(carId);
        getCar.get().setId(car.getId());
        getCar.get().setNum(car.getNum());
        getCar.get().setMaker(car.getMaker());
        getCar.get().setModel(car.getModel());
        getCar.get().setType(car.getType());
        getCar.get().setImage(car.getImage());

    }

    @Override
    public Optional<Car> findById(Long id) {
        return repository.findById(id);
    }
}
