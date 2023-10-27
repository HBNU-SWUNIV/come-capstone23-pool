package com.example.capston_pj;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capston_pj.models.Member;
import com.example.capston_pj.models.crew.SaveCrew;
import com.example.capston_pj.models.post.Posting;
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

public class PostActivity extends AppCompatActivity {
    String TAG = "Retrofit";
    private EditText mTitle,mContents,start,end,price,weight;
    private Button mon,tues,wen,thurs,fri,mCheck,aCheck,save,option;
    private Button man,woman,smoke,nonsmoke,pet,nopet,child,nochild,baggage,nobaggage,opOk,dcInfo,rpriceInfo;
    private TextView tgender,tsmoke,tchild,tpet,tbaggage,opNot,rPrice;
    private Spinner people,time1N,time1H,time1M,time2N,time2H,time2M;
    private CheckBox postMode,wCheck;
    private Member member;
    private String peopleNum;
    private String timeR1 = " ";
    private String noonR1=" ";
    private String noonR2=" ";
    private String timeR2 = " ";

    private Button button;
    private LinearLayout hide,opConfirm,w_layout,rPrice_layout;
    private Dialog opd,dcID;

    private boolean c1 =false;
    private boolean c2 =false;
    private boolean c3 =false;
    private boolean c4 =false;
    private boolean c5 =false;
    private boolean c6 =false;
    private boolean c7 =false;



    private int o1,o2,o3,o4,o5,o6,o7,o8,o9,o10;

    String startLocation = null,endLocation= null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        mTitle = findViewById(R.id.post_title_edit);
        mContents = findViewById(R.id.post_contents_edit);
        start = findViewById(R.id.post_edit_start);
        end = findViewById(R.id.post_edit_end);
        mon = findViewById(R.id.mon);
        tues = findViewById(R.id.tuse);
        wen = findViewById(R.id.wen);
        thurs = findViewById(R.id.thurs);
        fri = findViewById(R.id.fri);
        save = findViewById(R.id.post_save_button);
        dcInfo = findViewById(R.id.post_dcInfo);
        rPrice = findViewById(R.id.post_rprice);
        rpriceInfo =findViewById(R.id.post_rprice_info);
        opd = new Dialog(this);
        opd.setContentView(R.layout.dialog_option);

        dcID = new Dialog(this);
        dcID.setContentView(R.layout.dialog_rotation_info);

        people = (Spinner) findViewById(R.id.post_spinner_people);
        time1N = (Spinner) findViewById(R.id.time1_noon);
        time1H = (Spinner) findViewById(R.id.time1_hour);
        time1M = (Spinner) findViewById(R.id.time1_min);
        time2N = (Spinner) findViewById(R.id.time2_noon);
        time2H = (Spinner) findViewById(R.id.time2_hour);
        time2M = (Spinner) findViewById(R.id.time2_min);
        hide = (LinearLayout)findViewById(R.id.hide);
        rPrice_layout =(LinearLayout) findViewById(R.id.post_rprice_lay);
        opConfirm = (LinearLayout)findViewById(R.id.option_confirm);
        option = (Button)findViewById(R.id.post_add_option);
        tgender = (TextView)findViewById(R.id.post_gender);
        tsmoke = (TextView)findViewById(R.id.post_smoke);
        tpet = (TextView)findViewById(R.id.post_pet);
        tchild= (TextView)findViewById(R.id.post_child);
        tbaggage = (TextView)findViewById(R.id.post_baggage);
        opNot = (TextView)findViewById(R.id.option_nothing);
        postMode = (CheckBox)findViewById(R.id.post_driverChange);



        mCheck = findViewById(R.id.morningcheck);
        aCheck = findViewById(R.id.afternooncheck);
        price = (EditText) findViewById(R.id.post_price);

        Intent intent = getIntent();
        member = (Member) intent.getSerializableExtra("member");

