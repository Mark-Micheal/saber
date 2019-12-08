package com.example.saberfront;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class landingAct extends AppCompatActivity {
    String token;

    public void reserve(View view){
        Intent reserveInt = new Intent(getApplicationContext(),reservationActivity.class);
        reserveInt.putExtra("token",token);
        startActivity(reserveInt);

    }

    public void getMyReservations(View view){
        Intent myRes = new Intent(getApplicationContext(),viewMyRes.class);
        myRes.putExtra("token",token);
        startActivity(myRes);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        token = getIntent().getStringExtra("token");
    }
}
