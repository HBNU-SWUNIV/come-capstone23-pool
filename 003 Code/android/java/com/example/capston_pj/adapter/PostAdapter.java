package com.example.capston_pj.adapter;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
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
import com.example.capston_pj.ListActivity;
import com.example.capston_pj.PostIntoActivity;
import com.example.capston_pj.R;
import com.example.capston_pj.models.Member;
import com.example.capston_pj.models.post.Posting;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Posting> datas = null;
    private Context context;
    private ListActivity listActivity;
    private Member member;

    public PostAdapter(Context context, List<Posting> datas, ListActivity listActivity, Member member) {
        this.datas = datas;
        this.context = context;
        this.listActivity = listActivity;
        this.member = member;

    }

    @NonNull
    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Posting data = datas.get(position);
        String writer = data.getLoginId();
        String title = data.getName();
        String originalStart = data.getStart();
        String originalEnd = data.getEnd();
        String[] start = originalStart.split(",");
        String[] end = originalEnd.split(",");
        String []times = data.getTimes().split(",");
        float review= data.getReview();
        int rCount = data.getrCount();
        int sPrice = data.getPrice();
        long writerId = data.getWriterId();
        long id = data.getId();
        String mode = data.getMode();

        if(mode.equals("DC")){
            holder.modeImg.setVisibility(View.VISIBLE);
        }else {
            holder.modeImg.setVisibility(View.GONE);
        }
        holder.writer.setText("작성자 :" + writer);
        holder.title.setText(title);
        holder.start.setText(start[0]);
        holder.end.setText(end[0]);
        holder.review.setText(String.valueOf(review));
        holder.rCnt.setText("("+String.valueOf(rCount)+")");

        if(times.length==1){
            holder.sTime1.setText(times[0]);
        } else if (times.length==2) {
            holder.sTime1.setText(times[0]);
            holder.sTime2.setText(times[1]);
        }
        holder.price.setText(String.valueOf(sPrice)+"원");
        String imageUrl = "url"+data.getWriterId();
        loadProfileImage(imageUrl,holder.profile);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cUser = listActivity.cUsername.toString();
                Intent intent = new Intent(context, PostIntoActivity.class);
                intent.putExtra("writer",writer);
                intent.putExtra("title",title);
                intent.putExtra("id",id);
                intent.putExtra("writerId",writerId);
                intent.putExtra("start",originalStart);
                intent.putExtra("end",originalEnd);
                intent.putExtra("cUser",cUser);
                intent.putExtra("member",member);
                intent.putExtra("mode",mode);
                intent.putExtra("review",review);
                intent.putExtra("rCnt",rCount);
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
        return datas.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder{
        private TextView title,writer,sTime1,sTime2,start,end,price,review,rCnt;
        private ImageView modeImg;
        private ImageView profile;

        public PostViewHolder (@Nullable View itemView){
            super(itemView);
            writer = itemView.findViewById(R.id.item_post_writer);
            title = itemView.findViewById(R.id.item_post_title);
            start = itemView.findViewById(R.id.item_post_start);
            end = itemView.findViewById(R.id.item_post_end);
            sTime1 = itemView.findViewById(R.id.item_start_time1);
            sTime2 = itemView.findViewById(R.id.item_start_time2);
            price = itemView.findViewById(R.id.item_post_price);
            profile = itemView.findViewById(R.id.item_post_profile);
            modeImg = itemView.findViewById(R.id.item_post_rotation);
            review = itemView.findViewById(R.id.post_into_ever);
            rCnt = itemView.findViewById(R.id.post_into_ever_cnt);
        }

    }
}
