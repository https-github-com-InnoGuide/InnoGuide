package com.example.webapplicationwithspring;

import android.Manifest;
import android.app.Activity;
import android.app.AppComponentFactory;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MapActivity  extends AppCompatActivity implements OnMapReadyCallback{

    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionGranted = false;
    private static final int L_PERMISSION_REQUEST_CODE = 1234;

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
        new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFrag = null;
                // define the logic to switch between the fragments in navigation bar
                switch (item.getItemId()){
                    case R.id.nav_events: {
                        Toast.makeText(getApplicationContext(), "You are going to open events fragment", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.nav_profile:{
                        Toast.makeText(getApplicationContext(), "You are going to open profile fragment", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.nav_map:{
                        Toast.makeText(getApplicationContext(), "You are going to open the Map", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                return true;
            }
    };
    private GoogleMap gMap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getLocationPermission();
        BottomNavigationView nView = findViewById(R.id.bottom_navigation);
        nView.setOnNavigationItemSelectedListener(navListener);
    }

    private void initMap(){
        Log.d(TAG, "initMAp : initialization");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocation permission : gettionf location permissions");
        String[] permissions  = {Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                mLocationPermissionGranted = true;
            else{
                ActivityCompat.requestPermissions(this, permissions, L_PERMISSION_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionResult : called");
        mLocationPermissionGranted = false;
        switch (requestCode){
            case L_PERMISSION_REQUEST_CODE: {
                if(grantResults.length>0){
                    for(int i =0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionGranted = false;
                            Log.d(TAG, "onRequestPermissionResult : remission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionResult : remission fgranted");
                    mLocationPermissionGranted = true;
                    initMap();
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "On map: MAP is ready here");
        gMap = googleMap;
    }
}
