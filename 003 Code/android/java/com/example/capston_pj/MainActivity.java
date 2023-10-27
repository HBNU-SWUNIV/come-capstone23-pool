package com.example.capston_pj;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capston_pj.models.AlarmData;
import com.example.capston_pj.models.Member;
import com.example.capston_pj.models.MemberId;
import com.example.capston_pj.models.UpdateParam;
import com.example.capston_pj.models.post.GetPosting2;
import com.example.capston_pj.models.post.Posting;
import com.example.capston_pj.retrofitInterface.RetrofitPostContent;
import com.example.capston_pj.retrofitInterface.RetrofitUpdateInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /*private TextView textResult,textList;
    List<Posting> postingData = new ArrayList<>();
    private PostAdapter mAdapter;
    private List<Posting> mdatas;
    private String test;
    private RecyclerView mPostRecyclerView;
    private String name;
*/
    private ArrayList<Posting>arrayList;
    private UserInfoActivity userInfo;
    private DrivingCrewActivity drivingCrew;
    private ChatActivity chat;
    private ListActivity list;
    private Member member;
    private boolean info=false;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        member = (Member)intent.getSerializableExtra("member");
        info = intent.getBooleanExtra("info",false);


        userInfo = new UserInfoActivity();
        chat = new ChatActivity();
        list = new ListActivity();
        drivingCrew = new DrivingCrewActivity();
        context = getApplicationContext();

        if(info==false){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,list).commit();
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,userInfo).commit();
        }

        NavigationBarView navigationBarView = findViewById(R.id.bottomNavi);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.action_list:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,list).commit();
                        return true;
                    case R.id.action_post:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,drivingCrew).commit();
                        return true;
                    case R.id.action_chat:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,chat).commit();
                        return true;
                    case R.id.action_user_info:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,userInfo).commit();
                        return true;
                }
                return false;
            }
        });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if(!task.isSuccessful()){
                            System.out.println("Fetching FCM registration token failed");
                            return;
                        }

                        String token = task.getResult();

                        System.out.println(token);
                        getPosts(token);
                        getSchedule(member.getId());
                    }
                });

    }

    private void getPosts(String token) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitUpdateInterface appInterface = retrofit.create(RetrofitUpdateInterface.class);
        UpdateParam input = new UpdateParam(member.getId(),token);
        Call<UpdateParam> call = appInterface.token(input);
        call.enqueue(new Callback<UpdateParam>() {
            @Override
            public void onResponse(Call<UpdateParam> call, Response<UpdateParam> response) {
                if(response.isSuccessful()){
                    UpdateParam data = response.body();
                    Log.d("##","Successed! , Result" +data.getToken());

                }
            }

            @Override
            public void onFailure(Call<UpdateParam>call, Throwable t) {
                Log.d("bbb","onFailure: "+t.getLocalizedMessage());
            }
        });

    }
    //list["id,time","id,time"....]
    private void getSchedule(long id) {
        Log.d("CrewPostListTest","in");
        Retrofit retrofit = new Retrofit.Builder().baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitPostContent appInterface = retrofit.create(RetrofitPostContent.class);
        MemberId memberId = new MemberId(id);
        Call<GetPosting2> call = appInterface.getSchedule(memberId);
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

                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                    for (Posting i : arrayList){
                        String dow = i.getDow();
                        String time = i.getTimes();
                        String start = i.getStart();
                        String dows[] =  dow.split(",");
                        String times[] = time.split(",");

                        for(int j = 0; j < dows.length; j++){
                            for(int k = 0 ; k<times.length;k++){
                                int time1,time2;
                                if(times[k].startsWith("A")){
                                    time1 = Integer.parseInt(times[k].substring(2,4),10);
                                }else {
                                    time1 = Integer.parseInt(times[k].substring(2,4),10)+12;
                                }
                                if(times[k].length()==8){
                                    time2 = Integer.parseInt(times[k].substring(5,6),10);
                                }else {
                                    time2= 0;
                                }
                                Log.d("시간확인","dow: "+dows[j]+"time1: "+time1+" / time2: "+time2+" / 알람id: "+i.getId().intValue());
                                AlarmData aData = new AlarmData(i.getId(),Integer.parseInt(dows[j])+1,time1,time2);
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.DAY_OF_WEEK, aData.getDayOfWeek()); // 원하는 요일 설정
                                calendar.set(Calendar.HOUR_OF_DAY, aData.getHourOfDay()); // 원하는 시간 설정
                                calendar.set(Calendar.MINUTE, aData.getMinute());

                                Intent intent2 = new Intent(context, AlarmReceiver.class);
                                intent2.putExtra("alarmId",i.getId().intValue());
                                intent2.putExtra("start",start);
                                intent2.putExtra("memberId",id);

                                PendingIntent pendingIntent = PendingIntent.getBroadcast(context,i.getId().intValue(), intent2, PendingIntent.FLAG_MUTABLE);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                                } else {
                                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                                }
                            }
                        }

                    }
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