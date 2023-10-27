package com.example.capston_pj;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.capston_pj.models.UpdateParam;
import com.example.capston_pj.retrofitInterface.RetrofitUpdateInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AlarmReceiver extends BroadcastReceiver {
    private int alarmId;
    private String start;

    private Dialog mDia;
    private Long id;

    @Override
    public void onReceive(Context context, Intent intent) {
        //Log.d("알람","id: "+alarmId+" / start: "+start);
        alarmId = intent.getIntExtra("alarmId", -1); // 기본값으로 -1을 사용할 수 있음
        start = intent.getStringExtra("start");
        id = intent.getLongExtra("memberId",0);
        // 알람이 울릴 때 실행될 함수 호출
        executeFunction(context);
    }

    private void executeFunction(Context context) {
        String locate[] = start.split(",");
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        double dLat = Double.parseDouble(locate[1]);
        double dLon = Double.parseDouble(locate[2]);
        mDia = new Dialog(context);
        mDia.setContentView(R.layout.dialog_minus);
        Log.d("알람","id: "+alarmId+" / start: "+start);
        if (context.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            double mLat = loc.getLatitude();
            double mLon = loc.getLongitude();
            Log.d("myLocation","lat : "+mLat+", lon : "+mLon);
            double distance = distance(dLat, mLat, dLon, mLon);
            if(distance>0.005){
                Log.d("minus","out : "+distance);
                //minusDialog();
                minusRetro(id);
            }else {
                Log.d("susses","good : "+distance);
            }


        } else {
            Toast.makeText(context, "위치 권한이 없습니다.", Toast.LENGTH_SHORT).show();
        }

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent cancelIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent cancelPendingIntent = PendingIntent.getBroadcast(context, alarmId, cancelIntent, PendingIntent.FLAG_MUTABLE);
        alarmManager.cancel(cancelPendingIntent);
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

    private void minusDialog(){
        WindowManager.LayoutParams params = mDia.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mDia.getWindow().setAttributes((android.view.WindowManager.LayoutParams)params);
        mDia.show();

        Button okBtn = mDia.findViewById(R.id.minus_ok);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDia.dismiss();
                minusRetro(id);
            }
        });
    }
    private void minusRetro(Long id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("url")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitUpdateInterface service = retrofit.create(RetrofitUpdateInterface.class);
        UpdateParam param = new UpdateParam(id);
        Call<UpdateParam> call = service.minusScore(param);
        call.enqueue(new Callback<UpdateParam>() {
            @Override
            public void onResponse(Call<UpdateParam> call, Response<UpdateParam> response) {
                if (response.isSuccessful()) {
                    UpdateParam data = response.body();

                }
            }
            @Override
            public void onFailure(Call<UpdateParam> call, Throwable t) {
                Log.d("errorResult","이유:"+t );
            }
        });
    }
}
