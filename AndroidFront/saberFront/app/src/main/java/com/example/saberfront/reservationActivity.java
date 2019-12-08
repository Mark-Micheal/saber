package com.example.saberfront;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.view.View;
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
    Spinner dropdown2;
    Spinner dropdown3;
    Spinner dropdown4;
    Spinner dropdown5;

    public void SearchForFree(View view){
        Intent results = new Intent(getApplicationContext(),slotsResults.class);
        results.putExtra("token",token);
        results.putExtra("day",dropdown1.getSelectedItem().toString());
        results.putExtra("slot",dropdown1.getSelectedItem().toString());
        results.putExtra("building",dropdown1.getSelectedItem().toString());
        results.putExtra("floor",dropdown1.getSelectedItem().toString());
        results.putExtra("room",dropdown1.getSelectedItem().toString());
        startActivity(results);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        token = getIntent().getStringExtra("token");

        dropdown1 = findViewById(R.id.spinner1);
        String[] items1 = new String[]{"Choose a day","Monday", "Tuesday", "Wednesday","Thursday","Friday","Saturday","Sunday"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        dropdown1.setAdapter(adapter1);

        dropdown2 = findViewById(R.id.spinner2);
        String[] items2 = new String[]{"Choose a slot","1st", "2nd", "3rd","4th","5th","After Hours"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        dropdown2.setAdapter(adapter2);

        dropdown3 = findViewById(R.id.spinner);
        String[] items3 = new String[]{"Choose a building","B", "C", "D"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items3);
        dropdown3.setAdapter(adapter3);

        dropdown4 = findViewById(R.id.spinner3);
        String[] items4 = new String[]{"Choose a floor","1", "2", "3"};
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items4);
        dropdown4.setAdapter(adapter4);

        dropdown5 = findViewById(R.id.spinner4);
        String[] items5 = new String[]{"Choose a room","01", "02", "03","04", "05", "06","07", "08", "09","10", "11", "12"};
        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items5);
        dropdown5.setAdapter(adapter5);

    }
}
