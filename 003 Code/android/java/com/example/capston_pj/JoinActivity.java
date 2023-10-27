package com.example.capston_pj;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capston_pj.models.IdCheckResult;
import com.example.capston_pj.models.Member;
import com.example.capston_pj.retrofitInterface.RetrofitJoinInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JoinActivity extends AppCompatActivity {
    EditText etId,etPw,etName;
    TextView check,message;
    Button registerBtn;
    Spinner gender;
    Spinner home;
    String genderResult, homeResult;
    boolean success = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        Intent intent = getIntent();


        etId = (EditText) findViewById(R.id.et_id);
        etPw = (EditText) findViewById(R.id.et_pass);
        etName = (EditText) findViewById(R.id.et_name);
        registerBtn = (Button)findViewById(R.id.btn_register);
        check = (TextView)findViewById(R.id.et_check);
        message = (TextView)findViewById(R.id.et_message);
        gender = (Spinner)findViewById(R.id.join_gender);
        home = (Spinner)findViewById(R.id.join_home);
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                genderResult = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        home.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                homeResult = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("success","status: "+success );
                if(success==false){
                    message.setVisibility(View.VISIBLE);
                    message.setText("아이디 중복체크 해주세요");
                }else {
                    String id = etId.getText().toString();
                    String pw = etPw.getText().toString();
                    String name = etName.getText().toString();
                    String gender = "m";
                    if(genderResult.equals("남")){
                        gender = "m";
                    } else if (genderResult.equals("여")) {
                        gender="w";
                    }
                    createPost(id,pw,name,gender,homeResult);

                }

            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = etId.getText().toString();
                if(id.isEmpty()){
                    message.setVisibility(View.VISIBLE);
                    message.setText("아이디를 입력해주세요");
                    message.setTextColor( Color.parseColor("#FFFF0000") );
                }else{
                    idCheck(id);
                }

            }
        });

    }
    private void createPost(String id, String pw,String name,String gender,String home){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitJoinInterface service = retrofit.create(RetrofitJoinInterface.class);
        String dow = "0,0,0,0,0/0,0,0,0,0";
        Member member = new Member(id, pw, name,0L,gender,0L,0,0,0,home,dow);
        Call<Member> call = service.createPost(member);
        call.enqueue(new Callback<Member>() {
            @Override
            public void onResponse(Call<Member> call, Response<Member> response) {
                if (!response.isSuccessful()) {
                    Log.d("fail","fail" );
                    return;
                }

                Member postResponse = response.body();
                Log.d("getResult","result:"+postResponse.getId() );
                String content = "";
                      /*  content += "Code : " + response.code() + "\n";
                        content += "Id: " + postResponse.getId() + "\n";
                        content += "User Id: " + postResponse.getLoginId() + "\n";
                        content += "password: " + postResponse.getPassword() + "\n";*//*
                content += "name: " + postResponse.getName() + "\n";
                    */
                member.setUserId(postResponse.getId());
                member.setLoginId(postResponse.getLoginId());
                member.setPassword(postResponse.getPassword());
                member.setName(postResponse.getName());
                JoinActivity.this.finish();
            }

            @Override
            public void onFailure(Call<Member> call, Throwable t) {

            }
        });
    }
    private void idCheck(String id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitJoinInterface service = retrofit.create(RetrofitJoinInterface.class);
        Call<IdCheckResult> call = service.result(id);
        call.enqueue(new Callback<IdCheckResult>() {
            @Override
            public void onResponse(Call<IdCheckResult> call, Response<IdCheckResult> response) {
                if (response.isSuccessful()) {
                    IdCheckResult data = response.body();
                    String result = data.getResult();
                    Log.d("getResult","result:"+result );
                    if(result.equals("y")){
                        success = true;
                        message.setVisibility(View.VISIBLE);
                        message.setText("사용 가능한 아이디입니다.");
                        message.setTextColor( Color.parseColor("#000000") );
                        registerBtn.setBackgroundResource(R.drawable.round_corner_blue);
                    }
                    if(result.equals("n")){
                        message.setVisibility(View.VISIBLE);
                        message.setText("이미 사용 중인 아이디입니다.");
                        message.setTextColor( Color.parseColor("#FFFF0000") );
                    }

                }
            }
            @Override
            public void onFailure(Call<IdCheckResult> call, Throwable t) {
                Log.d("errorResult","이유:"+t );
            }
        });
    }
}
