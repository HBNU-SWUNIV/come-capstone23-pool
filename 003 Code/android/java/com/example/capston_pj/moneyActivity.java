package com.example.capston_pj;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capston_pj.models.Member;
import com.example.capston_pj.models.UpdateParam;
import com.example.capston_pj.retrofitInterface.RetrofitUpdateInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class moneyActivity extends AppCompatActivity {

    private Button plusTop,sendTop,minusTop,plusBot,sendBot,minusBot,finalBtn;
    private  EditText plusE,sendE,num,minusE,who;
    private TextView plusR,sendR,sendError,bank,minusR,minusError,ftx1,ftx2;
    private  LinearLayout plusL,sendL,minusL,finalL,topL;
    private Member member;
    private String myMoney;
    private int finalMoney =0;
    boolean sendFlag1 = false;
    boolean sendFlag2 = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);
        plusTop =findViewById(R.id.money_plus);
        sendTop =findViewById(R.id.money_send);
        plusE =findViewById(R.id.money_plus_edit);
        plusR =findViewById(R.id.result_plus);
        plusL = findViewById(R.id.plus_layout);
        plusBot =findViewById(R.id.plus_btn);
        sendE =findViewById(R.id.money_send_edit);
        sendR = findViewById(R.id.result_send);
        Intent intent = getIntent();
        member = (Member)intent.getSerializableExtra("member");
        myMoney = intent.getStringExtra("money");

        sendL =findViewById(R.id.send_layout);
        sendBot = findViewById(R.id.send_btn);
        sendError = findViewById(R.id.send_error);
        minusTop = findViewById(R.id.money_minus);
        bank =findViewById(R.id.minus_bank);
        num = findViewById(R.id.minus_edit_num);
        minusE = findViewById(R.id.minus_edit_money);
        minusR = findViewById(R.id.result_minus);
        minusBot =findViewById(R.id.minus_btn);
        minusError = findViewById(R.id.minus_error);
        minusL = findViewById(R.id.minus_layout);
        finalL = findViewById(R.id.final_layout);
        topL = findViewById(R.id.top_btn);
        ftx1 =findViewById(R.id.final_tx);
        ftx2 = findViewById(R.id.final_tx2);
        finalBtn =findViewById(R.id.money_final);
        finalMoney=Integer.parseInt(myMoney);
        who = findViewById(R.id.send_who);
        plusR.setText(myMoney);
        sendR.setText(myMoney);
        minusR.setText(myMoney);
        plusTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plusTop.setBackgroundResource(R.drawable.profile_background);
                plusTop.setTextColor(Color.parseColor("#ffffff"));
                sendTop.setBackgroundResource(R.drawable.find_info_background);
                sendTop.setTextColor(Color.parseColor("#666666"));
                minusTop.setBackgroundResource(R.drawable.find_info_background);
                minusTop.setTextColor(Color.parseColor("#666666"));
                plusL.setVisibility(View.VISIBLE);
                minusL.setVisibility(View.GONE);
                sendL.setVisibility(View.GONE);
            }
        });
        sendTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendTop.setBackgroundResource(R.drawable.profile_background);
                sendTop.setTextColor(Color.parseColor("#ffffff"));
                plusTop.setBackgroundResource(R.drawable.find_info_background);
                plusTop.setTextColor(Color.parseColor("#666666"));
                minusTop.setBackgroundResource(R.drawable.find_info_background);
                minusTop.setTextColor(Color.parseColor("#666666"));
                plusL.setVisibility(View.GONE);
                minusL.setVisibility(View.GONE);
                sendL.setVisibility(View.VISIBLE);
            }
        });
        minusTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minusTop.setBackgroundResource(R.drawable.profile_background);
                minusTop.setTextColor(Color.parseColor("#ffffff"));
                sendTop.setBackgroundResource(R.drawable.find_info_background);
                sendTop.setTextColor(Color.parseColor("#666666"));
                plusTop.setBackgroundResource(R.drawable.find_info_background);
                plusTop.setTextColor(Color.parseColor("#666666"));
                plusL.setVisibility(View.GONE);
                minusL.setVisibility(View.VISIBLE);
                sendL.setVisibility(View.GONE);
            }
        });

        bank.setFocusable(false);
        bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        plusE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String message = plusE.getText().toString();
                if(message.length()==0){
                    plusBot.setEnabled(false);
                    plusBot.setBackgroundResource(R.drawable.round_corner_gray);
                }else{
                    plusBot.setEnabled(true);
                    plusBot.setBackgroundResource(R.drawable.round_corner_blue);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
               String message = plusE.getText().toString();
                if(message.length()>0) {
                    int cal = Integer.parseInt(myMoney) + Integer.parseInt(message);
                    plusR.setText("₩ " + String.valueOf(cal));
                    finalMoney = cal;
                }
            }
        });
        plusBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int money = Integer.parseInt(plusE.getText().toString());
                UpdateParam param = new UpdateParam(member.getId(),money);
                serverPlusMoney(param);
            }
        });

        who.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String message = who.getText().toString();
                Log.i("flagLog", "1"+sendFlag1+"2"+sendFlag2);
                if(message.length()==0){
                    sendFlag1 =false;
                    sendBot.setEnabled(false);
                    sendBot.setBackgroundResource(R.drawable.round_corner_gray);
                }else{
                    sendFlag1 = true;
                    if(sendFlag1==true&&sendFlag2==true) {
                        sendBot.setEnabled(true);
                        sendBot.setBackgroundResource(R.drawable.round_corner_blue);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        sendE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String message = sendE.getText().toString();
                Log.i("flagLog", "1"+sendFlag1+"2"+sendFlag2);
                if(message.length()==0){
                    sendFlag2 = false;
                    sendBot.setEnabled(false);
                    sendBot.setBackgroundResource(R.drawable.round_corner_gray);
                }else{
                    sendFlag2 = true;
                    if(sendFlag1==true&&sendFlag2==true) {
                        sendBot.setEnabled(true);
                        sendBot.setBackgroundResource(R.drawable.round_corner_blue);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
               String message = sendE.getText().toString();
               if(message.length()>0) {
                   int cal = Integer.parseInt(myMoney) - Integer.parseInt(message);
                   if (cal < 0) {
                       sendError.setVisibility(View.VISIBLE);
                   } else {
                       sendError.setVisibility(View.GONE);
                       sendR.setText(String.valueOf(cal));
                       finalMoney = cal;
                   }
               }
            }
        });

        minusE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String message = minusE.getText().toString();
                if(message.length()==0){
                    minusBot.setEnabled(false);
                    minusBot.setBackgroundResource(R.drawable.round_corner_gray);
                }else{
                    minusBot.setEnabled(true);
                    minusBot.setBackgroundResource(R.drawable.round_corner_blue);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String message = minusE.getText().toString();
                if(message.length()>0) {
                    int cal = Integer.parseInt(myMoney) - Integer.parseInt(message);
                    if (cal < 0) {
                        minusError.setVisibility(View.VISIBLE);
                    } else {
                        minusError.setVisibility(View.GONE);
                        minusR.setText(String.valueOf(cal));
                        finalMoney = cal;
                    }
                }
            }
        });
        plusBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int money = Integer.parseInt(plusE.getText().toString());
                UpdateParam param = new UpdateParam(member.getId(),money);
                serverPlusMoney(param);
            }
        });
        sendBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int money = Integer.parseInt(sendE.getText().toString());
                if((Integer.parseInt(myMoney)-money)<0){
                    sendError.setVisibility(View.VISIBLE);
                }else{
                    String target = who.getText().toString();
                    UpdateParam param = new UpdateParam(member.getId(),money,target);
                    serverSendMoney(param);
                }
            }
        });
        minusBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int money = Integer.parseInt(minusE.getText().toString());
                if((Integer.parseInt(myMoney)-money)<0){
                    minusError.setVisibility(View.VISIBLE);
                }else{
                    UpdateParam param = new UpdateParam(member.getId(),money);
                    serverMinusMoney(param);
                }

            }
        });
        finalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(moneyActivity.this,MainActivity.class);
                member.setMoney(finalMoney);
                backIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                backIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                backIntent.putExtra("member",member);
                backIntent.putExtra("info",true);
                startActivity(backIntent);
            }
        });
    }

    private void serverPlusMoney(UpdateParam param) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitUpdateInterface service = retrofit.create(RetrofitUpdateInterface.class);

        Call<UpdateParam> call = service.plus(param);
        call.enqueue(new Callback<UpdateParam>() {
            @Override
            public void onResponse(Call<UpdateParam> call, Response<UpdateParam> response) {
                if (response.isSuccessful()) {
                    UpdateParam data = response.body();
                    plusL.setVisibility(View.GONE);
                    topL.setVisibility(View.GONE);
                    finalL.setVisibility(View.VISIBLE);
                    ftx1.setText("머니 충전 완료");
                    ftx2.setText("머니 충전이 완료되었습니다.");
                }
            }
            @Override
            public void onFailure(Call<UpdateParam> call, Throwable t) {
                Log.d("errorResult","이유:"+t );
            }
        });
    }
    private void serverMinusMoney(UpdateParam param) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitUpdateInterface service = retrofit.create(RetrofitUpdateInterface.class);

        Call<UpdateParam> call = service.minus(param);
        call.enqueue(new Callback<UpdateParam>() {
            @Override
            public void onResponse(Call<UpdateParam> call, Response<UpdateParam> response) {
                if (response.isSuccessful()) {
                    UpdateParam data = response.body();
                    if(!data.getResult().equals("x")){
                        minusL.setVisibility(View.GONE);
                        topL.setVisibility(View.GONE);
                        finalL.setVisibility(View.VISIBLE);
                        ftx1.setText("머니 출금 완료");
                        ftx2.setText("머니 출금이 완료되었습니다.");

                    }
                }
            }
            @Override
            public void onFailure(Call<UpdateParam> call, Throwable t) {
                Log.d("errorResult","이유:"+t );
            }
        });
    }
    private void serverSendMoney(UpdateParam param) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitUpdateInterface service = retrofit.create(RetrofitUpdateInterface.class);

        Call<UpdateParam> call = service.send(param);
        call.enqueue(new Callback<UpdateParam>() {
            @Override
            public void onResponse(Call<UpdateParam> call, Response<UpdateParam> response) {
                if (response.isSuccessful()) {
                    UpdateParam data = response.body();
                    if(!data.getResult().equals("x")){
                        sendL.setVisibility(View.GONE);
                        topL.setVisibility(View.GONE);
                        finalL.setVisibility(View.VISIBLE);
                        ftx1.setText("머니 송금 완료");
                        ftx2.setText("머니 송금이 완료되었습니다.");
                    }else if(data.getResult().equals("x")){
                        Toast.makeText(getApplicationContext(),"송금을 실패했습니다.",Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<UpdateParam> call, Throwable t) {
                Log.d("errorResult","이유:"+t );
            }
        });
    }

}
