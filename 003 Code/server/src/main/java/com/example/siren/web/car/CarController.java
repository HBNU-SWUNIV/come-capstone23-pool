package com.example.siren.web.car;

import com.example.siren.domain.car.Car;
import com.example.siren.domain.car.CarService;
import com.example.siren.domain.file.Board;
import com.example.siren.domain.member.Member;
import com.example.siren.domain.member.MemberUpdateDto;
import com.example.siren.domain.member.repository.MemberRepository;
import com.example.siren.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/car")
public class CarController {
    private final CarService service;
    private final MemberService member;

    @ResponseBody
    @PostMapping("/save")
    public Car carSave(@RequestBody Car car){
        Car save = service.save(car);
        MemberUpdateDto param = new MemberUpdateDto(car.getId());
        member.updateCar(param);
        return save;
    }

    @ResponseBody
    @PostMapping("/get")
    public Car carSearch(@RequestBody MemberUpdateDto member){
        Long id = member.getId();
        Optional<Member> getMember = this.member.findById(id);
        if(getMember.get().getCar()==0){
            Car error = new Car("x");
            return error;
        }else {
            Optional<Car> car = service.findById(id);
            return car.get();
        }
    }
}
