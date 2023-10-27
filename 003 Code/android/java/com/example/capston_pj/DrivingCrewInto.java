package com.example.capston_pj;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.capston_pj.models.Member;
import com.example.capston_pj.models.crew.Crew;
import com.example.capston_pj.models.crew.GetCrew;
import com.example.capston_pj.models.crew.SaveCrew;
import com.example.capston_pj.models.post.PostId;
import com.example.capston_pj.models.post.PostReviewDTO;
import com.example.capston_pj.models.post.SaveInfo;
import com.example.capston_pj.retrofitInterface.RetrofitInterface;
import com.example.capston_pj.retrofitInterface.RetrofitPostContent;
import com.example.capston_pj.retrofitInterface.RetrofitPostInterface;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DrivingCrewInto extends AppCompatActivity{

    private TextView driver,info,start1,end1,time1,mon1,tues1,wen1,thurs1,fri1,mon2,tues2,wen2,thurs2,fri2,type,tx_dow,reviewT,rCnt,drivers;
    private Button add,infoAdd,infoCancel,review,mail,update;
    private Dialog ad,adInfo,adInfoAdd,reviewD,uad;
    private Button mon,tues,wen,thurs,fri,d_am,d_pm;
    private EditText loginId;
    private ImageView indi;
    private Spinner spinner;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    private String diaTime="";
    private String time,dow,infoS;
    private Long postId;
    private String[] peopleArr;
    private int indicnt;
    private int c1,c2,c3,c4,c5,c6,c7,sRCnt;
    private float score;
    private ImageView profile;
    private LinearLayout profileBack;
    private String mode = "normal";
    private Member member;
    private float sReview;

    Long writerId;


        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_crew);
            start1 = (TextView)findViewById(R.id.crew_start1);
            end1 = (TextView) findViewById(R.id.crew_end1);
            time1 = (TextView) findViewById(R.id.crew_time1);
      /*  start2 = (TextView) view.findViewById(R.id.crew_start2);
        end2 = (TextView) view.findViewById(R.id.crew_end2);
        time2 = (TextView) view.findViewById(R.id.crew_time2);*/
            mon1 = (TextView) findViewById(R.id.crew_mon1);
            mon2 = (TextView) findViewById(R.id.crew_mon2);
            tues1 = (TextView) findViewById(R.id.crew_tues1);
            tues2 = (TextView) findViewById(R.id.crew_tues2);
            wen1 = (TextView) findViewById(R.id.crew_wen1);
            wen2 = (TextView) findViewById(R.id.crew_wen2);
            thurs1 = (TextView) findViewById(R.id.crew_thurs1);
            thurs2 = (TextView) findViewById(R.id.crew_thurs2);
            fri1 = (TextView) findViewById(R.id.crew_fri1);
            fri2 = (TextView) findViewById(R.id.crew_fri2);
            add = (Button) findViewById(R.id.crew_add1);
            driver = (TextView) findViewById(R.id.crew_driver);
            info = (TextView)findViewById(R.id.crew_info);
            infoAdd = (Button)findViewById(R.id.crew_info_add);
            indi = (ImageView)findViewById(R.id.indicator);
            infoCancel = (Button)findViewById(R.id.crew_info_cancel);
            type = (TextView)findViewById(R.id.crew_d_type);
            tx_dow = (TextView)findViewById(R.id.crew_tx_dow);
            profile = (ImageView)findViewById(R.id.crew_profile);
            review = (Button)findViewById(R.id.crew_review);
            reviewT = (TextView)findViewById(R.id.item_crew_into_ever);
            rCnt = (TextView)findViewById(R.id.item_crew_into_ever_cnt);
            mail = (Button) findViewById(R.id.mail);
            drivers = (TextView)findViewById(R.id.crew_drivers);
            update = (Button)findViewById(R.id.crew_update);

            //loginId.setText(" ");

            Intent intent = getIntent();
             member = (Member) intent.getSerializableExtra("member");
           // getDrivingInfo(member.getId());
            dow = intent.getStringExtra("dow");
            String [] dowList = dow.split(",");
            time =intent.getStringExtra("times");
            infoS = intent.getStringExtra("info");
            sReview = intent.getFloatExtra("review",0);
            sRCnt = intent.getIntExtra("rCnt",0);
            Log.d("infoSCheck","info: "+intent.getStringExtra("info"));
            String [] times = time.split(",");
            Log.d("driverCheck","writer: "+intent.getStringExtra("writer"));
            String writer = intent.getStringExtra("writer");
            Log.d("driverCheck2","writer: "+writer);
            writerId = intent.getLongExtra("writerId",0);
            drivers.setText(drivers.getText()+writer);

            mode = intent.getStringExtra("mode");

            String imageUrl = "url"+writerId;
            loadProfileImage(imageUrl,profile);

            reviewT.setText(String.valueOf(sReview));
            rCnt.setText("("+String.valueOf(sRCnt)+")");


            if(member.getId()!=writerId){
                add.setVisibility(View.GONE);
                mail.setVisibility(View.GONE);
                review.setVisibility(View.VISIBLE);
                update.setVisibility(View.GONE);
            }else {
                add.setVisibility(View.VISIBLE);
                mail.setVisibility(View.VISIBLE);
                review.setVisibility(View.GONE);
                update.setVisibility(View.VISIBLE);
            }

            if(infoS.length()>0){
                info.setText(infoS);
            }
            Log.d("tiemsCheck","times.length "+times.length);
            if(times.length==2){
                type.setText(type.getText()+"왕복");
            }else {
                type.setText(type.getText()+"편도");
            }

            for(String i : dowList){
                if(i.equals("1")){
                    tx_dow.setText(tx_dow.getText()+"월 ");
                }
                if(i.equals("2")){
                    tx_dow.setText(tx_dow.getText()+"화 ");
                }
                if(i.equals("3")){
                    tx_dow.setText(tx_dow.getText()+"수 ");
                }
                if(i.equals("4")){
                    tx_dow.setText(tx_dow.getText()+"목 ");
                }
                if(i.equals("5")){
                    tx_dow.setText(tx_dow.getText()+"금 ");
                }
                
            }
            
            driver.setText(writer + driver.getText());
            driver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getOther(writer);
                }
            });
            String[] start = intent.getStringExtra("start").split(",");
            String[] end = intent.getStringExtra("end").split(",");
            start1.setText(start1.getText()+start[0]);
            end1.setText(end1.getText() +end[0]);
            if(times.length==1){
                time1.setText(time1.getText()+times[0]);
            } else if (times.length==2) {
                time1.setText(time1.getText()+times[0]+"/"+times[1]);
            }

            postId = intent.getLongExtra("id",0);
            String people = intent.getStringExtra("people");
            Log.d("crewIntoPeople","people="+people);
            peopleArr = people.split("");

            //arrayList.add(data.getTimes());
           // Log.d("crewInfo","id="+data.getId());
            getCrews(postId);

            ad = new Dialog(this);
            ad.setContentView(R.layout.dialog);
            uad = new Dialog(this);
            uad.setContentView(R.layout.crew_update_dialog);
            adInfoAdd = new Dialog(this);
            adInfoAdd.setContentView(R.layout.dialog_info_add);
            reviewD = new Dialog(this);
            reviewD.setContentView(R.layout.dialog_review);

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialog();
                }
            });
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateDialog();
                }
            });

            indicnt = 0;
            indi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    indicnt++;
                    if (indicnt % 2 != 0) {
                        info.setVisibility(View.VISIBLE);
                        indi.setBackgroundResource(R.drawable.icon_reverse_expend);
                        if(member.getId()==writerId){
                            infoAdd.setVisibility(View.VISIBLE);
                            infoCancel.setVisibility(View.VISIBLE);

                        }

                    } else {
                        info.setVisibility(View.GONE);
                        infoAdd.setVisibility(View.GONE);
                        infoCancel.setVisibility(View.GONE);
                        indi.setBackgroundResource(R.drawable.icon_expand);

                    }
                }
            });
            infoAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showInfoAdd();
                }
            });
            review.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reviewDialog();
                }
            });
            mail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent appIntent = new Intent(DrivingCrewInto.this,MailActivity.class);
                    appIntent.putExtra("postId",postId);
                    appIntent.putExtra("writerId",writerId);
                    appIntent.putExtra("member",member);
                    startActivity(appIntent);
                }
            });
        }
    private void showDialog(){
        WindowManager.LayoutParams params = ad.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        ad.getWindow().setAttributes((android.view.WindowManager.LayoutParams)params);
        ad.show();

        CheckBox driver = (CheckBox)ad.findViewById(R.id.driverCheck);
        loginId = (EditText)ad.findViewById(R.id.crew_loginId);
        Button okBtn = ad.findViewById(R.id.dialog_ok);
        Button noBtn = ad.findViewById(R.id.dialog_cancel);
        mon = (Button)ad.findViewById(R.id.d_mon);
        tues = (Button)ad.findViewById(R.id.d_tuse);
        wen = (Button)ad.findViewById(R.id.d_wen);
        thurs = (Button)ad.findViewById(R.id.d_thurs);
        fri = (Button)ad.findViewById(R.id.d_fri);
        d_am = (Button)ad.findViewById(R.id.d_morning);
        d_pm = (Button)ad.findViewById(R.id.d_afternoon);
        String [] timeList = time.split(",");
        String [] dowList = dow.split(",");

        c1 = 0;
        c2 = 0;
        c3 = 0;
        c4 = 0;
        c5 = 0;
        c6 = 0;
        c7 = 0;

        if(mode.equals("DC")){
            driver.setVisibility(View.VISIBLE);
        }else {
            driver.setVisibility(View.GONE);
        }

        for(String i : dowList){
            Log.d("dowListCheck","dow="+i);
            if(i.equals("1")&&Integer.parseInt(peopleArr[0])>0){
                Log.d("dowListCheck2","mon");
                mon.setEnabled(true);
                mon.setBackgroundResource(R.drawable.round_corner);
            }
            if(i.equals("2")&&Integer.parseInt(peopleArr[1])>0){
                Log.d("dowListCheck2","tues");
                tues.setEnabled(true);
                tues.setBackgroundResource(R.drawable.round_corner);
            }
            if(i.equals("3")&&Integer.parseInt(peopleArr[2])>0){
                Log.d("dowListCheck2","wen");
                wen.setEnabled(true);
                wen.setBackgroundResource(R.drawable.round_corner);
            }
            if(i.equals("4")&&Integer.parseInt(peopleArr[3])>0){
                Log.d("dowListCheck2","thurs");
                thurs.setEnabled(true);
                thurs.setBackgroundResource(R.drawable.round_corner);
            }
            if(i.equals("5")&&Integer.parseInt(peopleArr[4])>0){
                Log.d("dowListCheck2","fri");
                fri.setEnabled(true);
                fri.setBackgroundResource(R.drawable.round_corner);
            }
        }
        for(String i : timeList){
            if(i.charAt(0)==('A')){
                d_am.setClickable(true);
                d_am.setEnabled(true);
                d_am.setBackground(ContextCompat.getDrawable(this, R.drawable.round_corner));
            }
            if(i.charAt(0)==('P')){
                d_pm.setClickable(true);
                d_pm.setEnabled(true);
                d_pm.setBackground(ContextCompat.getDrawable(this, R.drawable.round_corner));
            }
        }


/*        boolean dowCheck [] = new boolean[5];
        for(int i =0 ; i<5;i++){
            dowCheck[i] = false;
        }
        boolean noonCheck [] = new boolean[2];
        for(int i =0 ; i<2;i++){
            dowCheck[i] = false;
        }*/
        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c1++;
                if (c1 % 2 != 0) {
                    mon.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    mon.setTextColor(Color.parseColor("#4946E7"));

                }else{
                    mon.setBackgroundResource(R.drawable.round_corner);
                    mon.setTextColor(Color.parseColor("#000000"));

                }
            }
        });
        tues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c2++;
                if (c2 % 2 != 0) {
                    tues.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    tues.setTextColor(Color.parseColor("#4946E7"));

                }else{
                    tues.setBackgroundResource(R.drawable.round_corner);
                    tues.setTextColor(Color.parseColor("#000000"));

                }
            }
        });
        wen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c3++;
                if (c3 % 2 != 0) {
                    wen.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    wen.setTextColor(Color.parseColor("#4946E7"));

                }else{
                    wen.setBackgroundResource(R.drawable.round_corner);
                    wen.setTextColor(Color.parseColor("#000000"));

                }
            }
        });
        thurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c4++;
                if (c4 % 2 != 0) {
                    thurs.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    thurs.setTextColor(Color.parseColor("#4946E7"));

                }else{
                    thurs.setBackgroundResource(R.drawable.round_corner);
                    thurs.setTextColor(Color.parseColor("#000000"));

                }
            }
        });
        fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c5++;
                if (c5 % 2 != 0) {
                    fri.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    fri.setTextColor(Color.parseColor("#4946E7"));

                }else{
                    fri.setBackgroundResource(R.drawable.round_corner);
                    fri.setTextColor(Color.parseColor("#000000"));

                }
            }
        });
        d_am.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c6++;
                if (c6 % 2 != 0) {
                    d_am.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    d_am.setTextColor(Color.parseColor("#4946E7"));


                }else{
                    d_am.setBackgroundResource(R.drawable.round_corner);
                    d_am.setTextColor(Color.parseColor("#000000"));

                }
            }
        });
        d_pm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c7++;
                if (c7 % 2 != 0) {
                    d_pm.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    d_pm.setTextColor(Color.parseColor("#4946E7"));


                }else{
                    d_pm.setBackgroundResource(R.drawable.round_corner);
                    d_pm.setTextColor(Color.parseColor("#000000"));


                }
            }
        });


        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Integer> dowList = new ArrayList<>();
                if(c1%2!=0){
                    dowList.add(1);
                }
                if(c2%2!=0){
                    dowList.add(2);
                }
                if(c3%2!=0){
                    dowList.add(3);
                }
                if(c4%2!=0){
                    dowList.add(4);
                }
                if(c5%2!=0){
                    dowList.add(5);
                }
                if(c6%2!=0){
                    diaTime = timeList[0];
                }
                if(c7%2!=0){
                    if(!diaTime.equals("")){
                        diaTime = diaTime+","+timeList[1];
                    }
                    if(diaTime.equals("")){
                        diaTime=timeList[1];
                    }

                }

                dowList.sort(Comparator.naturalOrder());
                String dow =String.valueOf(dowList.get(0));
                for(int a=1;a<dowList.size();a++){
                    dow+=","+String.valueOf(dowList.get(a));
                }


                Log.d("crewSaveCheck","loginId="+String.valueOf(loginId.getText())+"postId"+postId+"dow"+dow+"diaTime"+diaTime);
                SaveCrew crew = new SaveCrew(String.valueOf(loginId.getText()),postId,dow,diaTime);
                if(driver.isChecked()){
                    saveDriver(postId,String.valueOf(loginId.getText()));
                }
                saveCrew(crew);
                recreate();
            }
        });
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad.dismiss();
            }
        });

    }
    private void updateDialog(){
        WindowManager.LayoutParams params = uad.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        uad.getWindow().setAttributes((android.view.WindowManager.LayoutParams)params);
        uad.show();

        CheckBox delete = (CheckBox)uad.findViewById(R.id.delete);
        loginId = (EditText)uad.findViewById(R.id.crew_loginId);
        Button okBtn = uad.findViewById(R.id.dialog_ok);
        Button noBtn = uad.findViewById(R.id.dialog_cancel);
        mon = (Button)uad.findViewById(R.id.d_mon);
        tues = (Button)uad.findViewById(R.id.d_tuse);
        wen = (Button)uad.findViewById(R.id.d_wen);
        thurs = (Button)uad.findViewById(R.id.d_thurs);
        fri = (Button)uad.findViewById(R.id.d_fri);
        d_am = (Button)uad.findViewById(R.id.d_morning);
        d_pm = (Button)uad.findViewById(R.id.d_afternoon);
        String [] timeList = time.split(",");
        String [] dowList = dow.split(",");

        c1 = 0;
        c2 = 0;
        c3 = 0;
        c4 = 0;
        c5 = 0;
        c6 = 0;
        c7 = 0;
        Log.d("시간 체크","time ="+timeList[0]);
        if(mode.equals("DC")){
            driver.setVisibility(View.VISIBLE);
        }else {
            driver.setVisibility(View.GONE);
        }

        for(String i : dowList){
            Log.d("dowListCheck","dow="+i);
            if(i.equals("1")&&Integer.parseInt(peopleArr[0])>0){
                Log.d("dowListCheck2","mon");
                mon.setEnabled(true);
                mon.setBackgroundResource(R.drawable.round_corner);
            }
            if(i.equals("2")&&Integer.parseInt(peopleArr[1])>0){
                Log.d("dowListCheck2","tues");
                tues.setEnabled(true);
                tues.setBackgroundResource(R.drawable.round_corner);
            }
            if(i.equals("3")&&Integer.parseInt(peopleArr[2])>0){
                Log.d("dowListCheck2","wen");
                wen.setEnabled(true);
                wen.setBackgroundResource(R.drawable.round_corner);
            }
            if(i.equals("4")&&Integer.parseInt(peopleArr[3])>0){
                Log.d("dowListCheck2","thurs");
                thurs.setEnabled(true);
                thurs.setBackgroundResource(R.drawable.round_corner);
            }
            if(i.equals("5")&&Integer.parseInt(peopleArr[4])>0){
                Log.d("dowListCheck2","fri");
                fri.setEnabled(true);
                fri.setBackgroundResource(R.drawable.round_corner);
            }
        }
        for(String i : timeList){

            if(i.charAt(0)==('A')){
                d_am.setClickable(true);
                d_am.setEnabled(true);
                d_am.setBackground(ContextCompat.getDrawable(this, R.drawable.round_corner));
            }
            if(i.charAt(0)==('P')){
                d_pm.setClickable(true);
                d_pm.setEnabled(true);
                d_pm.setBackground(ContextCompat.getDrawable(this, R.drawable.round_corner));
            }
        }

        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c1++;
                if (c1 % 2 != 0) {
                    mon.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    mon.setTextColor(Color.parseColor("#4946E7"));

                }else{
                    mon.setBackgroundResource(R.drawable.round_corner);
                    mon.setTextColor(Color.parseColor("#000000"));

                }
            }
        });
        tues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c2++;
                if (c2 % 2 != 0) {
                    tues.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    tues.setTextColor(Color.parseColor("#4946E7"));

                }else{
                    tues.setBackgroundResource(R.drawable.round_corner);
                    tues.setTextColor(Color.parseColor("#000000"));

                }
            }
        });
        wen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c3++;
                if (c3 % 2 != 0) {
                    wen.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    wen.setTextColor(Color.parseColor("#4946E7"));

                }else{
                    wen.setBackgroundResource(R.drawable.round_corner);
                    wen.setTextColor(Color.parseColor("#000000"));

                }
            }
        });
        thurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c4++;
                if (c4 % 2 != 0) {
                    thurs.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    thurs.setTextColor(Color.parseColor("#4946E7"));

                }else{
                    thurs.setBackgroundResource(R.drawable.round_corner);
                    thurs.setTextColor(Color.parseColor("#000000"));

                }
            }
        });
        fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c5++;
                if (c5 % 2 != 0) {
                    fri.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    fri.setTextColor(Color.parseColor("#4946E7"));

                }else{
                    fri.setBackgroundResource(R.drawable.round_corner);
                    fri.setTextColor(Color.parseColor("#000000"));

                }
            }
        });
        d_am.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c6++;
                if (c6 % 2 != 0) {
                    d_am.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    d_am.setTextColor(Color.parseColor("#4946E7"));


                }else{
                    d_am.setBackgroundResource(R.drawable.round_corner);
                    d_am.setTextColor(Color.parseColor("#000000"));

                }
            }
        });
        d_pm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c7++;
                if (c7 % 2 != 0) {
                    d_pm.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    d_pm.setTextColor(Color.parseColor("#4946E7"));


                }else{
                    d_pm.setBackgroundResource(R.drawable.round_corner);
                    d_pm.setTextColor(Color.parseColor("#000000"));


                }
            }
        });


        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Integer> dowList = new ArrayList<>();
                if(c1%2!=0){
                    dowList.add(1);
                }
                if(c2%2!=0){
                    dowList.add(2);
                }
                if(c3%2!=0){
                    dowList.add(3);
                }
                if(c4%2!=0){
                    dowList.add(4);
                }
                if(c5%2!=0){
                    dowList.add(5);
                }
                if(c6%2!=0){
                    diaTime = timeList[0];
                }
                if(c7%2!=0){
                    if(!diaTime.equals("")){
                        diaTime = diaTime+","+timeList[1];
                    }
                    if(diaTime.equals("")){
                        diaTime=timeList[1];
                    }

                }
                if(delete.isChecked()){
                    SaveCrew deleteCrew = new SaveCrew(String.valueOf(loginId.getText()),postId,"delete","");
                    updateCrew(deleteCrew);
                }else{
                    Log.d("dow size", "dow size = "+dowList.size());
                    dowList.sort(Comparator.naturalOrder());
                    String dow =String.valueOf(dowList.get(0));
                    for(int a=1;a<dowList.size();a++){
                        dow+=","+String.valueOf(dowList.get(a));
                    }
                    SaveCrew crew = new SaveCrew(String.valueOf(loginId.getText()),postId,dow,diaTime);
                    updateCrew(crew);
                }

                Log.d("crewSaveCheck","loginId="+String.valueOf(loginId.getText())+"postId"+postId+"dow"+dow+"diaTime"+diaTime);


                recreate();
            }
        });
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uad.dismiss();
            }
        });

    }

    private void showInfoAdd(){
        WindowManager.LayoutParams params = adInfoAdd.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        adInfoAdd.getWindow().setAttributes((android.view.WindowManager.LayoutParams)params);
        adInfoAdd.show();

        Button okBtn = adInfoAdd.findViewById(R.id.info_add_ok);
        Button noBtn = adInfoAdd.findViewById(R.id.info_add_no);
        EditText edit = adInfoAdd.findViewById(R.id.info_add_edit);
        edit.setText(infoS);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveInfo info = new SaveInfo(postId,edit.getText().toString());
               saveInfo(info);
            }
        });
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adInfoAdd.dismiss();
            }
        });
    }
    private void reviewDialog(){
        WindowManager.LayoutParams params = reviewD.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        reviewD.getWindow().setAttributes((android.view.WindowManager.LayoutParams)params);
        reviewD.show();

        Button okBtn = reviewD.findViewById(R.id.review_ok);
        Button noBtn = reviewD.findViewById(R.id.review_cancel);
        RatingBar rating = reviewD.findViewById(R.id.review_rating);
        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                score = v;
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostReviewDTO postReview = new PostReviewDTO(postId,score,member.getId());
                saveReview(postReview);
                reviewD.dismiss();

            }
        });
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reviewD.dismiss();
            }
        });
    }

    private void getCrews(Long id){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitPostContent appInterface = retrofit.create(RetrofitPostContent.class);
        PostId postId = new PostId(id);
        Call<GetCrew> call = appInterface.getCrew(postId);
        call.enqueue(new Callback<GetCrew>() {
            @Override
            public void onResponse(Call<GetCrew> call, Response<GetCrew> response) {
                if(response.isSuccessful()){
                    GetCrew data = response.body();
                    // Log.d("crewInfoOn","data="+data.item.get(0).getMemberName());
                    for(Crew i : data.item){
                        Log.d("driver","driver="+i.getDriver());
                        if(i.getDriver().equals("o")){
                            if(i.getMemberId()!=writerId)
                                drivers.setText(drivers.getText()+","+i.getMemberName());
                        }
                        Log.d("dow","dow="+i.getDow());
                        Log.d("getCrew","crewName="+i.getMemberName());
                        String [] dow = i.getDow().split(",");
                        String [] noon = i.getTimes().split(",");
                        for(String j : dow){
                            if(j.equals("1")){
                                for(String k:noon){
                                    if(k.charAt(0)=='A'){
                                        if(mon1.getText().length()==0){
                                            mon1.setText(String.valueOf(i.getMemberName()));
                                        }else{
                                            mon1.setText(mon1.getText()+"\n"+String.valueOf(i.getMemberName()));
                                        }
                                    }
                                    if(k.charAt(0)=='P'){
                                        if(mon2.getText().length()==0){
                                            mon2.setText(String.valueOf(i.getMemberName()));
                                        }else{
                                            mon2.setText(mon2.getText()+"\n"+String.valueOf(i.getMemberName()));
                                        }
                                    }
                                }
                            }
                            if(j.equals("2")){
                                for(String k:noon){
                                    if(k.charAt(0)=='A'){
                                        if(tues1.getText().length()==0){
                                            tues1.setText(String.valueOf(i.getMemberName()));
                                        }else{
                                            tues1.setText(tues1.getText()+"\n"+String.valueOf(i.getMemberName()));
                                        }
                                    }
                                    if(k.charAt(0)=='P'){
                                        if(tues2.getText().length()==0){
                                            tues2.setText(String.valueOf(i.getMemberName()));
                                        }else{
                                            tues2.setText(tues2.getText()+"\n"+String.valueOf(i.getMemberName()));
                                        }
                                    }
                                }
                            }
                            if(j.equals("3")){
                                for(String k:noon){
                                    if(k.charAt(0)=='A'){
                                        if(wen1.getText().length()==0){
                                            wen1.setText(String.valueOf(i.getMemberName()));
                                        }else{
                                            wen1.setText(wen1.getText()+"\n"+String.valueOf(i.getMemberName()));
                                        }

                                    }
                                    if(k.charAt(0)=='P'){
                                        if(wen2.getText().length()==0){
                                            wen2.setText(String.valueOf(i.getMemberName()));
                                        }else{
                                            wen2.setText(wen2.getText()+"\n"+String.valueOf(i.getMemberName()));
                                        }
                                    }
                                }
                            }
                            if(j.equals("4")){
                                for(String k:noon){
                                    if(k.charAt(0)=='A'){
                                        if(thurs1.getText().length()==0){
                                            thurs1.setText(String.valueOf(i.getMemberName()));
                                        }else{
                                            thurs1.setText(thurs1.getText()+"\n"+String.valueOf(i.getMemberName()));
                                        }
                                    }
                                    if(k.charAt(0)=='P'){
                                        if(thurs2.getText().length()==0){
                                            thurs2.setText(String.valueOf(i.getMemberName()));
                                        }else{
                                            thurs2.setText(thurs2.getText()+"\n"+String.valueOf(i.getMemberName()));
                                        }
                                    }
                                }
                            }
                            if(j.equals("5")){
                                for(String k:noon){
                                    if(k.charAt(0)=='A'){
                                        if(fri1.getText().length()==0){
                                            fri1.setText(String.valueOf(i.getMemberName()));
                                        }else{
                                            fri1.setText(fri1.getText()+"\n"+String.valueOf(i.getMemberName()));
                                        }
                                    }
                                    if(k.charAt(0)=='P'){
                                        if(fri2.getText().length()==0){
                                            fri2.setText(String.valueOf(i.getMemberName()));
                                        }else{
                                            fri2.setText(fri2.getText()+"\n"+String.valueOf(i.getMemberName()));
                                        }
                                    }
                                }
                            }

                        }

                    }

                }
            }

            @Override
            public void onFailure(Call<GetCrew> call, Throwable t) {
                Log.d("errorResult","이유:"+t );
            }
        });
    }
    public void loadProfileImage(String imageUrl, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.basic) // 로딩 중에 표시할 이미지
                .error(R.drawable.basic) // 로딩 실패 시 표시할 이미지
                .diskCacheStrategy(DiskCacheStrategy.NONE); // 디스크 캐시 전략 설정

        Glide.with(this)
                .load(imageUrl)
                .apply(requestOptions)
                .into(imageView);
    }
    private void saveCrew(SaveCrew crew){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitPostContent appInterface = retrofit.create(RetrofitPostContent.class);
        Log.d("loginID","loginId="+crew.getLoginId());
        Call<SaveCrew> call = appInterface.saveCrew(crew);
        call.enqueue(new Callback<SaveCrew>() {
            @Override
            public void onResponse(Call<SaveCrew> call, Response<SaveCrew> response) {
                if(response.isSuccessful()){
                    SaveCrew data = response.body();
                    if(data.getDow().equals("x")){
                        Toast toast = Toast.makeText(getApplicationContext(), "일정이 곂칩니다.", Toast.LENGTH_SHORT);
                        // Toast 메시지 표시
                        toast.show();
                    }
                }
            }
            @Override
            public void onFailure(Call<SaveCrew> call, Throwable t) {
                Log.d("errorResult","이유:"+t );
            }
        });
    }

    private void updateCrew(SaveCrew crew){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitPostContent appInterface = retrofit.create(RetrofitPostContent.class);
        Log.d("loginID","loginId="+crew.getLoginId());
        Call<SaveCrew> call = appInterface.update(crew);
        call.enqueue(new Callback<SaveCrew>() {
            @Override
            public void onResponse(Call<SaveCrew> call, Response<SaveCrew> response) {
                if(response.isSuccessful()){
                    SaveCrew data = response.body();
                    if(data.getDow().equals("x")){
                        Toast toast = Toast.makeText(getApplicationContext(), "일정이 곂칩니다.", Toast.LENGTH_SHORT);
                        // Toast 메시지 표시
                        toast.show();
                    }
                }
            }
            @Override
            public void onFailure(Call<SaveCrew> call, Throwable t) {
                Log.d("errorResult","이유:"+t );
            }
        });
    }

    private void saveInfo(SaveInfo info){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitPostContent appInterface = retrofit.create(RetrofitPostContent.class);

        Call<SaveInfo> call = appInterface.saveInfo(info);
        call.enqueue(new Callback<SaveInfo>() {
            @Override
            public void onResponse(Call<SaveInfo> call, Response<SaveInfo> response) {
                if(response.isSuccessful()){
                    SaveInfo data = response.body();

                }

            }

            @Override
            public void onFailure(Call<SaveInfo> call, Throwable t) {
                Log.d("errorResult","이유:"+t );
            }
        });
    }
    private void saveDriver(long postId,String driver){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitPostContent appInterface = retrofit.create(RetrofitPostContent.class);
        Log.d("driverParam","postId:"+postId+"driver"+driver );
        SaveInfo saveDriverN = new SaveInfo(postId,driver);

        Call<SaveInfo> call = appInterface.saveDriver(saveDriverN);
        call.enqueue(new Callback<SaveInfo>() {
            @Override
            public void onResponse(Call<SaveInfo> call, Response<SaveInfo> response) {
                if(response.isSuccessful()){
                    SaveInfo data = response.body();

                }

            }

            @Override
            public void onFailure(Call<SaveInfo> call, Throwable t) {
                Log.d("errorResult","이유:"+t );
            }
        });
    }
    private void saveReview(PostReviewDTO review){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitPostInterface appInterface = retrofit.create(RetrofitPostInterface.class);

        Call<PostReviewDTO> call = appInterface.addReview(review);
        call.enqueue(new Callback<PostReviewDTO>() {
            @Override
            public void onResponse(Call<PostReviewDTO> call, Response<PostReviewDTO> response) {
                if(response.isSuccessful()){
                    PostReviewDTO data = response.body();
                    String result = data.getResult();
                    if(result.equals("x")){
                        Toast.makeText(DrivingCrewInto.this,"리뷰저장에 실패했습니다.",Toast.LENGTH_SHORT).show();

                    }
                }

            }

            @Override
            public void onFailure(Call<PostReviewDTO> call, Throwable t) {
                Log.d("errorResult","이유:"+t );
            }
        });
    }
    private void getOther(String mName) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitInterface service = retrofit.create(RetrofitInterface.class);
        Member memberP = new Member(mName);
        Call<Member> call = service.findOther(memberP);
        call.enqueue(new Callback<Member>() {
            @Override
            public void onResponse(Call<Member> call, Response<Member> response) {
                if (response.isSuccessful()) {
                    Member data = response.body();
                    Intent intent2 = new Intent(DrivingCrewInto.this,OtherUserInfoActivity.class);
                    intent2.putExtra("member",data);
                    Log.d("writerClick","other:"+data.getId());
                    startActivity(intent2);

                }
            }
            @Override
            public void onFailure(Call<Member> call, Throwable t) {
                Log.d("errorResult","이유:"+t );
            }
        });
    }
}
