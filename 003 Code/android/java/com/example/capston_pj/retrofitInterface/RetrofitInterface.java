package com.example.capston_pj.retrofitInterface;

import com.example.capston_pj.models.FindLoginId;
import com.example.capston_pj.models.Member;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @POST("login-json")
    Call<Member>createPost(@Body Member member);

    @POST("/members/findLoginId")
    Call<FindLoginId>findId(@Body FindLoginId input);

    @POST("/members/findPw")
    Call<FindLoginId>findPw(@Body FindLoginId input);

    @POST("/members/findOther")
    Call<Member>findOther(@Body Member input);

}
