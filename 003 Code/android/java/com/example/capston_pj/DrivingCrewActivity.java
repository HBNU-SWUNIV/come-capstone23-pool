package com.example.capston_pj;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capston_pj.adapter.CrewAdapter;
import com.example.capston_pj.models.Member;
import com.example.capston_pj.models.MemberId;
import com.example.capston_pj.models.post.GetPosting2;
import com.example.capston_pj.models.post.Posting;
import com.example.capston_pj.retrofitInterface.RetrofitPostContent;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DrivingCrewActivity extends Fragment {

    private View view;

    private ArrayList<Posting>arrayList;
    private CrewAdapter crewAdapter;
    private RecyclerView recyclerView;
    private Posting posting;
    private Member member;

    private LinearLayoutManager linearLayoutManager;
    private TextView title;



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_crew_list,container,false);

        title = (TextView)view.findViewById(R.id.crewList_txt);
        recyclerView = (RecyclerView) view.findViewById(R.id.crewList_rv);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);


        Intent intent = getActivity().getIntent();
        member = (Member)intent.getSerializableExtra("member");
        title.setText(member.getName()+title.getText());
        Log.d("CrewPostListTest","on");
        GetPostList(member.getId());

        return view;
    }

    private void GetPostList(long id) {
        Log.d("CrewPostListTest","in");
        Retrofit retrofit = new Retrofit.Builder().baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitPostContent appInterface = retrofit.create(RetrofitPostContent.class);
        MemberId memberId = new MemberId(id);
        Call<GetPosting2> call = appInterface.getPostForCrew(memberId);
        call.enqueue(new Callback<GetPosting2>() {
            @Override
            public void onResponse(Call<GetPosting2> call, Response<GetPosting2> response) {
                if(response.isSuccessful()){
                    GetPosting2 data = response.body();

                    arrayList = new ArrayList<>();
                    for(Posting i : data.item){
                        arrayList.add(i);
                        Log.d("CrewPostList","data: "+i.getId());
                    }
                    Context context;
                    context = getActivity().getApplicationContext();
                    crewAdapter = new CrewAdapter(arrayList,context,member);
                    recyclerView.setAdapter(crewAdapter);

                }
                Log.d("CrewPostListfail","fail");
            }

            @Override
            public void onFailure(Call<GetPosting2>call, Throwable t) {
                Log.d("bbb","onFailure: "+t.getLocalizedMessage());
            }
        });
    }
}
