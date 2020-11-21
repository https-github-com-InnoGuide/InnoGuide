package com.example.webapplicationwithspring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {
    TextView firstName;
    TextView lastName;
    TextView email;
    TextView changePassword;
    TextView logout;

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
                        case R.id.nav_suggestions: {
                            startActivity(new Intent(getApplicationContext(), PlacesActivity.class));
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
        setContentView(R.layout.activity_profile);
        firstName = findViewById(R.id.profile_firstName);
        lastName = findViewById(R.id.profile_lastName);
        email = findViewById(R.id.profile_email);
        changePassword = findViewById(R.id.profile_changePassword);
        logout = findViewById(R.id.profile_logout);
        firstName.setText(Person.getFirstName());
        lastName.setText(Person.getLastName());
        email.setText(Person.getEmail());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person.deleteAll();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(0, 0);
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ChangePassActivity.class));
            }
        });

        BottomNavigationView nView = findViewById(R.id.profile_bottom_navigation);
        nView.setOnNavigationItemSelectedListener(navListener);
        nView.setSelectedItemId(R.id.nav_profile);
    }


}
