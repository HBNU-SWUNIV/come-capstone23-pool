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
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.capston_pj.DrivingCrewInto;
import com.example.capston_pj.R;
import com.example.capston_pj.models.Member;
import com.example.capston_pj.models.post.Posting;

import java.util.ArrayList;

public class CrewAdapter extends RecyclerView.Adapter<CrewAdapter.CrewViewHolder> {

    private ArrayList<Posting>arrayList;
    private Context context;
    private Member member;

    public CrewAdapter(ArrayList<Posting> arrayList, Context context, Member member) {
        this.arrayList = arrayList;
        this.context = context;
        this.member = member;
    }

    @NonNull
    @Override
    public CrewAdapter.CrewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_crew,parent,false);
        CrewViewHolder holder = new CrewViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CrewAdapter.CrewViewHolder holder, int position) {
        Posting data = arrayList.get(position);
        String writer = data.getLoginId();
        String title = data.getName();
        String originalStart = data.getStart();
        String originalEnd = data.getEnd();
        String[] start = originalStart.split(",");
        String[] end = originalEnd.split(",");
        String []times = data.getTimes().split(",");
        String originalTimes = data.getTimes();
        String content = data.getContent();
        String dow = data.getDow();
        String people = data.getPeople();
        long writerId = data.getWriterId();
        long id = data.getId();
        String info = data.getInfo();
        int sPrice = data.getPrice();
        String getMode = data.getMode();
        float review= data.getReview();
        int rCount = data.getrCount();

        if(getMode.equals("DC")){
            holder.mode.setVisibility(View.VISIBLE);
        }else {
            holder.mode.setVisibility(View.GONE);
        }

        holder.writer.setText("작성자 :" + writer);
        holder.title.setText(title);
        holder.start.setText(start[0]);
        holder.end.setText(end[0]);
        holder.sTime1.setText(times[0]);
        holder.review.setText(String.valueOf(review));
        holder.rCnt.setText("("+String.valueOf(rCount)+")");

        if(times.length>1){
            holder.sTime2.setText(times[1]);
        }

        holder.price.setText(String.valueOf(sPrice)+"원");
        String imageUrl = "url"+data.getWriterId();
        loadProfileImage(imageUrl,holder.profile);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DrivingCrewInto.class);
                intent.putExtra("writer",writer);
                intent.putExtra("title",title);
                intent.putExtra("id",id);
                intent.putExtra("writerId",writerId);
                intent.putExtra("start",originalStart);
                intent.putExtra("end",originalEnd);
                intent.putExtra("member",member);
                intent.putExtra("content",content);
                intent.putExtra("dow",dow);
                intent.putExtra("times",originalTimes);
                intent.putExtra("people",people);
                intent.putExtra("info",info);
                intent.putExtra("mode",getMode);
                intent.putExtra("review",review);
                intent.putExtra("rCnt",rCount);
                Log.d("CrewPostList","writer: "+writer+" title: "+title+" id: "+id+" writerId: "+writerId+
                        " start: "+start+" end: "+end+" member: "+member+" content: "+content+" dow: "+dow+" times: "+times+" people: "+people+" info"+info);
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
    public class CrewViewHolder extends RecyclerView.ViewHolder{
        private TextView title,writer,sTime1,sTime2,start,end,price,review,rCnt;
        private ImageView profile, mode;
        public CrewViewHolder (@Nullable View itemView){
            super(itemView);
            writer = itemView.findViewById(R.id.item_crew_writer);
            title = itemView.findViewById(R.id.item_crew_title);
            start = itemView.findViewById(R.id.item_crew_start);
            end = itemView.findViewById(R.id.item_crew_end);
            sTime1 = itemView.findViewById(R.id.item_crew_time1);
            sTime2 = itemView.findViewById(R.id.item_crew_time2);
            price = itemView.findViewById(R.id.item_crew_price);
            profile = itemView.findViewById(R.id.item_crew_profile);
            mode = itemView.findViewById(R.id.item_crew_rotation);
            review = itemView.findViewById(R.id.item_crew_ever);
            rCnt = itemView.findViewById(R.id.item_crew_ever_cnt);
        }
    }
}
