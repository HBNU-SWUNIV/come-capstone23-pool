package com.example.capston_pj.retrofitInterface;

import com.example.capston_pj.models.post.App;
import com.example.capston_pj.models.post.GetApp;
import com.example.capston_pj.models.post.GetPosting;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitPostListInterface {

    @GET("/post")
    Call<GetPosting>getAll(@Query("start")String start,
                           @Query("end")String end,
                           @Query("times")String times,
                           @Query("dow")String dow,
                           @Query("gender")String gender,
                           @Query("smoke")String smoke,
                           @Query("pet")String pet,
                           @Query("child")String child,
                           @Query("baggage")String baggage);


    @GET("/post/appList")
    Call<GetApp> result(@Query("postId")Long postId);

    @POST("/post/appDelete")
    Call<App> appDel(@Body App app);

    @POST("/post/app")
    Call<App> app(@Body App app);
}
