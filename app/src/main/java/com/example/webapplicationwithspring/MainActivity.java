package com.example.webapplicationwithspring;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.GoogleMap;

public class MainActivity extends AppCompatActivity {

 //   private EditText emailEditText = findViewById(R.id.login_Email);
//    private EditText passwordEditText = findViewById(R.id.login_Password);

    GoogleMap map;
    private static final String TAG = "MainActivity";
    private static final int ERROR = 9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(isServicesOK()){
            init();
        }
    }

    private void init(){
        Button buttonMap = (Button) findViewById(R.id.btnMap);
        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        //        String email = emailEditText.getText().toString();
         //       String password = passwordEditText.getText().toString();

                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

    }


    private boolean isServicesOK(){
        Log.d(TAG,"isServiceOK: check google services version" );

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
        if(available == ConnectionResult.SUCCESS){
            Log.d(TAG,"isServiceOK: Services works ok");
            return true;
        } else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "error occurred");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR);
            dialog.show();
        } else {
            Toast.makeText(this, "You cant make a request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
