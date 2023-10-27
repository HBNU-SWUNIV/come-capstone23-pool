package com.example.capston_pj.adapter;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.capston_pj.ChattingRoomActivity;
import com.example.capston_pj.R;
import com.example.capston_pj.models.ChattingRoomCode;
import com.example.capston_pj.models.Member;
import com.example.capston_pj.models.chattingRoom.Rooms;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private ArrayList<Rooms> arrayList;


    private Context context;
    private Member member;
    private long senderID=1;
    public ChatAdapter(ArrayList<Rooms> arrayList, Context context, Member member) {
        this.arrayList = arrayList;
        this.context = context;
        this.member = member;

    }

    @NonNull
    @Override
    public ChatAdapter.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chatting_room_list,parent,false);
        ChatViewHolder holder = new ChatViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ChatViewHolder holder, int position) {
      /*  holder.sender.setText(arrayList.get(position).getSender());
        holder.lastMessage.setText(arrayList.get(position).getLastMessage());
        holder.time.setText(arrayList.get(position).getTime());*/
        holder.sender.setText(arrayList.get(position).getNames());
        Rooms data = arrayList.get(position);
        Log.d("position","position:"+position);

        ChattingRoomCode roomCode = new ChattingRoomCode(arrayList.get(position).getRoomId(),arrayList.get(position).getRoomName());
        String [] ids  = data.getIds().split(",");
        for(String i : ids ){
            long id = Long.parseLong(i);
            if(member.getId()==id){
                continue;
            }else {
                senderID = id;
            }
        }

        String imageUrl = "url"+String.valueOf(senderID);
        loadProfileImage(imageUrl,holder.profile);
        Log.d("roomID","roomID:"+data.getRoomId());

        //getRoomRecord(holder,data.getRoomId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //해당 채팅방이동
                Intent intent = new Intent(context, ChattingRoomActivity.class);
                intent.putExtra("roomCode",roomCode);
                intent.putExtra("member",member);
                intent.putExtra("senderId",senderID);
                context.startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK));

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
    //아이템 삭제시 사용
    public void remove(int position){
        try {
            arrayList.remove(position);
            notifyItemRemoved(position);
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {

        protected TextView sender;
        protected TextView lastMessage;
        protected TextView time;

        private ImageView profile;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            this.sender = (TextView) itemView.findViewById(R.id.item_chat_name);
            this.lastMessage = (TextView) itemView.findViewById(R.id.item_chat_contents);
            this.time = (TextView) itemView.findViewById(R.id.item_chat_time);
            this.profile = (ImageView) itemView.findViewById(R.id.item_chat_profile);
        }
    }

}
