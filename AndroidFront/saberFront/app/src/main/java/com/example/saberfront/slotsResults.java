package com.example.saberfront;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class slotsResults extends AppCompatActivity {

    String token;

    ListView ls;
    private RequestQueue mQ;
    public ArrayList<String> content = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slots_results);
        token = getIntent().getStringExtra("token");
        ls = (ListView) findViewById(R.id.listView2);
        mQ = Volley.newRequestQueue(this);
        getFree();



        Map<String, String> params = new HashMap<String, String>();
//        params.put("email",email.getText().toString());
//        params.put("password",password.getText().toString());
        JSONObject myparams = new JSONObject(params);
        JsonObjectRequest jsonObjReq =new JsonObjectRequest(Request.Method.POST,
                "https://saberapp.herokuapp.com/api/login", myparams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

//                        try {
//
//
//                        } catch (JSONException e){
//                            e.printStackTrace();
//                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }}){
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type","application/json");
                headers.put("Accept","application/json");
                return headers;
            }
        };
        mQ.add(jsonObjReq);
    }

    void getFree(){
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET,
                "https://saberapp.herokuapp.com/api/myReservations", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                String res = "";
                                JSONObject element = response.getJSONObject(i);
                                res += element.getString("day") + " , " + element.getString("slot");
                                content.add(res);
                            }
                            ArrayAdapter<String> aa = new ArrayAdapter<String>(slotsResults.this, android.R.layout.simple_list_item_1, content);
                            ls.setAdapter(aa);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error Occured");
            }
        }) {
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        mQ.add(req);
    }
}
