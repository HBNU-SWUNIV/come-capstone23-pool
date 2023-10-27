package com.example.capston_pj.retrofitInterface;

import com.example.capston_pj.models.post.PostReviewDTO;
import com.example.capston_pj.models.post.Posting;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface RetrofitPostInterface {
    @POST("/post/post-json")
    Call<Posting>createPost(@Body Posting post);
    @POST("/post/review")
    Call<PostReviewDTO>addReview(@Body PostReviewDTO post);
}
