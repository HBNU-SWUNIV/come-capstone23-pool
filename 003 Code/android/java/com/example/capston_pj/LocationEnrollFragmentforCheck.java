package com.example.capston_pj;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.List;

public class LocationEnrollFragmentforCheck extends Fragment implements OnMapReadyCallback {
    public LocationEnrollFragmentforCheck(){

    }
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationProviderClient fusedLocationClient;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;
    private View view;

    Context context;
    private Button btn,btn2;
    private String[] PERMISSIONS = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private double lat,lng;

    private Location currentLocation;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.activity_mapsforcheck, container, false);
        context = getActivity();
        btn2 = (Button) view.findViewById(R.id.permission);

        Intent intent = getActivity().getIntent();
        lat = Double.parseDouble(intent.getStringExtra("lat"));
        lng = Double.parseDouble(intent.getStringExtra("lng"));
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
                lat,lng
        ));
        marker.setIcon(OverlayImage.fromResource(R.drawable.marker_icon));
        marker.setMap(naverMap);


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

                CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(lat,lng));
                naverMap.moveCamera(cameraUpdate);

                marker.setPosition(new LatLng(lat,lng));
            }

        });

    }

}
