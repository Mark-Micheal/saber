package com.example.saberfront;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ClearingReservationsAct extends AppCompatActivity {


    Spinner clear;
    Button confirm;
    String token;
    String chosenDay;
    View contextView;
    Snackbar mySnackbar;
    RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clearing_reservations);
        confirm = findViewById(R.id.clearButton);
        clear = findViewById(R.id.clearDaySpinner);
        mRequestQueue = Volley.newRequestQueue(this);


        Intent myIntent = getIntent();
        token = myIntent.getStringExtra("token");

        ArrayAdapter<CharSequence> adapter_day2 = ArrayAdapter.createFromResource(this,
                R.array.days_array, android.R.layout.simple_spinner_item);
        adapter_day2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clear.setAdapter(adapter_day2);
        contextView= findViewById(R.id.clearButton);
        mySnackbar =  Snackbar.make(contextView, R.string.snackmsg, Snackbar.LENGTH_LONG);


        confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                chosenDay= clear.getSelectedItem().toString();


                clearReservations();


                // Code here executes on main thread after user presses button
//                Intent goBack = new Intent(ClearingReservationsAct.this, DisplaySchedule.class);
//                goBack.putExtra("token",token);
//                startActivity(goBack);


            }
        });
    }

    private  void clearReservations(){
        String URL = "https://saberapp.herokuapp.com/api/clearReservations?day="+chosenDay;
        StringRequest request = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("hello from the outside"+ response);
                        mySnackbar.show();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type","application/json");
                headers.put("Accept","application/json");
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        mRequestQueue.add(request);
    }


}