        start.setFocusable(false);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostActivity.this,MapNaverActivity.class);
                intent.putExtra("from","PAS");
                startActivityResult.launch(intent);

            }
        });

        end.setFocusable(false);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostActivity.this,MapNaverActivity.class);
                intent.putExtra("from","PAE");
                endActivityResult.launch(intent);
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        rpriceInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("추천 가격 책정 방법").setMessage("200M 당 100원");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Toast.makeText(getApplicationContext(), "OK Click", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        mCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (c1== false) {
                    c1 = true;
                    mCheck.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    mCheck.setTextColor(Color.parseColor("#4946E7"));
                    if(c2==true){
                        c2 =false;
                        aCheck.setBackgroundResource(R.drawable.round_corner);
                        aCheck.setTextColor(Color.parseColor("#666666"));
                        hide.setVisibility(View.GONE);
                    }
                } else if(c1 == true) {
                    c1 = false;
                    mCheck.setBackgroundResource(R.drawable.round_corner);
                    mCheck.setTextColor(Color.parseColor("#666666"));
                }
            }
        });
        aCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (c2==false) {
                    c2 = true;
                    aCheck.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    aCheck.setTextColor(Color.parseColor("#4946E7"));
                    hide.setVisibility(View.VISIBLE);
                    if(c1 == true){
                        c1=false;
                        mCheck.setBackgroundResource(R.drawable.round_corner);
                        mCheck.setTextColor(Color.parseColor("#000000"));
                    }
                } else if(c2 == true){
                    c2 = false;
                    aCheck.setBackgroundResource(R.drawable.round_corner);
                    aCheck.setTextColor(Color.parseColor("#000000"));
                    hide.setVisibility(View.GONE);
                }
            }
        });
        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (c3 ==false) {
                    c3 = true;
                    mon.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    mon.setTextColor(Color.parseColor("#4946E7"));
                } else {
                    c3=false;
                    mon.setBackgroundResource(R.drawable.round_corner);
                    mon.setTextColor(Color.parseColor("#000000"));
                }
            }
        });
        tues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (c4 ==false) {
                    c4 = true;
                    tues.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    tues.setTextColor(Color.parseColor("#4946E7"));
                } else {
                    c4 =false;
                    tues.setBackgroundResource(R.drawable.round_corner);
                    tues.setTextColor(Color.parseColor("#000000"));
                }
            }
        });
        wen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (c5 ==false) {
                    c5 = true;
                    wen.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    wen.setTextColor(Color.parseColor("#4946E7"));
                } else {
                    c5=false;
                    wen.setBackgroundResource(R.drawable.round_corner);
                    wen.setTextColor(Color.parseColor("#000000"));
                }
            }
        });
        thurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (c6 ==false) {
                    c6 = true;
                    thurs.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    thurs.setTextColor(Color.parseColor("#4946E7"));
                } else {
                    c6=false;
                    thurs.setBackgroundResource(R.drawable.round_corner);
                    thurs.setTextColor(Color.parseColor("#000000"));
                }
            }
        });
        fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (c7 ==false) {
                    c7 = true;
                    fri.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    fri.setTextColor(Color.parseColor("#4946E7"));
                } else {
                    c7 =false;
                    fri.setBackgroundResource(R.drawable.round_corner);
                    fri.setTextColor(Color.parseColor("#000000"));
                }
            }
        });


       time1N.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               String result = adapterView.getItemAtPosition(i).toString();
               if(result.equals("오전")){
                   noonR1 = "AM";
               }else {
                   noonR1 = "PM";
               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });
       time1H.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               timeR1= adapterView.getItemAtPosition(i).toString();
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });
       time1M.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               timeR1 = timeR1+adapterView.getItemAtPosition(i).toString();
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });
        time2N.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String result = adapterView.getItemAtPosition(i).toString();
                if(result.equals("오전")){
                    noonR2 = "AM";
                }else {
                    noonR2 = "PM";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        time2H.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                timeR2= adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        time2M.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                timeR2 = timeR2+adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        people.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                peopleNum = adapterView.getItemAtPosition(i).toString();
                if(peopleNum.equals("1")){
                    peopleNum = "x";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                o1 = 0;
                o2 = 0;
                o3 = 0;
                o4 = 0;
                o5 = 0;
                o6 = 0;
                o7 = 0;
                o8 = 0;
                o9 = 0;
                o10 = 0;

                optionDia();
            }
        });
        dcInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dcIDia();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mode = "normal";
                String time=" , ";
                if(c2==false){
                    time = noonR1+timeR1;
                }else{
                    time = "AM"+timeR1+",PM"+timeR2;
                }

                List<Integer> dowList = new ArrayList<>();
                if(c3==true){
                    dowList.add(1);
                }
                if(c4==true){
                    dowList.add(2);
                }
                if(c5==true){
                    dowList.add(3);
                }
                if(c6==true){
                    dowList.add(4);
                }
                if(c7==true){
                    dowList.add(5);
                }
                dowList.sort(Comparator.naturalOrder());
                String dow =String.valueOf(dowList.get(0));//3

                String [] peopleArr = {"0","0","0","0","0"};
                peopleArr[Integer.parseInt(dow)-1] = peopleNum;//0,0,5,0,0
                if(dowList.size()>1){
                    for(int a=1;a<dowList.size();a++){
                        dow+=","+String.valueOf(dowList.get(a));
                        peopleArr[dowList.get(a)-1] =peopleNum;
                    }
                }

                String peopleNumD="";
                for(int i=0;i<peopleArr.length;i++){
                    Log.i("peopleValue", "peopleNumD"+peopleNumD);
                    peopleNumD += String.valueOf(peopleArr[i]);
                }

                if(postMode.isChecked()){
                    mode = "DC";
                }

                String saveStart = start.getText().toString()+","+startLocation;
                String saveEnd = end.getText().toString()+","+endLocation;

                Log.i("peopleValue2", "peopleNumD"+peopleNumD);
                createPost(member.getId(),member.getName(),mTitle.getText().toString(),saveStart,saveEnd,mContents.getText().toString(),peopleNumD,time,dow,Integer.parseInt(String.valueOf(price.getText())),member.getLoginId(),mode);
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
                        String data = intent.getStringExtra("PAS");
                        startLocation= intent.getStringExtra("PASlocation");
                        if(endLocation!=null){
                            String startLoc[] = startLocation.split(",");
                            String endLoc[] = endLocation.split(",");

                            double distance =distance(Double.parseDouble(startLoc[0]),Double.parseDouble(endLoc[0]),Double.parseDouble(startLoc[1]),Double.parseDouble(endLoc[1]));
                            double getPrice = Math.round(distance * 1000)/200;
                            int  priceResult = (int) (getPrice*100);
                            rPrice_layout.setVisibility(View.VISIBLE);
                            rPrice.setText(rPrice.getText()+String.valueOf(priceResult)+"원");
                        }
                            //Log.i("test실패", "data:" + data);
                            start.setText(data);
                    }
                }
            }
    );
    ActivityResultLauncher<Intent> endActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Log.i("테스트", "onActivityResult");

                if (result.getResultCode() == RESULT_OK) {
                    Intent intent = result.getData();
                    if (intent != null) {
                        String data = intent.getStringExtra("PAE");
                        endLocation = intent.getStringExtra("PAElocation");
                        if(startLocation!=null){
                            String startLoc[] = startLocation.split(",");
                            String endLoc[] = endLocation.split(",");
                            double distance =distance(Double.parseDouble(startLoc[0]),Double.parseDouble(endLoc[0]),Double.parseDouble(startLoc[1]),Double.parseDouble(endLoc[1]));
                            double getPrice = Math.round(distance * 1000)/200;
                            int  priceResult = (int) (getPrice*100);
                            rPrice_layout.setVisibility(View.VISIBLE);
                            rPrice.setText(rPrice.getText()+String.valueOf(priceResult)+"원");
                        }
                        Log.i("불러온 지도값", "data:" + data);
                        //Log.i("test실패", "data:" + data);
                        end.setText(data);
                    }
                }
            }
    );

    private void createPost(Long writerId, String loginId, String name, String start, String end, String content, String people, String times, String dow,int price,String memberId,String mode){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.i("peopleValue", "peopleNumD"+people);
        String gender=" ",smoke=" ",child=" ",baggage=" ",pet=" ";
        if(o1%2!=0&&o2%2!=0){
            gender = "mw";
        }else {
            if (o1 % 2 != 0) {
                gender = "m";
            }
            if (o2 % 2 != 0) {
                gender = "w";
            }
        }

        if(o3%2!=0){
            smoke = "y";
        }
        if(o4%2!=0){
            smoke = "n";
        }
        if(o5%2!=0){
            child = "y";
        }
        if(o6%2!=0){
            child = "n";
        }
        if(o7%2!=0){
            baggage = "y";
        }
        if(o8%2!=0){
            baggage = "n";
        }
        if(o9%2!=0){
            pet = "y";
        }
        if(o10%2!=0){
            pet="n";
        }
        RetrofitPostInterface service = retrofit.create(RetrofitPostInterface.class);
        Log.e(TAG,"버튼클릭");
        String driver = writerId.toString();
        String tWeigth = "x";
        if(weight.getText()!=null){
            tWeigth = weight.getText().toString();
        }
        Posting post = new Posting(writerId,loginId,name,start,end,content,people,times,dow,price,"",gender,smoke,child,baggage,pet,0,0,mode,driver,"x",tWeigth);
        Log.d("insert","writerId : " + writerId + "writer : " + loginId+ "name : " + name+ "start : " + start+ "end : " + end+ "content : " + content+"people"+people+"baggage"+baggage);
        Call<Posting> call = service.createPost(post);
        call.enqueue(new Callback<Posting>() {
            @Override
            public void onResponse(Call<Posting> call, Response<Posting> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                Posting postResponse = response.body();
                Log.i("Response dow", "dow"+postResponse.getDow());
                if(postResponse.getDow().equals("o")){
                    post.setLoginId(postResponse.getLoginId());
                    post.setName(postResponse.getName());
                    post.setContent(postResponse.getContent());

                    Intent backIntent = new Intent(PostActivity.this,MainActivity.class);
                    backIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    backIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    backIntent.putExtra("member",member);
                    startActivity(backIntent);
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "일정이 곂칩니다.", Toast.LENGTH_SHORT);
                    // Toast 메시지 표시
                    toast.show();
                }
                //SaveCrew crew = new SaveCrew(memberId,postResponse.getId(),dow,times);
                // saveCrew(crew);//작성자 크루에 포함
            }

            @Override
            public void onFailure(Call<Posting> call, Throwable t) {

            }
        });
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
                    }else {

                    }
                }
            }
            @Override
            public void onFailure(Call<SaveCrew> call, Throwable t) {
                Log.d("errorResult","이유:"+t );
            }
        });
    }

    private void dcIDia(){
        WindowManager.LayoutParams params = dcID.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dcID.getWindow().setAttributes((android.view.WindowManager.LayoutParams)params);
        dcID.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dcID.show();

        Button cancel = dcID.findViewById(R.id.dcid_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dcID.dismiss();
            }
        });
    }

    private void optionDia(){
        WindowManager.LayoutParams params = opd.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        opd.getWindow().setAttributes((android.view.WindowManager.LayoutParams)params);
        opd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        opd.show();

        man = (Button) opd.findViewById(R.id.op_man);
        woman = (Button) opd.findViewById(R.id.op_woman);
        smoke= (Button) opd.findViewById(R.id.op_smoke);
        nonsmoke = (Button) opd.findViewById(R.id.op_nonsmoke);
        child = (Button) opd.findViewById(R.id.op_child);
        nochild = (Button) opd.findViewById(R.id.op_noChild);
        baggage = (Button) opd.findViewById(R.id.op_baggage);
        nobaggage = (Button) opd.findViewById(R.id.op_noBaggage);
        pet = (Button) opd.findViewById(R.id.op_pet);
        nopet = (Button) opd.findViewById(R.id.op_nopet);
        opOk = (Button) opd.findViewById(R.id.op_ok);
        wCheck = (CheckBox) opd.findViewById(R.id.op_weightCheck);
        w_layout = (LinearLayout) opd.findViewById(R.id.op_weightLayout);
        weight = (EditText) opd.findViewById(R.id.op_weight);
        if(o1==0) {
            man.setBackgroundResource(R.drawable.round_corner);
            man.setTextColor(Color.parseColor("#666666"));
        }if(o2==0){
            woman.setBackgroundResource(R.drawable.round_corner);
            woman.setTextColor(Color.parseColor("#666666"));
        }if(o3==0) {
            smoke.setBackgroundResource(R.drawable.round_corner);
            smoke.setTextColor(Color.parseColor("#666666"));
        }if(o4==0) {
            nonsmoke.setBackgroundResource(R.drawable.round_corner);
            nonsmoke.setTextColor(Color.parseColor("#666666"));
        }if(o9==0) {
            pet.setBackgroundResource(R.drawable.round_corner);
            pet.setTextColor(Color.parseColor("#666666"));
        }if(o10==0) {
            nopet.setBackgroundResource(R.drawable.round_corner);
            nopet.setTextColor(Color.parseColor("#666666"));
        }if(o5==0) {
            child.setBackgroundResource(R.drawable.round_corner);
            child.setTextColor(Color.parseColor("#666666"));
        }if(o6==0) {
            nochild.setBackgroundResource(R.drawable.round_corner);
            nochild.setTextColor(Color.parseColor("#666666"));
        } if(o7==0) {
            baggage.setBackgroundResource(R.drawable.round_corner);
            baggage.setTextColor(Color.parseColor("#666666"));
        } if(o8==0) {
            nobaggage.setBackgroundResource(R.drawable.round_corner);
            nobaggage.setTextColor(Color.parseColor("#666666"));
        }
        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                o1++;
                if (o1 % 2 != 0) {
                    man.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    man.setTextColor(Color.parseColor("#4946E7"));

                }else{
                    man.setBackgroundResource(R.drawable.round_corner);
                    man.setTextColor(Color.parseColor("#000000"));

                }
            }
        });
        woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                o2++;
                if (o2 % 2 != 0) {
                    woman.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    woman.setTextColor(Color.parseColor("#4946E7"));

                }else{
                    woman.setBackgroundResource(R.drawable.round_corner);
                    woman.setTextColor(Color.parseColor("#000000"));

                }
            }
        });
        smoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                o3++;
                if (o3 % 2 != 0) {
                    smoke.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    smoke.setTextColor(Color.parseColor("#4946E7"));
                    if(o4%2!=0){
                        o4++;
                        nonsmoke.setBackgroundResource(R.drawable.round_corner);
                        nonsmoke.setTextColor(Color.parseColor("#666666"));
                    }

                }else{
                    smoke.setBackgroundResource(R.drawable.round_corner);
                    smoke.setTextColor(Color.parseColor("#666666"));

                }
            }
        });
        nonsmoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                o4++;
                if (o4 % 2 != 0) {
                    nonsmoke.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    nonsmoke.setTextColor(Color.parseColor("#4946E7"));
                    if(o3%2!=0){
                        o3++;
                        smoke.setBackgroundResource(R.drawable.round_corner);
                        smoke.setTextColor(Color.parseColor("#666666"));
                    }

                }else{
                    nonsmoke.setBackgroundResource(R.drawable.round_corner);
                    nonsmoke.setTextColor(Color.parseColor("#666666"));

                }
            }
        });
        child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                o5++;
                if (o5 % 2 != 0) {
                    child.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    child.setTextColor(Color.parseColor("#4946E7"));
                    if(o6%2!=0){
                        o6++;
                        nochild.setBackgroundResource(R.drawable.round_corner);
                        nochild.setTextColor(Color.parseColor("#666666"));
                    }

                }else{
                    child.setBackgroundResource(R.drawable.round_corner);
                    child.setTextColor(Color.parseColor("#666666"));

                }
            }
        });
        nochild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                o6++;
                if (o6 % 2 != 0) {
                    nochild.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    nochild.setTextColor(Color.parseColor("#4946E7"));
                    if(o5%2!=0){
                        o5++;
                        child.setBackgroundResource(R.drawable.round_corner);
                        child.setTextColor(Color.parseColor("#666666"));
                    }

                }else{
                    nochild.setBackgroundResource(R.drawable.round_corner);
                    nochild.setTextColor(Color.parseColor("#666666"));

                }
            }
        });
        baggage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                o7++;
                if (o7 % 2 != 0) {
                    baggage.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    baggage.setTextColor(Color.parseColor("#4946E7"));
                    wCheck.setVisibility(View.VISIBLE);
                    wCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            // 체크 상태가 변경될 때 호출되는 메서드입니다.
                            if (isChecked) {
                                w_layout.setVisibility(View.VISIBLE);
                            } else {
                                w_layout.setVisibility(View.GONE);
                            }
                        }
                    });
                    if(o8%2!=0){
                        o8++;
                        nobaggage.setBackgroundResource(R.drawable.round_corner);
                        nobaggage.setTextColor(Color.parseColor("#666666"));
                    }

                }else{
                    baggage.setBackgroundResource(R.drawable.round_corner);
                    baggage.setTextColor(Color.parseColor("#666666"));
                }
            }
        });
        nobaggage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                o8++;
                if (o8 % 2 != 0) {
                    nobaggage.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    nobaggage.setTextColor(Color.parseColor("#4946E7"));
                    wCheck.setChecked(false);
                    wCheck.setVisibility(View.GONE);
                    w_layout.setVisibility(View.GONE);

                    if(o7%2!=0){
                        o7++;
                        baggage.setBackgroundResource(R.drawable.round_corner);
                        baggage.setTextColor(Color.parseColor("#666666"));
                    }

                }else{
                    nobaggage.setBackgroundResource(R.drawable.round_corner);
                    nobaggage.setTextColor(Color.parseColor("#666666"));

                }
            }
        });
        pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                o9++;
                if (o9 % 2 != 0) {
                    pet.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    pet.setTextColor(Color.parseColor("#4946E7"));
                    if(o10%2!=0){
                        o10++;
                        nopet.setBackgroundResource(R.drawable.round_corner);
                        nopet.setTextColor(Color.parseColor("#666666"));
                    }

                }else{
                    pet.setBackgroundResource(R.drawable.round_corner);
                    pet.setTextColor(Color.parseColor("#666666"));

                }
            }
        });
        nopet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                o10++;
                if (o10 % 2 != 0) {
                    nopet.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    nopet.setTextColor(Color.parseColor("#4946E7"));
                    if(o9%2!=0){
                        o9++;
                        pet.setBackgroundResource(R.drawable.round_corner);
                        pet.setTextColor(Color.parseColor("#666666"));
                    }

                }else{
                    nopet.setBackgroundResource(R.drawable.round_corner);
                    nopet.setTextColor(Color.parseColor("#666666"));

                }
            }
        });
        opOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opNot.setVisibility(View.GONE);
                opConfirm.setVisibility(View.VISIBLE);
                if(o1%2!=0&&o2%2!=0){
                    tgender.setText("성별 : 남녀 모두");
                }else {
                    if (o1 % 2 != 0) {
                        tgender.setText("성별 : 남자만");
                    }
                    if (o2 % 2 != 0) {
                        tgender.setText("성별 : 여자만");
                    }
                }

                if(o3%2!=0){
                    tsmoke.setText("흡연여부 : 흡연");
                }
                if(o4%2!=0){
                    tsmoke.setText("흡연여부 : 비흡연");
                }
                if(o5%2!=0){
                    tchild.setText("아이동반 : 가능");
                }
                if(o6%2!=0){
                    tchild.setText("아이동반 : 불가능");
                }
                if(o7%2!=0){
                    tbaggage.setText("탑승자 화물소지 : 가능");
                }
                if(o8%2!=0){
                    tbaggage.setText("탑승자 화물소지 : 불가능");
                }
                if(o9%2!=0){
                    tpet.setText("반려동물동반 : 가능");
                }
                if(o10%2!=0){
                    tpet.setText("반려동물동반 : 불가능");
                }
                opd.dismiss();
            }
        });


    }
    double distance(double lat1, double lat2,double lon1, double lon2){
        double R = 6371;
        double dLat = deg2rad(lat2-lat1);
        double dLon = deg2rad(lon2-lon1);
        double a = Math.sin(dLat/2)*Math.sin(dLat/2)+Math.cos(deg2rad(lat1))* Math.cos(deg2rad(lat2)) * Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = R*c;
        Log.d("distance","dis : "+distance);
        return distance;
    }
    double deg2rad(double deg){
        return deg*(Math.PI/180);
    }
}