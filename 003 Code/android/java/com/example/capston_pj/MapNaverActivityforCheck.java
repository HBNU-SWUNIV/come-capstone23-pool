package com.example.capston_pj;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MapNaverActivityforCheck extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_naver);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        LocationEnrollFragmentforCheck myFragment = new LocationEnrollFragmentforCheck();
        ft.add(R.id.fragment_container,myFragment);
        ft.commit();

    }
}
