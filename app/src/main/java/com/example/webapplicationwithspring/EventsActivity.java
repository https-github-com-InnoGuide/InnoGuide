package com.example.webapplicationwithspring;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventsActivity extends AppCompatActivity {
    public  TextView eventData;
    private ScrollView scrollView;
// url for local server
    private String url = "http://192.168.0.119:8080/eventsList";
// this will switch between items in the navigation bar
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    /* define the logic to switch between the fragments in navigation bar */
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
                            // when we meet new item to be selected we switch to new activity
                            Toast.makeText(getApplicationContext(), "You are already in the Map", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MapActivity.class));
                            overridePendingTransition(0,0);
                            break;
                        }
                    }
                    return true;
                }
            };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        eventData =  findViewById(R.id.event_data);
        scrollView =  findViewById(R.id.scroll_view);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final TextView tv = new TextView(this);
        tv.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT));
        JsonArrayRequest objectRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jo = (JSONObject) response.get(i);
                                String eventName = jo.getString("eventName");
                                String eventDate = jo.getString("eventDate");
                                String eventDescription = jo.getString("eventDescription");
                                String eventPlace = jo.getString("eventPlace");
                                eventData.append(eventName + "  at  " + eventDate + "  there will be  " + eventDescription + " in " + eventPlace);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EventsActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("EventSSS",error.toString());
                    }
                }
        );
        requestQueue.add(objectRequest);
// set the bottom navigation bar to switch between activities
        BottomNavigationView nView = findViewById(R.id.bottom_navigation_for_events);
        nView.setOnNavigationItemSelectedListener(navListener);
        nView.setSelectedItemId(R.id.nav_events);
    }
}
