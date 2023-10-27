package com.example.capston_pj;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.capston_pj.models.Car;
import com.example.capston_pj.models.GetSchedule;
import com.example.capston_pj.models.Member;
import com.example.capston_pj.retrofitInterface.RetrofitMacInterface;
import com.example.capston_pj.retrofitInterface.RetrofitPostContent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OtherUserInfoActivity extends AppCompatActivity {
    private TextView  noCar,userName,mon1,mon2,tues1,tues2,wen1,wen2,thurs1,thurs2,fri1,fri2,carNum,carMaker,carModel,cartype,genderTx;
    private LinearLayout getCar;
    private FrameLayout frame;

    private ImageView ivImage,carImage;
    private Member member;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_info);
        Intent intent = getIntent();
        member = (Member)intent.getSerializableExtra("member");
        userName = (TextView) findViewById(R.id.o_info_user_name);
        ivImage = findViewById(R.id.o_profile);
        mon1 = findViewById(R.id.o_info_mon1);
        mon2 = findViewById(R.id.o_info_mon2);
        tues1 = findViewById(R.id.o_info_tues1);
        tues2 = findViewById(R.id.o_info_tues2);
        wen1 = findViewById(R.id.o_info_wen1);
        wen2 = findViewById(R.id.o_info_wen2);
        thurs1 = findViewById(R.id.o_info_thurs1);
        thurs2 = findViewById(R.id.o_info_thurs2);
        fri1 = findViewById(R.id.o_info_fri1);
        fri2 = findViewById(R.id.o_info_fri2);
        getCar = findViewById(R.id.o_info_getCar);
        carNum = findViewById(R.id.o_info_carnum);
        carMaker = findViewById(R.id.o_info_carMaker);
        carModel = findViewById(R.id.o_info_carModel);
        cartype = findViewById(R.id.o_info_car_type);
        carImage = findViewById(R.id.o_info_carImage);
        genderTx = findViewById(R.id.o_gender);
        noCar = findViewById(R.id.o_nocar);
        frame = findViewById(R.id.o_FirstFrame);



        carInfo(member.getId());
        String genderInfo = member.getGender().toString();
        if(genderInfo.equals("m")){
            genderTx.setText("남");
            frame.setBackgroundColor(Color.parseColor("#bbceff"));
        } else if (genderInfo.equals("w")) {
            genderTx.setText("여");
            frame.setBackgroundColor(Color.parseColor("#EED7FA"));
        }

        String imageUrl = "url"+member.getId();
        String CarImageUrl = "url"+member.getId();
        loadProfileImage(imageUrl,ivImage);
        loadCarImage(CarImageUrl,carImage);

        loadSchedule(member.getId());

        userName.setText(member.getName()+"님");

    }


    private void carInfo(Long id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitMacInterface service = retrofit.create(RetrofitMacInterface.class);
        Car memberId = new Car(id);
        Call<Car> call = service.getCarInfo(memberId);
        call.enqueue(new Callback<Car>() {
            @Override
            public void onResponse(Call<Car> call, Response<Car> response) {
                if (response.isSuccessful()) {
                    Car data = response.body();
                    Log.d("carData","data:"+data.getNum() );
                    if(data.getNum().equals("x")){
                        getCar.setVisibility(View.GONE);
                        noCar.setVisibility(View.VISIBLE);
                    }else {
                        getCar.setVisibility(View.VISIBLE);
                        noCar.setVisibility(View.GONE);

                        carNum.setText(data.getNum().toString());
                        carMaker.setText(data.getMaker().toString());
                        carModel.setText(data.getModel().toString());
                        cartype.setText(data.getType().toString());
                    }

                }
            }
            @Override
            public void onFailure(Call<Car> call, Throwable t) {
                Log.d("errorResult","이유:"+t );
            }
        });
    }


    private void loadSchedule(Long id){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        RetrofitPostContent service = retrofit.create(RetrofitPostContent.class);
        GetSchedule param = new GetSchedule(id);
        Call<GetSchedule> call = service.getSchedule(param);
        call.enqueue(new Callback<GetSchedule>() {
            @Override
            public void onResponse(Call<GetSchedule> call, Response<GetSchedule> response) {
                if (!response.isSuccessful()) {
                    Log.d("error","code:"+response.code());
                    return;
                }
                GetSchedule data = response.body();
                String[] result = data.getResult();
                String[] arr;
                if(!result[0].equals("x")){
                    arr = result[0].split(",");
                    if(arr.length==1){
                        if(arr[0].charAt(0)=='A'){
                            mon1.setText(arr[0].substring(2));
                        } else if (arr[0].charAt(0)=='P') {
                            mon2.setText(arr[0].substring(2));
                        }
                    } else if (arr.length==2) {
                        mon1.setText(arr[0].substring(2));
                        mon2.setText(arr[1].substring(2));
                    }
                }
                if(!result[1].equals("x")){
                    arr = result[1].split(",");
                    if(arr.length==1){
                        if(arr[0].charAt(0)=='A'){
                            tues1.setText(arr[0].substring(2));
                        } else if (arr[0].charAt(0)=='P') {
                            tues2.setText(arr[0].substring(2));
                        }
                    } else if (arr.length==2) {
                        tues1.setText(arr[0].substring(2));
                        tues2.setText(arr[1].substring(2));
                    }
                }
                if(!result[2].equals("x")){
                    arr = result[2].split(",");
                    if(arr.length==1){
                        if(arr[0].charAt(0)=='A'){
                            wen1.setText(arr[0].substring(2));
                        } else if (arr[0].charAt(0)=='P') {
                            wen2.setText(arr[0].substring(2));
                        }
                    } else if (arr.length==2) {
                        wen1.setText(arr[0].substring(2));
                        wen2.setText(arr[1].substring(2));
                    }
                }
                if(!result[3].equals("x")){
                    arr = result[3].split(",");
                    if(arr.length==1){
                        if(arr[0].charAt(0)=='A'){
                            thurs1.setText(arr[0].substring(2));
                        } else if (arr[0].charAt(0)=='P') {
                            thurs2.setText(arr[0].substring(2));
                        }
                    } else if (arr.length==2) {
                        thurs1.setText(arr[0].substring(2));
                        thurs2.setText(arr[1].substring(2));
                    }
                }
                if(!result[4].equals("x")){
                    arr = result[4].split(",");
                    if(arr.length==1){
                        if(arr[0].charAt(0)=='A'){
                            fri1.setText(arr[0].substring(2));
                        } else if (arr[0].charAt(0)=='P') {
                            fri2.setText(arr[0].substring(2));
                        }
                    } else if (arr.length==2) {
                        fri1.setText(arr[0].substring(2));
                        fri2.setText(arr[1].substring(2));
                    }
                }
            }

            @Override
            public void onFailure(Call<GetSchedule> call, Throwable t) {
                Log.d("fail","error:"+t);
            }
        });
    }

    public void loadProfileImage(String imageUrl, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.basic) // 로딩 중에 표시할 이미지
                .error(R.drawable.basic) // 로딩 실패 시 표시할 이미지
                .diskCacheStrategy(DiskCacheStrategy.NONE); // 디스크 캐시 전략 설정

        Glide.with(this)
                .load(imageUrl)
                .apply(requestOptions)
                .into(imageView);
    }
    public void loadCarImage(String imageUrl, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.basic) // 로딩 중에 표시할 이미지
                .error(R.drawable.basic) // 로딩 실패 시 표시할 이미지
                .diskCacheStrategy(DiskCacheStrategy.NONE); // 디스크 캐시 전략 설정

        Glide.with(this)
                .load(imageUrl)
                .apply(requestOptions)
                .into(imageView);
    }

}
