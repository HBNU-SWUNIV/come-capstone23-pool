package com.example.capston_pj.retrofitInterface;

import com.example.capston_pj.models.Car;
import com.example.capston_pj.models.chattingRoom.MacDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitMacInterface {
    @POST("mac/save")
    Call<MacDto> save(@Body MacDto macDto);
    @POST("car/get")
    Call<Car> getCarInfo(@Body Car car);

    @POST("car/save")
    Call<Car> saveCarInfo(@Body Car car);

}
