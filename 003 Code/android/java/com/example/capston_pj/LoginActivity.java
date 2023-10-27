package com.example.capston_pj;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capston_pj.models.Member;
import com.example.capston_pj.retrofitInterface.RetrofitInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    String TAG = "Retrofit";
    EditText etId,etPw;
    Button loginBtn,registerBtn;
    TextView find;
    CheckBox auto;
    private String loginId,loginPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etId = (EditText) findViewById(R.id.et_id);
        etPw = (EditText) findViewById(R.id.et_pass);
        loginBtn = (Button) findViewById(R.id.btn_login);
        registerBtn = (Button) findViewById(R.id.btn_register);

        find = (TextView)findViewById(R.id.find_user_info);
        auto = (CheckBox)findViewById(R.id.auto_login);
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE);

        loginId = sharedPreferences.getString("inputId", null);
        loginPwd = sharedPreferences.getString("inputPwd", null);
        if(loginId != null && loginPwd != null) {
            createPost(loginId, loginPwd);
        }else if(loginId == null && loginPwd == null) {
            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = etId.getText().toString();
                    String pw = etPw.getText().toString();
                    createPost(id, pw);
                }
            });
            registerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LoginActivity.this,JoinActivity.class);
                    startActivity(intent);
                }
            });
          /*  mapBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Member member = new Member("test","test","test",1L,"m",0L,0);
                    member.setUserId(2);
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("member",member);
                    startActivity(intent);
                }
            });*/
            find.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LoginActivity.this, FindInfoActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
    private void createPost(String id, String pw){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitInterface service = retrofit.create(RetrofitInterface.class);
        Log.e(TAG,"버튼클릭");
        Member member = new Member(id, pw);
        Call<Member> call = service.createPost(member);
        call.enqueue(new Callback<Member>() {
            @Override
            public void onResponse(Call<Member> call, Response<Member> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                Member postResponse = response.body();

                String content = "";
                      /*  content += "Code : " + response.code() + "\n";
                        content += "Id: " + postResponse.getId() + "\n";
                        content += "User Id: " + postResponse.getLoginId() + "\n";
                        content += "password: " + postResponse.getPassword() + "\n";*/
                content += "name: " + postResponse.getName() + "\n";

                member.setUserId(postResponse.getId());
                member.setLoginId(postResponse.getLoginId());
                member.setPassword(postResponse.getPassword());
                member.setName(postResponse.getName());
                member.setMoney(postResponse.getMoney());
                member.setGender(postResponse.getGender());
                member.setCar(postResponse.getCar());
                member.setHome(postResponse.getHome());


                if(auto.isChecked()){
                    if(loginId == null && loginPwd == null) {
                        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor autoLogin = sharedPreferences.edit();
                        autoLogin.putString("inputId", id);
                        autoLogin.putString("inputPwd", pw);
                        autoLogin.commit();
                    }
                }

                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
               /* intent.putExtra("userName",member.getName());
                intent.putExtra("userId",member.getLoginId());
                intent.putExtra("userPassword",member.getId());*/
                intent.putExtra("member",member);

                startActivity(intent);
                finish();

            }

            @Override
            public void onFailure(Call<Member> call, Throwable t) {

            }
        });
    }


}
