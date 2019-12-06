package com.example.saberfront;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class registration extends AppCompatActivity {


    RequestQueue mQ;
    EditText name;
    EditText email;
    EditText password;


    public void register(View view){
        JsonParser();
        //validation on the input password,name(if any) and email(using api above.
        //send to backend.
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

    private void JsonParser(){
        String eM = email.getText().toString();
        String url = "https://apilayer.net/api/check?access_key=03ae542095ff0badad76cc55aba8a1a2&email="+eM;
        JsonObjectRequest req =new JsonObjectRequest(com.android.volley.Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean res = response.getBoolean("smtp_check");
                    if(res){
                        Context context = getApplicationContext();
                        CharSequence text = "good email!!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }else{
                        Context context = getApplicationContext();
                        CharSequence text = "bad email!!";
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
    }
}
