package com.example.saberadminyay;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminsShowingSchedules extends AppCompatActivity {
Button n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        n= (Button) findViewById(R.id.n);
    }
}
