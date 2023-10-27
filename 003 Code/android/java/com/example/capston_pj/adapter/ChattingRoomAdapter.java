package com.example.capston_pj.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capston_pj.R;
import com.example.capston_pj.models.Member;
import com.example.capston_pj.models.chattingRoom.Code;
import com.example.capston_pj.models.chattingRoom.Record;

import java.util.ArrayList;

public class ChattingRoomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ArrayList<Record> records;
    private Member member;

    public ChattingRoomAdapter(ArrayList<Record> records, Member member) {
        this.records = records;
        this.member = member;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(viewType == Code.ViewType.LEFT_CONTENT)
        {
            view = inflater.inflate(R.layout.item_left_chat, parent, false);
            return new LeftViewHolder(view);
        }
        else
        {
            view = inflater.inflate(R.layout.item_right_chat, parent, false);
            return new RightViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {


        if(viewHolder instanceof LeftViewHolder)
        {
            ((LeftViewHolder) viewHolder).message.setText(records.get(position).getMessage());

        }
        else
        {
            ((RightViewHolder) viewHolder).message.setText(records.get(position).getMessage());
        }
    }


    @Override
    public int getItemCount() {
        return (null != records ? records.size() : 0);
    }
    @Override
    public int getItemViewType(int position) {
        return records.get(position).getViewType();
    }

    public class LeftViewHolder extends RecyclerView.ViewHolder{
        TextView message;

        LeftViewHolder(View itemView)
        {
            super(itemView);

            message = itemView.findViewById(R.id.item_chatting_room_left);
        }
    }

    public class RightViewHolder extends RecyclerView.ViewHolder{
        TextView message;
        RightViewHolder(View itemView)
        {
            super(itemView);
            message = itemView.findViewById(R.id.item_chatting_room_right);
        }
    }
}
