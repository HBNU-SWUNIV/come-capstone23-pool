package com.example.capston_pj;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.capston_pj.models.FindLoginId;
import com.example.capston_pj.models.UpdateParam;
import com.example.capston_pj.retrofitInterface.RetrofitInterface;
import com.example.capston_pj.retrofitInterface.RetrofitUpdateInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FindInfoActivity extends AppCompatActivity {
    Button id,pw,findId,findIdLogin,findPw,changePw,findPwLogin;
    EditText idNick,pwId,pwNick,pwNew,pwNew2;
    TextView idResult,error,idtxt1,idtxt2;
    Spinner idGen,pwGen;
    boolean pwedCheck1=false;
    boolean pwedCheck2 = false;
    String idtx,idgender,pwtx,pwgender,pwidtx;
    LinearLayout findIdL,findPwL,idResultL,pwChangeL;

    private Long memberId;
    private String memberName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findinfo);

        id = (Button) findViewById(R.id.find_info_id);
        pw = (Button) findViewById(R.id.find_info_pw);
        //아이디 찾기
        findIdL = (LinearLayout)findViewById(R.id.findid_layout);
        idtxt1 = (TextView)findViewById(R.id.idtxt);
        idNick = (EditText) findViewById(R.id.findid_nickname);
        idtxt2 = (TextView)findViewById(R.id.idtxt2);
        idGen = (Spinner) findViewById(R.id.findid_gender);
        findId = (Button) findViewById(R.id.findid_btn);
        //비밀번호 찾기
        findPwL = (LinearLayout)findViewById(R.id.findpw_layout);
        pwId = (EditText) findViewById(R.id.findpw_id);
        pwNick = (EditText) findViewById(R.id.findpw_nickname);
        pwGen = (Spinner) findViewById(R.id.findpw_gender);
        findPw = (Button) findViewById(R.id.findpw_btn);
        //아이디 결과
        idResultL = (LinearLayout)findViewById(R.id.layout_idresult);
        idResult = (TextView) findViewById(R.id.id_result);
        findIdLogin = (Button) findViewById(R.id.findid_login_btn);
        //비밀번호 변경화면
        pwChangeL = (LinearLayout)findViewById(R.id.layout_pwchange);
        pwNew = (EditText) findViewById(R.id.findpw_chagepw1);
        pwNew2 = (EditText) findViewById(R.id.findpw_chagepw2);
        changePw = (Button) findViewById(R.id.findpw_change_btn);

        error = (TextView)findViewById(R.id.error);

        findId.setEnabled(false);
        findPw.setEnabled(false);

        id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id.setBackgroundResource(R.drawable.profile_background);
                id.setTextColor(Color.parseColor("#ffffff"));
                pw.setBackgroundResource(R.drawable.find_info_background);
                pw.setTextColor(Color.parseColor("#666666"));

               findIdL.setVisibility(View.VISIBLE);
               findPwL.setVisibility(View.GONE);
               idResultL.setVisibility(View.GONE);
               pwChangeL.setVisibility(View.GONE);

               findId.setVisibility(View.VISIBLE);
               findIdLogin.setVisibility(View.GONE);
               findPw.setVisibility(View.GONE);
               changePw.setVisibility(View.GONE);
            }
        });
        pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pw.setBackgroundResource(R.drawable.profile_background);
                pw.setTextColor(Color.parseColor("#ffffff"));
                id.setBackgroundResource(R.drawable.find_info_background);
                id.setTextColor(Color.parseColor("#666666"));

                findIdL.setVisibility(View.GONE);
                findPwL.setVisibility(View.VISIBLE);
                idResultL.setVisibility(View.GONE);
                pwChangeL.setVisibility(View.GONE);

                findId.setVisibility(View.GONE);
                findIdLogin.setVisibility(View.GONE);
                findPw.setVisibility(View.VISIBLE);
                changePw.setVisibility(View.GONE);
            }
        });
        idNick.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String message = idNick.getText().toString();
                if(message.length()==0){
                    findId.setEnabled(false);
                    findId.setBackgroundResource(R.drawable.round_corner_gray);
                }else{
                    findId.setEnabled(true);
                    findId.setBackgroundResource(R.drawable.round_corner_blue);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        pwId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String message = pwId.getText().toString();
                if(message.length()==0){
                    pwedCheck1= false;
                    findPw.setEnabled(false);
                    findPw.setBackgroundResource(R.drawable.round_corner_gray);
                }else{
                    pwedCheck1 = true;
                    if(pwedCheck1==true&&pwedCheck2==true){
                        findPw.setEnabled(true);
                        findPw.setBackgroundResource(R.drawable.round_corner_blue);
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        pwNick.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String message = pwNick.getText().toString();
                if(message.length()==0){
                    pwedCheck2= false;
                    findPw.setEnabled(false);
                    findPw.setBackgroundResource(R.drawable.round_corner_gray);
                }else{
                    pwedCheck2 = true;
                    if(pwedCheck1==true&&pwedCheck2==true){
                        findPw.setEnabled(true);
                        findPw.setBackgroundResource(R.drawable.round_corner_blue);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        idGen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idgender = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        pwGen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pwgender = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        findId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idtx = idNick.getText().toString();
                String genderResult = "m";

                if(idgender.equals("남")){
                    genderResult ="m";
                } else if (idgender.equals("여")) {
                    genderResult ="w";
                }
                findIdServer(idtx,genderResult);
            }
        });
        findPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findPwL = (LinearLayout)findViewById(R.id.findpw_layout);

                pwidtx = pwId.getText().toString();
                pwtx = pwNick.getText().toString();
                String genderResult = "m";

                if(pwgender.equals("남")){
                    genderResult ="m";
                } else if (pwgender.equals("여")) {
                    genderResult ="w";
                }

                findPwServer(pwidtx,pwtx,genderResult);
            }
        });
        changePw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String first = pwNew.getText().toString();
                String second = pwNew2.getText().toString();

                if(first.equals(second)){
                    chagePwServer(memberId,first,memberName);
                }else {
                    error.setVisibility(View.VISIBLE);
                    error.setText("비밀번호가 일치하지 않습니다.");
                }
            }
        });
        findIdLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private void findIdServer(String id,String gender){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FindLoginId input = new FindLoginId(id,gender);
        RetrofitInterface service = retrofit.create(RetrofitInterface.class);
        Call<FindLoginId> call = service.findId(input);
        call.enqueue(new Callback<FindLoginId>() {
            @Override
            public void onResponse(Call<FindLoginId> call, Response<FindLoginId> response) {
                if (response.isSuccessful()) {
                    FindLoginId data = response.body();
                    Log.d("getidresult","불러온 값:"+data);
                    if(data.getResult().equals("x")){
                        error.setVisibility(View.VISIBLE);
                    }
                    else{
                        findIdL.setVisibility(View.GONE);
                        findPwL.setVisibility(View.GONE);
                        idResultL.setVisibility(View.VISIBLE);
                        pwChangeL.setVisibility(View.GONE);

                        findId.setVisibility(View.GONE);
                        findIdLogin.setVisibility(View.VISIBLE);
                        findPw.setVisibility(View.GONE);
                        changePw.setVisibility(View.GONE);

                        idResult.setText(idResult.getText()+data.getResult()+"입니다.");

                        idResult.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<FindLoginId> call, Throwable t) {
                Log.d("errorResult","이유:"+t );
            }
        });
    }
    private void findPwServer(String loginId, String id,String gender){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FindLoginId input = new FindLoginId(loginId,id,gender);
        RetrofitInterface service = retrofit.create(RetrofitInterface.class);
        Call<FindLoginId> call = service.findPw(input);
        call.enqueue(new Callback<FindLoginId>() {
            @Override
            public void onResponse(Call<FindLoginId> call, Response<FindLoginId> response) {
                if (response.isSuccessful()) {
                    FindLoginId data = response.body();
                    Log.d("getidresult","불러온 값:"+data);
                    if(data.getResult().equals("x")){
                        error.setVisibility(View.VISIBLE);
                    }
                    else{
                        findIdL.setVisibility(View.GONE);
                        findPwL.setVisibility(View.GONE);
                        idResultL.setVisibility(View.GONE);
                        pwChangeL.setVisibility(View.VISIBLE);

                        findId.setVisibility(View.GONE);
                        findIdLogin.setVisibility(View.GONE);
                        findPw.setVisibility(View.GONE);
                        changePw.setVisibility(View.VISIBLE);
                        memberId = data.getMemberId();
                        Log.d("getid","불러온 값:"+data.getMemberId());
                        memberName = data.getName();

                    }
                }
            }
            @Override
            public void onFailure(Call<FindLoginId> call, Throwable t) {
                Log.d("errorResult","이유:"+t );
            }
        });
    }
    private void chagePwServer(Long id, String pw,String name){

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

            }

            @Override
            public void onFailure(Call<UpdateParam> call, Throwable t) {
                Log.d("fail","error:"+t);
            }
        });
    }

}
