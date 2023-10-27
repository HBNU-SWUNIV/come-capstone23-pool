package com.example.capston_pj.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.capston_pj.ChattingRoomActivity;
import com.example.capston_pj.R;
import com.example.capston_pj.models.ChattingRoomCode;
import com.example.capston_pj.models.Member;
import com.example.capston_pj.models.chattingRoom.MacDto;
import com.example.capston_pj.models.post.App;
import com.example.capston_pj.models.post.AppList;
import com.example.capston_pj.retrofitInterface.RetrofitCreateRoom;
import com.example.capston_pj.retrofitInterface.RetrofitMacInterface;
import com.example.capston_pj.retrofitInterface.RetrofitPostListInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MailAdapter extends RecyclerView.Adapter<MailAdapter.AppViewHolder> {

    private ArrayList<AppList>arrayList;
    private Context context;
    private Long writer,postId;
    private Member member;


    public MailAdapter(ArrayList<AppList> arrayList, Context context, long writer, long postId, Member member) {
        this.arrayList = arrayList;
        this.context = context;
        this.writer = writer;
        this.postId = postId;
        this.member = member;
    }

    @NonNull
    @Override
    public MailAdapter.AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app,parent,false);
        AppViewHolder holder = new AppViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MailAdapter.AppViewHolder holder, int position) {
        AppList data = arrayList.get(position);
        Long id = data.getId();
        String name = data.getName();
        Long profile = data.getProfile();
        float score = data.getScore();
        String dow = data.getDow();

        String[] dows1 = dow.split("/");
        if(dows1[0].equals("00000")){
            holder.am.setVisibility(View.GONE);
        }else {
            String[] split = dows1[0].split("");
            String dowResult = "";
            if(split[0].equals("1")){
                dowResult+="월";
            }
            if(split[1].equals("1")){
                dowResult+= "화";
            }
            if(split[2].equals("1")){
                dowResult+="수";
            }
            if(split[3].equals("1")){
                dowResult+="목";
            }
            if(split[4].equals("1")){
                dowResult+= "금";
            }
            Log.d("dowAm","="+dowResult);
            holder.dowAm.setText(dowResult);

        }


        if(dows1[1].equals("00000")){
            holder.pm.setVisibility(View.GONE);
        }else {
            String[] split = dows1[1].split("");
            String dowResult = "";
            if(split[0].equals("1")){
                dowResult+="월";
            }
            if(split[1].equals("1")){
                dowResult+= "화";
            }
            if(split[2].equals("1")){
                dowResult+="수";
            }
            if(split[3].equals("1")){
                dowResult+="목";
            }
            if(split[4].equals("1")){
                dowResult+= "금";
            }
            Log.d("dowPm","="+dowResult);
            holder.dowPm.setText(dowResult);

        }


        holder.name.setText(name);
        holder.score.setText(String.valueOf(score));

        String imageUrl = "url"+data.getId();
        loadProfileImage(imageUrl,holder.profile);
        holder.ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeChatRoom(writer,id);
            }
        });
        holder.no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i =0;i<arrayList.size();i++){
                    if(id==arrayList.get(i).getId()){
                        arrayList.remove(i);
                        notifyItemRemoved(i);
                        notifyDataSetChanged();
                    }
                }
                appDelete(postId,id);
            }
        });
    }
    public void loadProfileImage(String imageUrl, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.basic) // 로딩 중에 표시할 이미지
                .error(R.drawable.basic) // 로딩 실패 시 표시할 이미지
                .diskCacheStrategy(DiskCacheStrategy.NONE); // 디스크 캐시 전략 설정

        Glide.with(context)
                .load(imageUrl)
                .apply(requestOptions)
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }
    public class AppViewHolder extends RecyclerView.ViewHolder{
        private  TextView name,score,dowAm,dowPm;
        private ImageView profile;
        private Button ok, no;

        private LinearLayout am,pm;
        public AppViewHolder (@Nullable View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.item_mail_name);
            score = itemView.findViewById(R.id.item_mail_ever);
            profile = itemView.findViewById(R.id.item_mail_profile);
            ok = itemView.findViewById(R.id.item_mail_ok);
            no = itemView.findViewById(R.id.item_mail_no);
            am = itemView.findViewById(R.id.item_am);
            pm = itemView.findViewById(R.id.item_pm);
            dowAm =itemView.findViewById(R.id.item_dow_am);
            dowPm =itemView.findViewById(R.id.item_dow_pm);
        }
    }

    private void appDelete(long postId,long id) {
        App app = new App(postId,id);
        Log.d("CrewPostListTest","in");
        Retrofit retrofit = new Retrofit.Builder().baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitPostListInterface appInterface = retrofit.create(RetrofitPostListInterface.class);
        Call<App> call = appInterface.appDel(app);
        call.enqueue(new Callback<App>() {
            @Override
            public void onResponse(Call<App> call, Response<App> response) {
                if(response.isSuccessful()){
                    App data = response.body();

                }
            }

            @Override
            public void onFailure(Call<App>call, Throwable t) {
                Log.d("bbb","onFailure: "+t.getLocalizedMessage());
            }
        });
    }

    private void makeChatRoom(long writer,long id){ //**********신청자 목록 리스트에서 처리!!!!&&application 관련 dto 생성하기
        List<Long> idList = new ArrayList<>();
        idList.add(id);
        idList.add(writer);
        ChattingRoomCode chattingRoomCode = new ChattingRoomCode(idList,"nego");
        Retrofit retrofit = new Retrofit.Builder().baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitCreateRoom appInterface = retrofit.create(RetrofitCreateRoom.class);
        Call<ChattingRoomCode> call = appInterface.getCode(chattingRoomCode);
        call.enqueue(new Callback<ChattingRoomCode>() {
            @Override
            public void onResponse(Call<ChattingRoomCode> call, Response<ChattingRoomCode> response) {
                if(response.isSuccessful()){
                    ChattingRoomCode data = response.body();
                    chattingRoomCode.setRoomId(data.getRoomId());
                    Log.d("stateCheck","state ="+data.getState());
                    if(data.getState().equals("new")) {
                        Log.d("saveMacCheck","memberId ="+id);
                        Log.d("saveMacCheck","writerId ="+writer);
                        joinChatting(id, data.getRoomId());
                        joinChatting(writer, data.getRoomId());
                    }
                    appDelete(postId,id);
                    Intent intent3 = new Intent(context, ChattingRoomActivity.class);
                    intent3.putExtra("member",member);
                    intent3.putExtra("roomCode",chattingRoomCode);
                    intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent3);

                }
            }

            @Override
            public void onFailure(Call<ChattingRoomCode> call, Throwable t) {
                Log.d("errorResult","이유:"+t );
            }
        });
    }

    private void joinChatting(long memberId,String roomId){//**********신청자 목록 리스트에서 처리!!!!
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitMacInterface service = retrofit.create(RetrofitMacInterface.class);
        MacDto macDto = new MacDto(memberId,roomId);
        Call<MacDto> call = service.save(macDto);
        call.enqueue(new Callback<MacDto>() {
            @Override
            public void onResponse(Call<MacDto> call, Response<MacDto> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                MacDto result = response.body();

            }

            @Override
            public void onFailure(Call<MacDto> call, Throwable t) {

            }
        });
    }
}
