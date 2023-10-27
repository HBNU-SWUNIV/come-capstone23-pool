package com.example.capston_pj;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.capston_pj.models.GetContent;
import com.example.capston_pj.models.Member;
import com.example.capston_pj.models.post.App;
import com.example.capston_pj.retrofitInterface.RetrofitInterface;
import com.example.capston_pj.retrofitInterface.RetrofitPostContent;
import com.example.capston_pj.retrofitInterface.RetrofitPostListInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostIntoActivity  extends AppCompatActivity {
    private TextView title,writer,content,start,end, price ,mon1,tues1,wen1,thurs1,fri1,type,dowT,mon2,tues2,wen2,thurs2,fri2,time,tpeople,review,rCnt;

    private TextView dmon1,dtues1,dwen1,dthurs1,dfri1,dmon2,dtues2,dwen2,dthurs2,dfri2,gender,smoke,pet,child,baggage;
    private EditText dpeople;
    private ImageView profile,driveMode;

    private String sTitle,sWriter,sStart,sEnd,cUser,people;
    private String tdmon1="";
    private String tdtues1="";
    private String tdwen1="";
    private String tdthurs1="";
    private String tdfri1="";
    private String tdmon2="";
    private String tdtues2="";
    private String tdwen2="";
    private String tdthurs2="";
    private String tdfri2="";

    private Dialog adcj;
    private String dow [];
    private String times[];
    private Long writerId;
    private Member member;
    private Button joinBtn, dcancle,dok;
    private String mode;
    long sid;
    private int c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,sRCnt;
    private float sReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_into);

        title = (TextView) findViewById(R.id.post_into_title);
        writer = (TextView) findViewById(R.id.post_into_driver);
        content = (TextView) findViewById(R.id.post_into_contents);
        start = (TextView) findViewById(R.id.post_into_start);
        end = (TextView) findViewById(R.id.post_into_end);
        type = (TextView)findViewById(R.id.post_into_type);
        dowT = (TextView)findViewById(R.id.post_into_dow);
        time = (TextView)findViewById(R.id.post_into_time);
        price = (TextView)findViewById(R.id.post_into_price);
        mon1 = (TextView) findViewById(R.id.into_mon1);
        mon2 = (TextView) findViewById(R.id.into_mon2);
        tues1 = (TextView) findViewById(R.id.into_tues1);
        tues2 = (TextView) findViewById(R.id.into_tues2);
        wen1 = (TextView) findViewById(R.id.into_wen1);
        wen2 = (TextView) findViewById(R.id.into_wen2);
        thurs1 = (TextView) findViewById(R.id.into_thurs1);
        thurs2 = (TextView) findViewById(R.id.into_thurs2);
        fri1 = (TextView) findViewById(R.id.into_fri1);
        fri2 = (TextView) findViewById(R.id.into_fri2 );
        joinBtn = (Button)findViewById(R.id.post_into_join);
        tpeople=(TextView)findViewById(R.id.post_into_people);
        gender = (TextView)findViewById(R.id.into_gender);
        smoke = (TextView)findViewById(R.id.into_smoke);
        pet = (TextView)findViewById(R.id.into_pet);
        child = (TextView)findViewById(R.id.into_child);
        baggage = (TextView)findViewById(R.id.into_baggage);
        profile = (ImageView)findViewById(R.id.into_profile);
        driveMode = (ImageView)findViewById(R.id.item_post_rotation);
        review = (TextView)findViewById(R.id.post_into_ever);
        rCnt = (TextView)findViewById(R.id.post_into_ever_cnt);

        String imageUrl = "url"+writerId;
        loadProfileImage(imageUrl,profile);


        Intent intent = getIntent();
        sTitle = intent.getStringExtra("title");
        sWriter = intent.getStringExtra("writer");
        sStart = intent.getStringExtra("start");
        sEnd = intent.getStringExtra("end");
        cUser = intent.getStringExtra("cUser");
        sid = intent.getLongExtra("id",0);
        writerId = intent.getLongExtra("writerId",0);
        member = (Member)intent.getSerializableExtra("member");
        mode = intent.getStringExtra("mode");
        sReview = intent.getFloatExtra("review",0);
        sRCnt = intent.getIntExtra("rCnt",0);

        review.setText(String.valueOf(sReview));
        rCnt.setText("("+String.valueOf(sRCnt)+")");

        if(mode.equals("DC")){
            driveMode.setVisibility(View.VISIBLE);
        }else {
            driveMode.setVisibility(View.GONE);
        }

        adcj = new Dialog(this);
        adcj.setContentView(R.layout.dialog_crewjoin);

        title.setText(sTitle);
        writer.setText(sWriter);
        String[] nStart = sStart.split(",");
        String[] nEnd = sEnd.split(",");
        start.setText(start.getText().toString()+" "+nStart[0]);
        end.setText(end.getText().toString()+" "+nEnd[0]);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostIntoActivity.this,MapNaverActivityforCheck.class);
                intent.putExtra("lat",nStart[1]);
                intent.putExtra("lng",nStart[2]);
                startActivityResult.launch(intent);
            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostIntoActivity.this,MapNaverActivityforCheck.class);
                intent.putExtra("lat",nEnd[1]);
                intent.putExtra("lng",nEnd[2]);
                startActivityResult.launch(intent);
            }
        });
        writer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("writerClick","check=");
                getOther(writer.getText().toString());
            }
        });
        Log.d("sidcheck","sid="+sid);
        createPost(sid);
        Log.d("sWriter","sWriter="+sWriter);
        Log.d("cUser","cUser="+cUser);
        if(sWriter.equals(cUser)) {
            joinBtn.setVisibility(View.GONE);
        }
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAdd();
            }
        });
    }
    private void showAdd(){
        WindowManager.LayoutParams params = adcj.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        adcj.getWindow().setAttributes((android.view.WindowManager.LayoutParams)params);
        adcj.show();
        dmon1 = (TextView) adcj.findViewById(R.id.cj_mon1);
        dmon2 = (TextView) adcj.findViewById(R.id.cj_mon2);
        dtues1 = (TextView) adcj.findViewById(R.id.cj_tues1);
        dtues2 = (TextView) adcj.findViewById(R.id.cj_tues2);
        dwen1 = (TextView) adcj.findViewById(R.id.cj_wen1);
        dwen2 = (TextView) adcj.findViewById(R.id.cj_wen2);
        dthurs1 = (TextView) adcj.findViewById(R.id.cj_thurs1);
        dthurs2 = (TextView) adcj.findViewById(R.id.cj_thurs2);
        dfri1 = (TextView) adcj.findViewById(R.id.cj_fri1);
        dfri2 = (TextView) adcj.findViewById(R.id.cj_fri2 );
        Button okBtn = adcj.findViewById(R.id.info_add_ok);
        Button noBtn = adcj.findViewById(R.id.info_add_no);
        c1 = 0;
        c2 = 0;
        c3 = 0;
        c4 = 0;
        c5 = 0;
        c6 = 0;
        c7 = 0;
        c8 = 0;
        c9 = 0;
        c10 = 0;
        dmon1.setText(tdmon1);
        dtues1.setText(tdtues1);
        dwen1.setText(tdwen1);
        dthurs1.setText(tdthurs1);
        dfri1.setText(tdfri1);
        dmon2.setText(tdmon2);
        dtues2.setText(tdtues2);
        dwen2.setText(tdwen2);
        dthurs2.setText(tdthurs2);
        dfri2.setText(tdfri2);
        // dow = 1,2,3,4,5/1,2,3,4,5 (am/pm)
        int[] am = {0,0,0,0,0};
        int[] pm = {0,0,0,0,0};

        dmon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tdmon1.length()>0) {
                    c1++;
                    if (c1 % 2 != 0) {
                        dmon1.setBackgroundResource(R.drawable.table_inside_blue);
                        dmon1.setTextColor(Color.parseColor("#4946E7"));
                        am[0] = 1;

                    } else {
                        dmon1.setBackgroundResource(R.drawable.table_inside);
                        dmon1.setTextColor(Color.parseColor("#000000"));
                        am[0] =0;

                    }
                }
            }
        });
        dtues1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tdtues1.length()>0) {
                    c2++;
                    if (c2 % 2 != 0) {
                        dtues1.setBackgroundResource(R.drawable.table_inside_blue);
                        dtues1.setTextColor(Color.parseColor("#4946E7"));
                        am[1] = 1;

                    } else {
                        dtues1.setBackgroundResource(R.drawable.table_inside);
                        dtues1.setTextColor(Color.parseColor("#000000"));
                        am[1] = 0;

                    }
                }
            }
        });
        dwen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tdwen1.length()>0) {
                    c3++;
                    if (c3 % 2 != 0) {
                        dwen1.setBackgroundResource(R.drawable.table_inside_blue);
                        dwen1.setTextColor(Color.parseColor("#4946E7"));
                        am[2] = 1;

                    } else {
                        dwen1.setBackgroundResource(R.drawable.table_inside);
                        dwen1.setTextColor(Color.parseColor("#000000"));
                        am[2] = 0;
                    }
                }
            }
        });
        dthurs1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tdthurs1.length()>0) {
                    c4++;

                    if (c4 % 2 != 0) {
                        dthurs1.setBackgroundResource(R.drawable.table_inside_blue);
                        dthurs1.setTextColor(Color.parseColor("#4946E7"));
                        am[3] = 1;
                    } else {
                        dthurs1.setBackgroundResource(R.drawable.table_inside);
                        dthurs1.setTextColor(Color.parseColor("#000000"));
                        am[3] = 0;
                    }
                }
            }
        });
        dfri1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tdfri1.length()>0) {
                    c5++;
                    if (c5 % 2 != 0) {
                        dfri1.setBackgroundResource(R.drawable.table_inside_blue);
                        dfri1.setTextColor(Color.parseColor("#4946E7"));
                        am[4] = 1;
                    } else {
                        dfri1.setBackgroundResource(R.drawable.table_inside);
                        dfri1.setTextColor(Color.parseColor("#000000"));
                        am[4] = 0;
                    }
                }
            }
        });
        dmon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tdmon2.length()>0) {
                    c1++;
                    if (c1 % 2 != 0) {
                        dmon2.setBackgroundResource(R.drawable.table_inside_blue);
                        dmon2.setTextColor(Color.parseColor("#4946E7"));
                        pm[0] = 1;
                    } else {
                        dmon2.setBackgroundResource(R.drawable.table_inside);
                        dmon2.setTextColor(Color.parseColor("#000000"));
                        pm[0] = 0;
                    }
                }
            }
        });
        dtues2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tdtues2.length()>0) {
                    c2++;
                    if (c2 % 2 != 0) {
                        dtues2.setBackgroundResource(R.drawable.table_inside_blue);
                        dtues2.setTextColor(Color.parseColor("#4946E7"));
                        pm[1] = 1;
                    } else {
                        dtues2.setBackgroundResource(R.drawable.table_inside);
                        dtues2.setTextColor(Color.parseColor("#000000"));
                        pm[1] = 0;
                    }
                }
            }
        });
        dwen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tdwen2.length()>0) {
                    c3++;
                    if (c3 % 2 != 0) {
                        dwen2.setBackgroundResource(R.drawable.table_inside_blue);
                        dwen2.setTextColor(Color.parseColor("#4946E7"));
                        pm[2] = 1;
                    } else {
                        dwen2.setBackgroundResource(R.drawable.table_inside);
                        dwen2.setTextColor(Color.parseColor("#000000"));
                        pm[2] = 0;
                    }
                }
            }
        });
        dthurs2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tdthurs2.length()>0) {
                    c4++;
                    if (c4 % 2 != 0) {
                        dthurs2.setBackgroundResource(R.drawable.table_inside_blue);
                        dthurs2.setTextColor(Color.parseColor("#4946E7"));
                        pm[3] = 1;
                    } else {
                        dthurs2.setBackgroundResource(R.drawable.table_inside);
                        dthurs2.setTextColor(Color.parseColor("#000000"));
                        pm[3] = 0;
                    }
                }
            }
        });
        dfri2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tdfri2.length()>0) {
                    c5++;
                    if (c5 % 2 != 0) {
                        dfri2.setBackgroundResource(R.drawable.table_inside_blue);
                        dfri2.setTextColor(Color.parseColor("#4946E7"));
                        pm[4] = 1;
                    } else {
                        dfri2.setBackgroundResource(R.drawable.table_inside);
                        dfri2.setTextColor(Color.parseColor("#000000"));
                        pm[4] = 0;
                    }
                }
            }
        });
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //운전자에게 신청 내용 전송
                String dow="";
                for(int i: am){
                    dow+=String.valueOf(i);
                }
                dow+="/";
                for(int i: pm){
                    dow+=String.valueOf(i);
                }
                application(dow);
                adcj.dismiss();
            }
        });
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adcj.dismiss();
            }
        });


    }
    private void createPost(Long id){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitPostContent appInterface = retrofit.create(RetrofitPostContent.class);
        Call<GetContent> call = appInterface.getData(id);
        call.enqueue(new Callback<GetContent>() {
            @Override
            public void onResponse(Call<GetContent> call, Response<GetContent> response) {
                if(response.isSuccessful()){
                    GetContent data = response.body();
                    String gContent = data.getContent();
                    String genderT = data.getGender();
                    String smokeT = data.getSmoke();
                    String petT = data.getPet();
                    String childT = data.getChild();
                    String baggageT = data.getBaggage();
                    String weight = data.getWeight();

                    if(genderT.equals("m")){
                        gender.setText(gender.getText()+"남자만");
                    } else if (genderT.equals("w")) {
                        gender.setText(gender.getText()+"여자만");
                    }else if(genderT.equals("mw")){
                        gender.setText(gender.getText()+"상관없음");
                    }

                    if(smokeT.equals("y")){
                        smoke.setText(smoke.getText()+"흡연");
                    }else if(smokeT.equals("n")){
                        smoke.setText(smoke.getText()+"비흡연");
                    }
                    if(petT.equals("y")){
                        pet.setText(pet.getText()+"가능");
                    }else if(petT.equals("n")){
                        pet.setText(pet.getText()+"불가능");
                    }
                    if(childT.equals("y")){
                        child.setText(child.getText()+"가능");
                    }else if(childT.equals("n")){
                        child.setText(child.getText()+"불가능");
                    }
                    if(baggageT.equals("y")){
                        baggage.setText(baggage.getText()+"가능");
                        Log.d("weight는","weigth : "+weight);
                        if(!weight.equals("x")){
                            baggage.setText(baggage.getText()+"("+weight+"kg)");
                        }
                    }else if(baggageT.equals("n")){
                        baggage.setText(baggage.getText()+"불가능");
                    }


                    price.setText(price.getText()+data.getPrice());
                    times = data.getTime().split(",");
                    if(times.length==2){
                        type.setText(type.getText()+"왕복");
                    }else {
                        type.setText(type.getText()+"편도");
                    }
                    time.setText(time.getText()+data.getTime());
                    people = data.getPeople();
                    String[] peopleArr = String.valueOf(people).split("");
                    int max = 0;
                    for(int i= 0;i<peopleArr.length;i++){
                        if(peopleArr[i].equals("x")){
                            peopleArr[i]="1";
                        }
                        int num = Integer.parseInt(peopleArr[i]);
                        if(max<num){
                            max = num;
                        }

                    }
                    tpeople.setText(String.valueOf(max));
                    Log.d("people","people"+people);
                    dow= data.getDow().split(",");
                    for(String i : dow){
                        if(i.equals("1")){
                            dowT.setText(dowT.getText()+"월 ");
                        }
                        if(i.equals("2")){
                            dowT.setText(dowT.getText()+"화 ");
                        }
                        if(i.equals("3")){
                            dowT.setText(dowT.getText()+"수 ");
                        }
                        if(i.equals("4")){
                            dowT.setText(dowT.getText()+"목 ");
                        }
                        if(i.equals("5")){
                            dowT.setText(dowT.getText()+"금 ");
                        }

                    }


                    for(String a : dow){
                        Log.d("aCheck","pA0"+peopleArr[0]+"pA1"+peopleArr[1]);
                        if(a.equals("1")){
                            for(String k:times){
                                if(k.charAt(0)=='A'){
                                    mon1.setText(peopleArr[0]);
                                    tdmon1=peopleArr[0];
                                }
                                if(k.charAt(0)=='P'){
                                    mon2.setText(peopleArr[0]);
                                   tdmon2=peopleArr[0];
                                }
                            }
                        }
                        if(a.equals("2")){
                            for(String k:times){
                                if(k.charAt(0)=='A'){
                                    tues1.setText(peopleArr[1]);
                                    tdtues1=peopleArr[1];
                                }
                                if(k.charAt(0)=='P'){
                                    tues2.setText(peopleArr[1]);
                                    tdtues2=peopleArr[1];
                                }
                            }
                        }
                        if(a.equals("3")){
                            for(String k:times){
                                if(k.charAt(0)=='A'){
                                    wen1.setText(peopleArr[2]);
                                    tdwen1=peopleArr[2];

                                }
                                if(k.charAt(0)=='P'){
                                    wen2.setText(peopleArr[2]);
                                    tdwen2=peopleArr[2];
                                }
                            }
                        }
                        if(a.equals("4")){
                            for(String k:times){
                                if(k.charAt(0)=='A'){
                                    thurs1.setText(peopleArr[3]);
                                    tdthurs1=peopleArr[3];
                                }
                                if(k.charAt(0)=='P'){
                                    thurs2.setText(peopleArr[3]);
                                    tdthurs2=peopleArr[3];
                                }
                            }
                        }
                        if(a.equals("5")){
                            for(String k:times){
                                if(k.charAt(0)=='A'){
                                    fri1.setText(peopleArr[4]);
                                    tdfri1=peopleArr[4];
                                }
                                if(k.charAt(0)=='P'){
                                    fri2.setText(peopleArr[4]);
                                   tdfri2=peopleArr[4];
                                }
                            }
                        }
                    }

                    content.setText(gContent);

                }
            }

            @Override
            public void onFailure(Call<GetContent> call, Throwable t) {
                Log.d("errorResult","이유:"+t );
            }
        });
    }
    ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Log.i("테스트", "onActivityResult");

                if (result.getResultCode() == RESULT_OK) {
                    Intent intent = result.getData();
                    if (intent != null) {
                        //Log.i("test실패", "data:" + data);
                        //start.setText(data);
                    }
                }
            }
    );
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
                    Intent intent2 = new Intent(PostIntoActivity.this,OtherUserInfoActivity.class);
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

    private void application(String dow) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitPostListInterface service = retrofit.create(RetrofitPostListInterface.class);
        App app = new App(dow,member.getId(),sid);
        Call<App> call = service.app(app);
        call.enqueue(new Callback<App>() {
            @Override
            public void onResponse(Call<App> call, Response<App> response) {
                if (response.isSuccessful()) {
                    App data = response.body();
                    Log.d("application","result:"+data);
                }
            }
            @Override
            public void onFailure(Call<App> call, Throwable t) {
                Log.d("errorResult","이유:"+t );
            }
        });
    }
}
