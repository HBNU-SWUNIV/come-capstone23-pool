package com.example.capston_pj;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capston_pj.adapter.PostAdapter;
import com.example.capston_pj.models.Car;
import com.example.capston_pj.models.Member;
import com.example.capston_pj.models.post.GetPosting;
import com.example.capston_pj.models.post.Posting;
import com.example.capston_pj.retrofitInterface.RetrofitMacInterface;
import com.example.capston_pj.retrofitInterface.RetrofitPostListInterface;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListActivity extends Fragment {

    private EditText start,end,time;
    private TextView textResult, total;
    List<Posting> postingData = new ArrayList<>();
    private PostAdapter mAdapter;
    private List<Posting> mdatas;
    private String test;
    private RecyclerView mPostRecyclerView;
    private String name;
    private View view , searchView,opView;
    public String cUsername;
    Button editBtn,drawer,gender,smoke,pet,child,baggage,mon,tues,wen,thurs,fri;
    ImageView indi;
    private CheckBox homeCheck;
    boolean home = true;
    private boolean flag = false;
    private boolean opi = false;
    private boolean opg = false;
    private boolean ops = false;
    private boolean opp = false;
    private boolean opc = false;
    private boolean opb = false;

    private boolean mf = false;
    private boolean tf = false;
    private boolean thf = false;
    private boolean wf = false;
    private boolean ff = false;

    private ArrayList<Integer>dowArr = new ArrayList<>();
    private String dowC = null;
    private String genderC = null;
    private String smokeC = null;
    private String petC = null;
    private String childC = null;
    private String baggageC = null;

    private Member member;
    private Context mContext;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_list,container,false);
        textResult = (TextView) view.findViewById(R.id.textResult);
        start = (EditText)view.findViewById(R.id.main_search_start);
        end = (EditText)view.findViewById(R.id.main_search_end);
        editBtn = view.findViewById(R.id.main_post_edit);
        Intent intent = getActivity().getIntent();
        member = (Member)intent.getSerializableExtra("member");
        Log.d("memberTest","memberName:"+member.getName());
        textResult.setText("반갑습니다"+member.getName()+"님");
        cUsername = member.getName();
        searchView = view.findViewById(R.id.search_view);
        mPostRecyclerView= view.findViewById(R.id.recycler);
        drawer = view.findViewById(R.id.drawer_Btn);
        indi = view.findViewById(R.id.main_indicator);
        opView = view.findViewById(R.id.option_view);
        total = view.findViewById(R.id.total);
        mon = view.findViewById(R.id.mon);
        tues = view.findViewById(R.id.tues);
        wen = view.findViewById(R.id.wen);
        thurs = view.findViewById(R.id.thurs);
        fri = view.findViewById(R.id.fri);
        homeCheck = view.findViewById(R.id.homeCheck);
        gender = view.findViewById(R.id.option_gender);
        smoke = view.findViewById(R.id.option_smoke);
        pet = view.findViewById(R.id.option_pet);
        child = view.findViewById(R.id.option_child);
        baggage = view.findViewById(R.id.option_baggage);
        Log.d("memberHome","home : "+member.getHome());
        homeCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(home==true){
                    home = false;
                    getPosts(member,null,null,null,null,null,null,null,null,null);
                } else if (home==false) {
                    home = true;
                    getPosts(member,member.getHome(),null,null,null,null,null,null,null,null);
                }
            }
        });

        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mf == false) {
                    mf =true;
                    mon.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    mon.setTextColor(Color.parseColor("#4946E7"));

                } else {
                    mf =false;
                    mon.setBackgroundResource(R.drawable.round_corner);
                    mon.setTextColor(Color.parseColor("#666666"));

                }
            }
        });  tues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tf == false) {
                    tf =true;
                    tues.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    tues.setTextColor(Color.parseColor("#4946E7"));

                } else {
                    tf =false;
                    tues.setBackgroundResource(R.drawable.round_corner);
                    tues.setTextColor(Color.parseColor("#666666"));

                }
            }
        });
        wen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wf == false) {
                    wf =true;
                    wen.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    wen.setTextColor(Color.parseColor("#4946E7"));

                } else {
                    wf =false;
                    wen.setBackgroundResource(R.drawable.round_corner);
                    wen.setTextColor(Color.parseColor("#666666"));

                }
            }
        });
        thurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (thf == false) {
                    thf =true;
                    thurs.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    thurs.setTextColor(Color.parseColor("#4946E7"));

                } else {
                    thf =false;
                    thurs.setBackgroundResource(R.drawable.round_corner);
                    thurs.setTextColor(Color.parseColor("#666666"));

                }
            }
        });
        fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ff == false) {
                    ff =true;
                    fri.setBackgroundResource(R.drawable.round_corner_stroke_blue2);
                    fri.setTextColor(Color.parseColor("#4946E7"));

                } else {
                    ff =false;
                    fri.setBackgroundResource(R.drawable.round_corner);
                    fri.setTextColor(Color.parseColor("#666666"));

                }
            }
        });


        drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == false){
                    flag =true;
                    searchView.setVisibility(View.GONE);
                } else {
                    flag=false ;
                    searchView.setVisibility(View.VISIBLE);
                }
            }
        });
        indi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (opi == false) {
                    opi =true;
                    opView.setVisibility(View.VISIBLE);
                    indi.setBackgroundResource(R.drawable.icon_reverse_expend);

                } else {
                    opi =false;
                    opView.setVisibility(View.GONE);
                    indi.setBackgroundResource(R.drawable.icon_expand);
                }
            }
        });
        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (opg == false) {
                    opg =true;
                    gender.setBackgroundResource(R.drawable.main_option2);
                    gender.setTextColor(Color.parseColor("#4946E7"));
                } else {
                    opg =false;
                    gender.setBackgroundResource(R.drawable.main_option1);
                    gender.setTextColor(Color.parseColor("#666666"));
                }
            }
        });
        smoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ops == false) {
                    ops =true;
                    smoke.setBackgroundResource(R.drawable.main_option2);
                    smoke.setTextColor(Color.parseColor("#4946E7"));
                } else {
                    ops =false;
                    smoke.setBackgroundResource(R.drawable.main_option1);
                    smoke.setTextColor(Color.parseColor("#666666"));
                }
            }
        });
        pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (opp == false) {
                    opp =true;
                    pet.setBackgroundResource(R.drawable.main_option2);
                    pet.setTextColor(Color.parseColor("#4946E7"));
                } else {
                    opp =false;
                    pet.setBackgroundResource(R.drawable.main_option1);
                    pet.setTextColor(Color.parseColor("#666666"));
                }
            }
        });
        child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (opc == false) {
                    opc =true;
                    child.setBackgroundResource(R.drawable.main_option2);
                    child.setTextColor(Color.parseColor("#4946E7"));
                } else {
                    opc =false;
                    child.setBackgroundResource(R.drawable.main_option1);
                    child.setTextColor(Color.parseColor("#666666"));
                }
            }
        });
        baggage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (opb == false) {
                    opb =true;
                    baggage.setBackgroundResource(R.drawable.main_option2);
                    baggage.setTextColor(Color.parseColor("#4946E7"));
                } else {
                    opb =false;
                    baggage.setBackgroundResource(R.drawable.main_option1);
                    baggage.setTextColor(Color.parseColor("#666666"));
                }
            }
        });


        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("clickCheck","click ");

                getcar(member.getId());

            }
        });
        view.findViewById(R.id.main_post_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dowArr.clear();
                dowC = null;
                if(mf==true){
                    dowArr.add(1);
                }
                if(tf==true){
                    dowArr.add(2);
                }
                if(wf ==true){
                    dowArr.add(3);
                }
                if(thf==true){
                    dowArr.add(4);
                }
                if(ff==true){
                    dowArr.add(5);
                }

                dowArr.sort(Comparator.naturalOrder());
                if(dowArr.size()>0){
                    dowC =String.valueOf(dowArr.get(0));
                    for(int a=1;a<dowArr.size();a++){
                        dowC+=","+String.valueOf(dowArr.get(a));
                    }
                }


                //gender 설정해야함
                if(ops==true){
                    smokeC ="y";
                }
                if(opp==true){
                    petC ="y";
                }
                if(opc==true){
                    childC ="y";
                }
                if(opb==true){
                    baggageC ="y";
                }


                String gStart = null;
                String gEnd = null;
                String gTime = null;

                if(start.getText().length()!=0){
                    gStart = start.getText().toString();
                }
                if(end.getText().length()!=0){
                    gEnd = end.getText().toString();
                }
               /* if(time.getText().length()!=0){
                    gTime = time.getText().toString();
                }*/

                getPosts(member,gStart,gEnd,gTime,dowC,genderC,smokeC,petC,childC,baggageC);

            }
        });
        if(homeCheck.isChecked()){
            getPosts(member,member.getHome(),null,null,null,null,null,null,null,null);
        } else if (homeCheck.isChecked()) {
            getPosts(member,null,null,null,null,null,null,null,null,null);
        }
        return view;
    }
    private void getcar(Long id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitMacInterface service = retrofit.create(RetrofitMacInterface.class);
        Car memberId = new Car(id);
        Call<Car> call = service.getCarInfo(memberId);
        call.enqueue(new Callback<Car>() {
            @Override
            public void onResponse(Call<Car> call, Response<Car> response) {
                if (response.isSuccessful()) {
                    Car data = response.body();
                    if(data.getNum().equals("x")){
                        Toast myToast = Toast.makeText(mContext,"차량을 먼저 등록해 주세요\n(회원정보->차량등록하기)", Toast.LENGTH_SHORT);
                        myToast.show();
                    }else {
                        Intent intent = new Intent(getActivity(),PostActivity.class);
                        intent.putExtra("member",member);
                        startActivity(intent);
                    }

                }
            }
            @Override
            public void onFailure(Call<Car> call, Throwable t) {
                Log.d("errorResult","이유:"+t );
            }
        });
    }

    private void getPosts(Member member,String start,String end,String times,String dow,String gender,String smoke,String pet,String child, String baggage ) {
        Log.d("searchTest1","member : " + member.getId() + "start : " + start+ "end : " + end+ "Time : " + times+ "dow : " + dow+ "gender : " + gender+"smoke"+smoke+
                "pet : "+pet+"child : "+child+"baggage : "+baggage);
        Log.d("parameter","start:" + start+"end:"+end+"time:"+times);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitPostListInterface appInterface = retrofit.create(RetrofitPostListInterface.class);
        Call<GetPosting> call = appInterface.getAll(start,end,times,dow,gender,smoke,pet,child,baggage);
        call.enqueue(new Callback<GetPosting>() {
            @Override
            public void onResponse(Call<GetPosting> call, Response<GetPosting> response) {
                if(response.isSuccessful()){
                    GetPosting data = response.body();
                    Log.d("##","Successed! , Result \n" + data.toString());
                    mdatas = new ArrayList<>();
                    for(Posting i:data.item){
                        mdatas.add(i);
                    }
                    total.setText("총 "+String.valueOf(mdatas.size())+"건");
                    mContext = getActivity().getApplicationContext();
                    mAdapter = new PostAdapter(mContext,mdatas, ListActivity.this,member);
                    mPostRecyclerView.setAdapter(mAdapter);

                }
            }

            @Override
            public void onFailure(Call<GetPosting>call, Throwable t) {
                Log.d("bbb","onFailure: "+t.getLocalizedMessage());
            }
        });

    }

}


