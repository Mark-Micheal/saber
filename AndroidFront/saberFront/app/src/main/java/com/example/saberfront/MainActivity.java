package com.example.saberfront;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;


import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;



public class MainActivity extends AppCompatActivity {

    RequestQueue mQ;
    EditText email;
    EditText password;
    String token;
    String type;

    public void loginListener(View view){
        Map<String, String> params = new HashMap<String, String>();
        params.put("email",email.getText().toString());
        params.put("password",password.getText().toString());
        JSONObject myparams = new JSONObject(params);
        Log.d(myparams.toString(),"my parameters");
        JsonObjectRequest jsonObjReq =new JsonObjectRequest(Request.Method.POST,
                "https://saberapp.herokuapp.com/api/login", myparams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            token = response.getString("token");
                            type =  response.getString("type");

                            if(type.equals("Student")){
                                Intent studentLogin = new Intent (MainActivity.this, landingAct.class);
                                studentLogin.putExtra("token",token);
                                startActivity(studentLogin);

                            }
                            else{

                                Intent adminLogin = new Intent (MainActivity.this, AdminAfterLogin.class);
                                adminLogin.putExtra("token",token);
                                startActivity(adminLogin);

                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Context context = getApplicationContext();
                CharSequence text = "Account does not exist";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                VolleyLog.d("JSONPost","Error: "+error.getMessage());
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.data != null) {
                    String jsonError = new String(networkResponse.data);
                    Log.d(jsonError,"my net error");
                }
                else{
                    CharSequence text2 = "No internet connection";
                    Toast toast2 = Toast.makeText(context, text2, duration);
                    toast2.show();
                }
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


    public void validation(View view){
        startActivity(new Intent(getApplicationContext(),registration.class));

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQ = Volley.newRequestQueue(this);
        email = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);
    }
}