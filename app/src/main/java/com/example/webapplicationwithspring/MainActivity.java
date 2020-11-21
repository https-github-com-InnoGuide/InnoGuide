package com.example.webapplicationwithspring;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.webapplicationwithspring.Events.EventsController;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.GoogleMap;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private String url = "http://192.168.0.119:8080/auth/login";

    GoogleMap map;
    private static final String TAG = "MainActivity";
    private static final int ERROR = 9001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.login_Email);
        passwordEditText = findViewById(R.id.login_Password);

        if(isServicesOK()){
            init();
        }
    }

    private void init(){
        TextView text_signIn = findViewById(R.id.sing_in);
        text_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if(email.equals("") || password.equals("")){
                    Toast.makeText(getApplicationContext(), "Fill both email and password", Toast.LENGTH_SHORT).show();
                }else{
                    // initialize the ArrayRequest and set value to the EventContoller Map
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    final JSONObject request = new JSONObject();
                    try {
                        request.put("email", email);
                        request.put("password", password);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonObjectRequest objectRequest = new JsonObjectRequest(
                            Request.Method.POST,
                            url,
                            request,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        Token.token = response.getString("token");
                                        Person.setToken(Token.token);
                                        Person.setFirstName(response.getString("firstName"));
                                        Person.setLastName(response.getString("lastName"));
                                        Person.setEmail(response.getString("email"));
                                        Person.setGender(response.getString("gender"));
                                        Intent intent = new Intent(MainActivity.this, MapActivity.class);
                                        startActivity(intent);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), "Server does not response", Toast.LENGTH_SHORT).show();
                                    Log.d("EventSSS", error.toString());
                                }
                            }
                    );
                    requestQueue.add(objectRequest);
                }
            }
        });

        TextView text_signUp = findViewById(R.id.sing_up);
        text_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
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
            Log.d(TAG, "error occured");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR);
            dialog.show();
        } else {
            Toast.makeText(this, "You cant make a request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
