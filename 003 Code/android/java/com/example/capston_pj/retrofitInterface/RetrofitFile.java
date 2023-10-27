package com.example.capston_pj.retrofitInterface;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitFile {
    @Multipart
    @POST("/file/upload")
    Call<String> request(@Part MultipartBody.Part files, @Part("id")long id);

    @Multipart
    @POST("/carFile/upload")
    Call<String> getCarImage(@Part MultipartBody.Part files, @Part("id")long id);
}
