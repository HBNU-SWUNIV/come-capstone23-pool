package com.example.capston_pj.retrofitInterface;

import com.example.capston_pj.models.GetContent;
import com.example.capston_pj.models.GetSchedule;
import com.example.capston_pj.models.MemberId;
import com.example.capston_pj.models.crew.GetCrew;
import com.example.capston_pj.models.crew.SaveCrew;
import com.example.capston_pj.models.post.GetPosting2;
import com.example.capston_pj.models.post.PostId;
import com.example.capston_pj.models.post.Posting;
import com.example.capston_pj.models.post.SaveInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitPostContent {
    @GET("/post/content")
    Call<GetContent> getData(@Query("id")Long id);

    @POST("/post/writerId")
    Call<Posting> PostFromWriter(@Body Long id);
    @POST("map/post>>member")
    Call<GetCrew> getCrew(@Body PostId id);

    @POST("map/member>>post")
    Call<GetPosting2> getPostForCrew(@Body MemberId id);

    @POST("map/getSchedule")
    Call<GetPosting2> getSchedule(@Body MemberId id);

    @POST("map/save")
    Call<SaveCrew> saveCrew(@Body SaveCrew crew);
    @POST("post/info")
    Call<SaveInfo> saveInfo(@Body SaveInfo crew);
    @POST("post/driver")
    Call<SaveInfo> saveDriver(@Body SaveInfo driver);

    @POST("map/m_schedule")
    Call<GetSchedule> getSchedule(@Body GetSchedule id);

    @POST("map/update")
    Call<SaveCrew> update(@Body SaveCrew crew);
}
