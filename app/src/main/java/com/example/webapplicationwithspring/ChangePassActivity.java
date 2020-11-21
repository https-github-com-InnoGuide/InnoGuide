package com.example.webapplicationwithspring;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.r0adkll.slidr.Slidr;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ChangePassActivity extends AppCompatActivity {

    private EditText currPass;
    private EditText newPass;
    private EditText RNewPass;
    private TextView confirm;

    private String url = "http://192.168.0.119:8080/auth/changePass";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        Slidr.attach(this);

        currPass = findViewById(R.id.changePass_curPass);
        newPass = findViewById(R.id.changePass_newPass);
        RNewPass = findViewById(R.id.changePass_RNewPass);

        confirm = findViewById(R.id.changePass_confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currPassword = currPass.getText().toString();
                if(currPassword.equals("")){

                } else {
                    String newPassword = newPass.getText().toString();
                    String RNewPassword = RNewPass.getText().toString();
                    if(newPassword.equals(RNewPassword)){
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        final JSONObject request = new JSONObject();
                        try {
                            request.put("email", Person.getEmail());
                            request.put("oldPass", currPassword);
                            request.put("newPass", newPassword);
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
                                        String resp = null;
                                        try {
                                            resp = response.getString("status");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        if (resp.equals("Success")) {
                                            Intent intent = new Intent(ChangePassActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        } else {
                                            final AlertDialog aboutDialog = new AlertDialog.Builder(
                                                    ChangePassActivity.this).setMessage("Invalid password")
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // TODO Auto-generated method stub
                                                        }
                                                    }).create();
                                            aboutDialog.show();
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
                        ){
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("Authorization", Token.token);
                                params.put("Content-Type", "application/json");
                                return params;
                            }
                        };
                        requestQueue.add(objectRequest);




                    } else {



                    }
                }

            }
        });

    }
}
