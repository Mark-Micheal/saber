package com.example.saberfront;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.Toast;
//import java.util.ArrayList;
//import java.util.List;
//import android.app.Activity;
//import android.os.Bundle;
//
//import android.view.View.OnClickListener;
//
//import android.widget.Button;
//
//import android.widget.Toast;

public class reservationActivity extends AppCompatActivity {


    String token;
    Spinner dropdown1;

    public void SearchForFree(View view){
        if(dropdown1.getSelectedItem().toString()!="Choose a day") {
            Intent results = new Intent(getApplicationContext(), slotsResults.class);
            results.putExtra("token", token);
            results.putExtra("day", dropdown1.getSelectedItem().toString());
            startActivity(results);
        }else{
            Context context = getApplicationContext();
            CharSequence text = "Please choose a day";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        token = getIntent().getStringExtra("token");

        dropdown1 = findViewById(R.id.spinner1);
        String[] items1 = new String[]{"Choose a day","Monday", "Tuesday", "Wednesday","Thursday","Saturday","Sunday"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        dropdown1.setAdapter(adapter1);


    }
}
