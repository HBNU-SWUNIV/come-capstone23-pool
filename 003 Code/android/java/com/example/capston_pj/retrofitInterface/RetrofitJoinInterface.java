package com.example.capston_pj.retrofitInterface;

import com.example.capston_pj.models.IdCheckResult;
import com.example.capston_pj.models.Member;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitJoinInterface {
    @POST("members/join-json")
    Call<Member> createPost(@Body Member member);

    @GET("/members/check")
    Call<IdCheckResult> result(@Query("memberId")String id);
}
