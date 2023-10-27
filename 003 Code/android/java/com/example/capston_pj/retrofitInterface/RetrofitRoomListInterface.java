package com.example.capston_pj.retrofitInterface;

import com.example.capston_pj.models.MemberId;
import com.example.capston_pj.models.chattingRoom.GetRecord;
import com.example.capston_pj.models.chattingRoom.GetRoom;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitRoomListInterface {
    @POST("/mac/member>>chatRoom")
    Call<GetRoom>getAll(@Body MemberId id);
    @POST("/chat/record")
    Call<GetRecord>getRoom(@Body MemberId roomId);
}
