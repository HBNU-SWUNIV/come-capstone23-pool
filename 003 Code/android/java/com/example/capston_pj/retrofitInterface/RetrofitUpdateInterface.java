package com.example.capston_pj.retrofitInterface;

import com.example.capston_pj.models.UpdateParam;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitUpdateInterface {

    @POST("members/update")
    Call<UpdateParam>update(@Body UpdateParam updateParam);
    @POST("members/delete")
    Call<UpdateParam>delete(@Body UpdateParam updateParam);
    @POST("members/plusMoney")
    Call<UpdateParam>plus(@Body UpdateParam updateParam);
    @POST("members/minusMoney")
    Call<UpdateParam>minus(@Body UpdateParam updateParam);
    @POST("members/sendMoney")
    Call<UpdateParam>send(@Body UpdateParam updateParam);

    @POST("members/token")
    Call<UpdateParam>token(@Body UpdateParam updateParam);
    @POST("members/minus")
    Call<UpdateParam>minusScore(@Body UpdateParam updateParam);
}
