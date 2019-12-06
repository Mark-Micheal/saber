package com.example.saberfront;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;

import android.content.Intent;
import android.widget.EditText;

import com.android.volley.RequestQueue;
//import com.android.volley.toolbox.Volley;


public class MainActivity extends AppCompatActivity {

    //RequestQueue mQ;
    EditText email;
    public void loginListener(View view){
        startActivity(new Intent(getApplicationContext(),landingAct.class));
    }

    public void validation(View view){
        startActivity(new Intent(getApplicationContext(),registration.class));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mQ = Volley.newRequestQueue(this);
        email = (EditText) findViewById(R.id.editText);
    }
}
