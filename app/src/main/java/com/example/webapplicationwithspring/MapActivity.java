package com.example.webapplicationwithspring;

import android.Manifest;
import android.content.Intent;
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

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MapActivity  extends AppCompatActivity implements OnMapReadyCallback{
// define tags and permission strings
    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionGranted = false;
    private static final int L_PERMISSION_REQUEST_CODE = 1234;
    // this will switch between items in the navigation bar
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
        new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                // define the logic to switch between the fragments in navigation bar
                switch (item.getItemId()){
                    case R.id.nav_events: {
                        // when the new item was clicked we switch an activity
                        Toast.makeText(getApplicationContext(), "You are going to open events fragment", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), EventsActivity.class));
                        overridePendingTransition(0,0);

                        break;
                    }
                    case R.id.nav_profile:{

                        Toast.makeText(getApplicationContext(), "You are going to open profile fragment", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.nav_map:{
                        Toast.makeText(getApplicationContext(), "You are already in the Map", Toast.LENGTH_SHORT).show();
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
        // map initialization
        getLocationPermission();
        // set the bottom navigation to current item
        BottomNavigationView nView = findViewById(R.id.bottom_navigation);
        nView.setOnNavigationItemSelectedListener(navListener);
        nView.setSelectedItemId(R.id.nav_map);
    }

    private void initMap(){
        Log.d(TAG, "initMAp : initialization");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);
    }
    // get location permission
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
// check for all permissions to be granted
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
// when the map is ready initialize it
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "On map: MAP is ready here");
        gMap = googleMap;
    }
}
