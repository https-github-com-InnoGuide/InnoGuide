package com.example.webapplicationwithspring;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SignInActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText passwordR;
    private EditText firstName;
    private EditText lastName;
    private CheckBox male;
    private CheckBox female;
    private TextView create;

    private String url = "http://192.168.0.119:8080/auth/signin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        email = findViewById(R.id.sing_in_email);
        password = findViewById(R.id.sing_in_password);
        passwordR = findViewById(R.id.sing_in_passwordR);
        firstName = findViewById(R.id.sing_in_firstName);
        lastName = findViewById(R.id.sing_in_LastName);
        male = findViewById(R.id.checkBox_male);
        female = findViewById(R.id.checkBox_female);
        create = findViewById(R.id.sing_in_crate);

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(female.isChecked()){
                    female.setChecked(false);
                }
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(male.isChecked()){
                    male.setChecked(false);
                }
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailS = email.getText().toString();
                String passwordS = password.getText().toString();
                String passwordRS = passwordR.getText().toString();
                String firstNameS = firstName.getText().toString();
                String lastNameS = lastName.getText().toString();
                boolean maleB = male.isChecked();
                boolean femaleB = female.isChecked();
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                JSONObject request = new JSONObject();
                try {
                    request.put("email", email);
                    request.put("password", password);
                    request.put("firstName", firstNameS);
                    request.put("lastName", lastNameS);
                    request.put("role", "USER");
                    request.put("status", "ACTIVE");
                    if(male.isChecked()){
                        request.put("gender", "male");
                    } else{
                        request.put("gender", "female");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest objectRequest = new JsonObjectRequest(
                        Request.Method.PUT,
                        url,
                        request,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String status = response.getString("status");
                                    if (status.equals("User was successfully created")) {
                                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT);
                                    }
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
        });
    }
}
