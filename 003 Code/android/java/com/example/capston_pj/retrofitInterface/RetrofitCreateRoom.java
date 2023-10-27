package com.example.capston_pj.retrofitInterface;

import com.example.capston_pj.models.ChattingRoomCode;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitCreateRoom {
    @POST("/chat")
    Call<ChattingRoomCode> getCode(@Body ChattingRoomCode name);
}
