package com.example.webapplicationwithspring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PlacesActivity extends AppCompatActivity {

    private ListView listView;

    EventsActivity.MyAdapter adapter;
    FragmentManager fragmentManager = getFragmentManager();

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    /* define the logic to switch between the fragments in navigation bar */
                    switch (item.getItemId()) {
                        case R.id.nav_events: {
                            Toast.makeText(getApplicationContext(), "You are going to open events fragment", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case R.id.nav_profile: {

                            Toast.makeText(getApplicationContext(), "You are going to open profile fragment", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case R.id.nav_map: {
                            // when we meet new item to be selected we switch to new activity
                            Toast.makeText(getApplicationContext(), "You are already in the Map", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MapActivity.class));
                            overridePendingTransition(0, 0);
                            break;
                        }
                    }
                    return true;
                }
            };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);




        BottomNavigationView nView = findViewById(R.id.bottom_navigation_for_events);
        nView.setOnNavigationItemSelectedListener(navListener);
        nView.setSelectedItemId(R.id.nav_events);
    }
}
