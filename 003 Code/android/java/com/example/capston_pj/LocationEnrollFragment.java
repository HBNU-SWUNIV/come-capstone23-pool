package com.example.capston_pj;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationEnrollFragment extends Fragment implements OnMapReadyCallback {
    public LocationEnrollFragment(){

    }
    private FusedLocationProviderClient fusedLocationClient;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;
    private TextView tx;
    private View view;
    private String from,add;
    Context context;
    private Button btn,btn2;
    private String[] PERMISSIONS = {
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private double lat,lng;

    private Location currentLocation;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.activity_maps, container, false);
        context = getActivity();
        tx = (TextView) view.findViewById(R.id.tv_location);
        btn = (Button) view.findViewById(R.id.btn_confirm);
        btn2 = (Button) view.findViewById(R.id.permission);
        Log.i("viewTest", "tx " +tx.getText());

        Intent intent = getActivity().getIntent();
        from = intent.getStringExtra("from");
        add = intent.getStringExtra("add");

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        // getChildFragmentManager() 메소드를 호출합니다.

        FragmentManager fm = getChildFragmentManager();

        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.naver_map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.naver_map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("btnClick", from);
                if(from.equals("PAS")){
                    Intent intent = new Intent();
                    intent.putExtra("PAS",tx.getText());
                    intent.putExtra("PASlocation",lat+","+lng);
                    requireActivity().setResult(Activity.RESULT_OK,intent);

                    // Fragment 닫기
                    requireActivity().onBackPressed();
                }
                if(from.equals("PAE")){
                    Intent intent = new Intent(getActivity(),PostActivity.class);
                    intent.putExtra("PAE",tx.getText());

                    requireActivity().setResult(Activity.RESULT_OK,intent);
                    intent.putExtra("PAElocation",lat+","+lng);
                    // Fragment 닫기
                    requireActivity().onBackPressed();
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PermissionListener permissionListener = new PermissionListener()
                {
                    @Override
                    public void onPermissionGranted()
                    {
                        Toast.makeText(context, "권한이 허가된 상태입니다", Toast.LENGTH_SHORT).show();
                        Log.e("권한", "권한 허가 상태");
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions)
                    {
                        Toast.makeText(context, "권한이 거부된 상태입니다", Toast.LENGTH_SHORT).show();
                        Log.e("권한", "권한 거부 상태");
                    }
                };

                TedPermission.with(context)
                        .setPermissionListener(permissionListener)
                        .setRationaleMessage("위치 권한이 필요합니다!")
                        .setDeniedMessage("지금 거부하시더라도 '설정 > 권한'에서 다시 권한을 허용하실 수 있습니다")
                        .setPermissions(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                        .check();
            }
        });
        view.findViewById(R.id.fab_tracking).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
                naverMap.moveCamera(cameraUpdate);
            }
        });

        return view;
    }




    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        naverMap.setLocationSource(locationSource);
        Marker marker = new Marker();
        marker.setPosition(new LatLng(
                naverMap.getCameraPosition().target.latitude,
                naverMap.getCameraPosition().target.longitude
        ));
        marker.setIcon(OverlayImage.fromResource(R.drawable.marker_icon));
        marker.setMap(naverMap);

        naverMap.addOnCameraChangeListener((reason, animated) -> {
            Log.i("NaverMap", "카메라 변경 - reson: " + reason + ", animated: " + animated);
            marker.setPosition(new LatLng(
                    naverMap.getCameraPosition().target.latitude,
                    naverMap.getCameraPosition().target.longitude
            ));
            tx.setText("위치 이동중");
            tx.setTextColor(Color.parseColor("#c4c4c4"));
            btn.setClickable(false);
            btn.setText("no");
        });

        naverMap.addOnCameraIdleListener(() -> {
            marker.setPosition(new LatLng(
                    naverMap.getCameraPosition().target.latitude,
                    naverMap.getCameraPosition().target.longitude
            ));
            lat = naverMap.getCameraPosition().target.latitude;
            lng = naverMap.getCameraPosition().target.longitude;
            tx.setText(getAddress(context, lat, lng));
            tx.setTextColor(Color.parseColor("#2d2d2d"));
            btn.setClickable(true);
            btn.setText("yes");
        });
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
        ) {
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                currentLocation = location;
                LocationOverlay locationOverlay = naverMap.getLocationOverlay();
                locationOverlay.setVisible(true);
                locationOverlay.setPosition(new LatLng(
                        currentLocation.getLatitude(),
                        currentLocation.getLongitude()
                ));

                CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
                naverMap.moveCamera(cameraUpdate);

                marker.setPosition(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
            }

        });

    }

    public static String getAddress(Context mContext, double lat, double lng) {
        String nowAddress = "현재 위치를 확인 할 수 없습니다.";
        Geocoder geocoder = new Geocoder(mContext, Locale.KOREA);
        List<Address> address;
        try {
            if (geocoder != null) {
                //세번째 파라미터는 좌표에 대해 주소를 리턴 받는 갯수로
                //한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 최대갯수 설정
                address = geocoder.getFromLocation(lat, lng, 1);

                if (address != null && address.size() > 0) {
                    // 주소 받아오기
                    String currentLocationAddress = address.get(0).getAddressLine(0).toString();
                    nowAddress = currentLocationAddress;
                    Log.i("현재위치 체크", "currentLocationAddress = " + currentLocationAddress);

                }
            }

        } catch (IOException e) {
            Log.i("주소 실패", "fail = ");
            //Toast.makeText(baseContext, "주소를 가져 올 수 없습니다.", Toast.LENGTH_LONG).show();

            e.printStackTrace();
        }
        return nowAddress;
    }

}
