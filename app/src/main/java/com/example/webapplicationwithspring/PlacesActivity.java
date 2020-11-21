package com.example.webapplicationwithspring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.webapplicationwithspring.Events.Place;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class PlacesActivity extends AppCompatActivity {

// viewPager will be initialized with adapter
    ViewPager viewPager;
// class Adapter is created to populate viewPager with different places
    Adapter adapter;
// tools to change the background color
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
// my List of places
    List<Place> places;

    public static int position = -1;


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    /* define the logic to switch between the fragments in navigation bar */
                    switch (item.getItemId()) {
                        case R.id.nav_events: {
                            startActivity(new Intent(getApplicationContext(), EventsActivity.class));
                            overridePendingTransition(0, 0);
                            Toast.makeText(getApplicationContext(), "You are going to open events fragment", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        case R.id.nav_profile: {
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            overridePendingTransition(0,0);
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




// places initialization
        places = new ArrayList<Place>(){
            {
                add(new Place("Bar 108", "55.74852740679568, 48.74187662098441", "Good place to sit with your friends and drink beer",
                        "13:00 - 02:00", "Sportivnaya street 108" , "@bar108", "+7(843)514-81-08", 0));
                add(new Place("Sport complex", "55.75149071098209, 48.74229488800128", "Good place for sport activities",
                        "07:00 - 23:00", "Sportivnaya street 107", "@sportInno", "+7(937)296-86-85", 1));
                add(new Place("Technopark", "55.75196407745037, 48.75216375438738", "Here are located modern buildings of business centers, specially designed to provide residents with the most comfortable working conditions.",
                        "10:00-20:00", "University Street 7 ", "@technopark", "+7(843)200-07-01", 2));
                add(new Place("Innopolis University", "55.753562696292434, 48.74349062437596", "Russian autonomous non-profit organization of highest education. It specializes in education, research and development in information technology and robotics.",
                        "08:00-21:00", "University Street 1", "@InnUni", "+7(843)203-92-53", 3));

            }
        };
// adapter initialization with initail places
        adapter = new Adapter(places,this);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130, 0 , 130 , 0);
// array of colors to change the background color
        Integer[] colors_temp = {
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color4)
        };

        colors = colors_temp;

        viewPager.setCurrentItem(2);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position < (adapter.getCount() - 1) && position < (colors.length - 1)){
                    viewPager.setBackgroundColor((Integer) argbEvaluator.evaluate(positionOffset , colors[position] , colors[position + 1]));
                } else
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //if(position != -1){
          //  viewPager.setCurrentItem();
        //}

        BottomNavigationView nView = findViewById(R.id.places_bottom_navigation);
        nView.setOnNavigationItemSelectedListener(navListener);
        nView.setSelectedItemId(R.id.nav_suggestions);
    }
}
