package com.example.webapplicationwithspring;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.webapplicationwithspring.Events.Event;
import com.example.webapplicationwithspring.Events.EventsController;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventsActivity extends AppCompatActivity {
    private ListView listView;
    private boolean refreshed = false;
    // adapter and fragment manager initialization, we will need them for
    // displaying the Event data in the list
    // Switching between the fragment when some event is clicked
    MyAdapter adapter;
    FragmentManager fragmentManager = getFragmentManager();
    // url for local server
    private String url = "http://192.168.0.119:8080/eventsList";
    //private String url = "http://172.20.10.12:8080/eventsList";
    // this will switch between items in the navigation bar;
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
                        case R.id.nav_suggestions:{
                            startActivity(new Intent(getApplicationContext(), PlacesActivity.class));
                            overridePendingTransition(0,0);
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // set thr activity to the last saved state
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        //
        listView = findViewById(R.id.list_view);
        listView.setItemsCanFocus(false);
        listView.setClickable(true);
        // When some particular event is clicked in the list the following method is called
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // for debagging
                Toast.makeText(EventsActivity.this, "you clicked item" + position + "long press" + id, Toast.LENGTH_SHORT).show();
                // show details about the fragment, the fragment manager will be called
                showDetails(position);
            }
        });

        // displaying data extracted from the server
        if (EventsController.getSize() != 0 && !refreshed) {
            String[] titles = new String[EventsController.getSize()];
            String[] descriptions = new String[EventsController.getSize()];
            for (Event e : EventsController.getValue()) {
                titles[e.getId()] = e.getEventName();
                descriptions[e.getId()] = e.getEventDescription();
            }
            MyAdapter adapter = new MyAdapter(this, titles, descriptions);
            listView.setAdapter(adapter);
        } else {
            // make a response to server to store the data
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            // initialize the ArrayRequest and set value to the EventContoller Map
            JsonArrayRequest objectRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            List<String> titles = new ArrayList<String>();
                            List<String> descriptions = new ArrayList<String>();
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject jo = (JSONObject) response.get(i);
                                    String eventName = jo.getString("eventName");
                                    String eventDate = jo.getString("eventDate");
                                    String eventDescription = jo.getString("eventDescription");
                                    String eventPlace = jo.getString("eventPlace");
                                    EventsController.addEvent(eventName, eventDate, eventDescription, eventPlace, Integer.parseInt(jo.getString("id")));
                                    titles.add(eventName);
                                    descriptions.add(eventDescription);
                                }
                                adapter = new MyAdapter(EventsActivity.this, titles.toArray(new String[titles.size()]), descriptions.toArray(new String[descriptions.size()]));
                                listView.setAdapter(adapter);
                            } catch (JSONException e) {
                                //View errorView = getTextView(EventsActivity.this, "Server does not response");

                                //listView.addHeaderView(errorView);
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            TextView tv = getTextView(EventsActivity.this, "Server does not response");
                            FrameLayout frameLayout = findViewById(R.id.fragment_container);
                            frameLayout.addView(tv);
                            Log.d("EventSSS", error.toString());
                        }
                    }
            );
            requestQueue.add(objectRequest);
        }


        // On back button click action
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                if(listView.getVisibility() == View.INVISIBLE){
                    listView.setVisibility(View.VISIBLE);
                    fragmentManager.beginTransaction().remove(fragmentManager.findFragmentById(R.id.fragment_container)).commit();
                }
                // Handle the back button event
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);


// set the bottom navigation bar to switch between activities
        BottomNavigationView nView = findViewById(R.id.bottom_navigation_for_events);
        nView.setOnNavigationItemSelectedListener(navListener);
        nView.setSelectedItemId(R.id.nav_events);
    }

    public TextView getTextView(Context context, String text) {
        final TextView tv = new TextView(context);
        tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        tv.setTextSize(30);
        tv.setText(text);
        //final FragmentManager manager = getSupportFragmentManager();
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //manager.beginTransaction().replace(R.id.scroll_layout, new BlankFragment()).commit();
                Toast.makeText(EventsActivity.this, "you have clicked on event", Toast.LENGTH_SHORT).show();
            }
        });
        return tv;

    }


    // when the idem is clicked details have to be shown
    private void showDetails(int index) {
        listView.setItemChecked(index, true);
        EventFragment eventFragment =  EventFragment.newInstance(index);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, eventFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
        listView.setVisibility(View.INVISIBLE);
    }
    // customized array adapter is needed for proper data visualization
    class MyAdapter extends ArrayAdapter<String> {
        Context context;
        String rTitle[];
        String rDescription[];
        //int rImage[];

        public MyAdapter(@NonNull Context context, String[] title, String[] description) {
            super(context, R.layout.row, R.id.text_viewTitle, title);
            this.context = context;
            // events =  eventsCol.toArray(new Event[eventsCol.size()]);
            this.rTitle = title;
            this.rDescription = description;
            // this.rImage = Images;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            TextView titleView = row.findViewById(R.id.text_viewTitle);
            TextView descriptionView = row.findViewById(R.id.text_viewDescription);

            titleView.setText(rTitle[position]);
            descriptionView.setText(rDescription[position]);
            return row;
        }
    }
}
