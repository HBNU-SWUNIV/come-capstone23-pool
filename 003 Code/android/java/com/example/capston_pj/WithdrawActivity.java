package com.example.capston_pj;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

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

public class WithdrawActivity extends AppCompatActivity {
    CheckBox check;
    EditText edit;
    Button ok,cancel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        check = findViewById(R.id.withdraw_check);
        edit = findViewById(R.id.witdraw_pw);
        ok = findViewById(R.id.witdraw_ok);
        cancel = findViewById(R.id.witdraw_cancel);

        Intent intent = getIntent();
        Member member = (Member)intent.getSerializableExtra("member");

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check.isChecked()){
                    if(edit.getText().toString().equals(member.getPassword())){
                        withdraw(member.getId());
                    }
                }
            }


        });

    }
    private void withdraw(long id) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        Log.d("retrofitDelete","id:"+id);
        RetrofitUpdateInterface service = retrofit.create(RetrofitUpdateInterface.class);
        UpdateParam updateParam = new UpdateParam(id);
        Call<UpdateParam> call = service.delete(updateParam);
        call.enqueue(new Callback<UpdateParam>() {
            @Override
            public void onResponse(Call<UpdateParam> call, Response<UpdateParam> response) {
                if (!response.isSuccessful()) {
                    Log.d("error","code:"+response.code());
                    return;
                }
                Intent finish = new Intent(WithdrawActivity.this,Finish.class);
                startActivity(finish);

            }

            @Override
            public void onFailure(Call<UpdateParam> call, Throwable t) {
                Log.d("fail","error:"+t);
            }
        });
    }
}
