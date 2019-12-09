package com.example.saberfront;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

    static String token;
    public static String day;

    ListView ls;

    JSONArray contents;
    public static JSONObject chosen;
    private static RequestQueue mQ;
    public ArrayList<String> content = new ArrayList<String>();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slots_results);
        token = getIntent().getStringExtra("token");
        day =getIntent().getStringExtra("day");
        ls = (ListView) findViewById(R.id.listView2);
        mQ = Volley.newRequestQueue(this);
        try {
            getFree();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    chosen = contents.getJSONObject(position);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialog d = new dialog();
                d.show(getSupportFragmentManager(),"nothing");
            }
        });
    }


    public static void reserve(){

        JSONObject params = new JSONObject();
        try {
            params.put("slot",chosen.getString("slot"));
            params.put("building",chosen.getString("building"));
            params.put("floor",chosen.getString("floor"));
            params.put("number",chosen.getString("number"));
            params.put("day",day);
            params.put("additional_info","done");
        } catch (JSONException e) {
            //e.printStackTrace();
        }
        System.out.println(params.toString());
        JsonObjectRequest jsonObjReq =new JsonObjectRequest(Request.Method.POST,
                "https://saberapp.herokuapp.com/api/reservations", params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

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
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        mQ.add(jsonObjReq);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void getFree() throws JSONException {
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET,
                "https://saberapp.herokuapp.com/api/freeReservations?day="+day,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            contents=response;
                            for (int i = 0; i < response.length(); i++) {
                                String res = "";
                                JSONObject element = response.getJSONObject(i);
                                res += "Building : "+element.getString("building")+" , Floor :"+element.getString("floor")+" , Room :"+element.getString("number") + " , " + element.getString("slot");
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
