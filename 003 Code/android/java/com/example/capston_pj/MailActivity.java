package com.example.capston_pj;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capston_pj.adapter.MailAdapter;
import com.example.capston_pj.models.Member;
import com.example.capston_pj.models.post.AppList;
import com.example.capston_pj.models.post.GetApp;
import com.example.capston_pj.retrofitInterface.RetrofitPostListInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MailActivity extends AppCompatActivity {

    private View view;

    private MailAdapter mailAdapter;
    private RecyclerView recyclerView;

    private Long postId,writerId;
    private Member member;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_list);
        recyclerView = (RecyclerView)findViewById(R.id.mailList_rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        Intent intent =getIntent();
        postId = intent.getLongExtra("postId",0);
        writerId = intent.getLongExtra("writerId",0);
        member = (Member) intent.getSerializableExtra("member");
        getAppList(postId);
    }

    private void getAppList(long id) {
        Log.d("CrewPostListTest","in");
        Retrofit retrofit = new Retrofit.Builder().baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitPostListInterface appInterface = retrofit.create(RetrofitPostListInterface.class);
        Call<GetApp> call = appInterface.result(id);
        call.enqueue(new Callback<GetApp>() {
            @Override
            public void onResponse(Call<GetApp> call, Response<GetApp> response) {
                if(response.isSuccessful()){
                    GetApp data = response.body();
                    ArrayList<AppList> appArr = new ArrayList<>();

                    for(AppList i : data.item){
                        appArr.add(i);

                    }
                    Context context;
                    context = getApplicationContext();
                    mailAdapter = new MailAdapter(appArr,context,writerId,postId,member);
                    recyclerView.setAdapter(mailAdapter);

                }
                Log.d("CrewPostListfail","fail");
            }

            @Override
            public void onFailure(Call<GetApp>call, Throwable t) {
                Log.d("bbb","onFailure: "+t.getLocalizedMessage());
            }
        });
    }
}
