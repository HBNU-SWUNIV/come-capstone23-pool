package com.example.capston_pj;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.capston_pj.adapter.ChattingRoomAdapter;
import com.example.capston_pj.models.ChattingRoomCode;
import com.example.capston_pj.models.Chattings;
import com.example.capston_pj.models.Member;
import com.example.capston_pj.models.MemberId;
import com.example.capston_pj.models.chattingRoom.Code;
import com.example.capston_pj.models.chattingRoom.GetRecord;
import com.example.capston_pj.models.chattingRoom.Record;
import com.example.capston_pj.retrofitInterface.RetrofitRoomListInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChattingRoomActivity extends AppCompatActivity {

private ChattingRoomAdapter chattingRoomAdapter;
private RecyclerView recyclerView;
private LinearLayoutManager linearLayoutManager;

private ImageView profile;

private Button btn;
private EditText edit;
private TextView senderT;
private Member member;
private String roomId;
private long senderId;

private ArrayList<Record> records = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_room);
        Intent intent = getIntent();
        member = (Member)intent.getSerializableExtra("member");
        ChattingRoomCode roomCode = (ChattingRoomCode)intent.getSerializableExtra("roomCode");

        Log.d("memberInfoInChatroom","memberName ="+member.getName());

        recyclerView = (RecyclerView) findViewById(R.id.chatting_room_rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        chattingRoomAdapter = new ChattingRoomAdapter(records,member);
        recyclerView.setAdapter(chattingRoomAdapter);
        btn = (Button) findViewById(R.id.chatting_room_btn);
        edit =(EditText)findViewById(R.id.chatting_room_edit);
        profile = (ImageView)findViewById(R.id.item_chat_profile);
        senderT = (TextView)findViewById(R.id.item_chat_name);
        String imageUrl = "url"+String.valueOf(senderId);
        loadProfileImage(imageUrl,profile);


        Chattings enter = new Chattings(member.getName(),"","");
      /*  arrayList.add(enter);
        chattingRoomAdapter.notifyDataSetChanged();*/
        socket("ENTER",enter,roomCode.getRoomId());
        Log.d("roomIdCheck","roomId ="+roomCode.getRoomId());
        Log.d("Membercheck","roomMember ="+member.getName());
        roomId = roomCode.getRoomId();

        getRoomRecord(roomId);



        recyclerView.scrollToPosition(records.size()-1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Chattings chatting = new Chattings(member.getName(),edit.getText().toString(),"시간");
               socket("TALK",chatting,roomCode.getRoomId());
                edit.setText(null);
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

    private void socket(String status,Chattings chatting,String roomCode) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        WebSocketListener webSocketListener = new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);

                // WebSocket 연결이 성공하면 JSON 데이터를 보냅니다.
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("type", status);
                    jsonObject.put("roomId", roomCode);
                    jsonObject.put("sender", chatting.getName());
                    jsonObject.put("message", chatting.getText());
                    String message = jsonObject.toString();

                    webSocket.send(message);

                    Log.d("chatCheck","check");
                    //arrayList.add(chatting);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
                runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                      /*  long now = System.currentTimeMillis();
                        Date date = new Date(now);
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                        String getTime = sdf.format(date);*/
                        try {
                            JSONObject jsonObject = new JSONObject(text);
                            String message = "";
                            String sender = "";
                            message = jsonObject.getString("message");
                            sender = jsonObject.getString("sender");
                            Record getChattings;
                            if(sender.equals(member.getName())){
                                getChattings= new Record(sender,message, Code.ViewType.RIGHT_CONTENT);
                            }else {
                                getChattings= new Record(sender,message, Code.ViewType.LEFT_CONTENT);

                            }

                            records.add(getChattings);
                            recyclerView.scrollToPosition(records.size()-1);
                            chattingRoomAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                    }
                });
                // 서버에서 메시지를 수신한 경우 처리합니다.
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                // WebSocket 연결이 실패한 경우 처리합니다.
                Log.d("chattingFail","onFailure: "+t.getLocalizedMessage());
            }
        };

        Request request = new Request.Builder().url("url").build();
        WebSocket webSocket = client.newWebSocket(request, webSocketListener);
    }
    private void getRoomRecord(String roomId) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitRoomListInterface appInterface = retrofit.create(RetrofitRoomListInterface.class);
        MemberId room = new MemberId(roomId);
        Call<GetRecord> call = appInterface.getRoom(room);
        call.enqueue(new Callback<GetRecord>() {
            @Override
            public void onResponse(Call<GetRecord> call, retrofit2.Response<GetRecord> response) {
                if(response.isSuccessful()){
                    GetRecord data = response.body();
                    Log.d("record roomId","roomId:"+roomId);

                    for(Record i : data.item){
                        Log.d("recordItem","sender:"+i.getSender()+"message:"+i.getMessage());
                        records.add(i);
                    }
                    /*if(records.size()>0){
                        String last = records.get(records.size()-1).getMessage();
                        if(last.length()<=15){
                            holder.lastMessage.setText(last);
                        } else if (last.length()>15) {
                            holder.lastMessage.setText(last.substring(0,15)+"...");
                        }

                    }*/
                    for(int i = 0; i<records.size();i++){
                        Log.d("senderCheck","sender ="+records.get(i).getSender().equals(member.getName()));
                        if(records.get(i).getSender().equals(member.getName())){
                            continue;
                        }else {
                            senderT.setText(records.get(i).getSender());
                            break;
                        }
                    }

                }
            }
            @Override
            public void onFailure(Call<GetRecord>call, Throwable t) {
                Log.d("bbb","onFailure: "+t.getLocalizedMessage());
            }
        });
    }
}

