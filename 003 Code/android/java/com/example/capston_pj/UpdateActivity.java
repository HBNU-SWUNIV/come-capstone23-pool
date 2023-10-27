package com.example.capston_pj;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capston_pj.models.Member;
import com.example.capston_pj.models.UpdateParam;
import com.example.capston_pj.retrofitInterface.RetrofitUpdateInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UpdateActivity extends AppCompatActivity{
    EditText updatePw,updateName;
    TextView wd;
    Button update;
    private long id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent intent = getIntent();
        Member member = (Member)intent.getSerializableExtra("member");
        id=member.getId();

        updatePw = findViewById(R.id.update_pw);
        updateName = findViewById(R.id.update_name);
        update = findViewById(R.id.update_btn);
        wd = findViewById(R.id.update_withdraw);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String paramPw,paramName;

                if(updatePw.getText().length()==0){
                    paramPw = member.getPassword();
                }else {
                    paramPw = updatePw.getText().toString();
                }
                if(updateName.getText().length()==0){
                    paramName = member.getName();
                }else{
                    paramName = updateName.getText().toString();
                }
                Log.d("update","pw:"+paramPw+"name:"+paramName);
                createPost(id,paramPw,paramName);
            }
        });

        wd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setIntent = new Intent(getApplicationContext(),WithdrawActivity.class);
                setIntent.putExtra("member",member);
                startActivity(setIntent);
            }
        });

    }
    private void createPost(Long id, String pw,String name){

       Gson gson = new GsonBuilder()
               .setLenient()
               .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        Log.d("retrofitUpdate","id:"+id+"pw:"+pw+"name:"+name);
        RetrofitUpdateInterface service = retrofit.create(RetrofitUpdateInterface.class);
        UpdateParam updateParam = new UpdateParam(id,pw,name);
        Call<UpdateParam> call = service.update(updateParam);
        call.enqueue(new Callback<UpdateParam>() {
            @Override
            public void onResponse(Call<UpdateParam> call, Response<UpdateParam> response) {
                if (!response.isSuccessful()) {
                    Log.d("error","code:"+response.code());
                    return;
                }
                Toast toast = Toast.makeText(getApplicationContext(), "회원정보가 수정되었습니다.", Toast.LENGTH_SHORT);
                toast.show();
                UpdateActivity.this.finish();

            }

            @Override
            public void onFailure(Call<UpdateParam> call, Throwable t) {
                Log.d("fail","error:"+t);
            }
        });
    }

}
