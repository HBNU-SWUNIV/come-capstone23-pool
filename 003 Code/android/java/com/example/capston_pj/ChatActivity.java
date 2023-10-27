package com.example.capston_pj;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capston_pj.adapter.ChatAdapter;
import com.example.capston_pj.models.Member;
import com.example.capston_pj.models.MemberId;
import com.example.capston_pj.models.chattingRoom.GetRoom;
import com.example.capston_pj.models.chattingRoom.Rooms;
import com.example.capston_pj.retrofitInterface.RetrofitRoomListInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ChatActivity extends Fragment {
    private View view;

    private ArrayList<Rooms> arrayList;
    private ChatAdapter chatAdapter;
    private RecyclerView recyclerView;
    private Member member;
    private LinearLayoutManager linearLayoutManager;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_chat,container,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.chat_rv);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        Intent intent = getActivity().getIntent();
        member = (Member)intent.getSerializableExtra("member");

        getRoomList(member.getId());//member.getId()
        return view;
    }
    private void getRoomList(Long id) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitRoomListInterface appInterface = retrofit.create(RetrofitRoomListInterface.class);
        MemberId memberId = new MemberId(id);
        Call<GetRoom> call = appInterface.getAll(memberId);
        call.enqueue(new Callback<GetRoom>() {
            @Override
            public void onResponse(Call<GetRoom> call, Response<GetRoom> response) {
                if(response.isSuccessful()){
                    GetRoom data = response.body();

                    Log.d("나와라","Successed! , Result " + data.toString());
                    arrayList = new ArrayList<>();
                    for(Rooms i : data.item){
                        arrayList.add(i);

                    }

                    Context context;
                    context = getActivity().getApplicationContext();
                    chatAdapter = new ChatAdapter(arrayList,context,member);
                    recyclerView.setAdapter(chatAdapter);

                }
            }

            @Override
            public void onFailure(Call<GetRoom>call, Throwable t) {
                Log.d("bbb","onFailure: "+t.getLocalizedMessage());
            }
        });

    }



}
