package com.example.saberfront;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class registration extends AppCompatActivity {


    RequestQueue mQ;
    EditText name;
    EditText email;
    EditText password;
    String token;
    boolean res=false;

    public void register(View view){
        if(password.getText().toString().length()<6 || !JsonParser()){
            if(password.getText().toString().length()<6) {
                Context context = getApplicationContext();
                CharSequence text = "password is too short !!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }else{
            Map<String, String> params = new HashMap<String, String>();
            params.put("email",email.getText().toString());
            params.put("password",password.getText().toString());
            params.put("name",name.getText().toString());
            params.put("type","Student");
            JSONObject myparams = new JSONObject(params);
            Log.d(myparams.toString(),"my parameters");
            JsonObjectRequest jsonObjReq =new JsonObjectRequest(Request.Method.POST,
                    "https://saberapp.herokuapp.com/api/register", myparams,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                token = response.getString("token");
                                Intent studentRegister = new Intent (registration.this, landingAct.class);
                                studentRegister.putExtra("token",token);
                                startActivity(studentRegister);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("JSONPost","Error: "+error.getMessage());
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        String jsonError = new String(networkResponse.data);
                        Log.d(jsonError,"my net error");
                    }
                    else{
                        Log.d("No error","my net error");
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

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mQ = Volley.newRequestQueue(this);
        email = (EditText) findViewById(R.id.editText3);
        name = (EditText) findViewById(R.id.editText5);
        password = (EditText) findViewById(R.id.editText4);
    }

    private boolean JsonParser(){
        String eM = email.getText().toString();
        String url = "https://apilayer.net/api/check?access_key=03ae542095ff0badad76cc55aba8a1a2&email="+eM;
        JsonObjectRequest req =new JsonObjectRequest(com.android.volley.Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    res = response.getBoolean("smtp_check");
                    if(!res){
                        Context context = getApplicationContext();
                        CharSequence text = "email not right !!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQ.add(req);
        return res;
    }
}
