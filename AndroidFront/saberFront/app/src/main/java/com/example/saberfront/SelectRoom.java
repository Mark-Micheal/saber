package com.example.saberfront;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SelectRoom extends AppCompatActivity {
    Button showSchedules;
    Spinner roomNrSpinner;
    Spinner floorNrSpinner;
    Spinner buildingNrSpinner;
    Spinner daySpinner;
    String roomNr;
    String buildingNr;
    String floorNr;
    String day;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_room);

        roomNrSpinner = findViewById(R.id.roomSpinner);
        buildingNrSpinner = findViewById(R.id.buildingSpinner);
        floorNrSpinner = findViewById(R.id.floorSpinner);
        daySpinner = findViewById(R.id.daySpinner);
        showSchedules  = findViewById(R.id.nextbutton);

        Intent myIntent = getIntent();
        token = myIntent.getStringExtra("token");

        ArrayAdapter<CharSequence> adapter_building = ArrayAdapter.createFromResource(this,
                R.array.building_array, android.R.layout.simple_spinner_item);
        adapter_building.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buildingNrSpinner.setAdapter(adapter_building);

        ArrayAdapter<CharSequence> adapter_room = ArrayAdapter.createFromResource(this,
                R.array.room_array, android.R.layout.simple_spinner_item);
        adapter_room.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomNrSpinner.setAdapter(adapter_room);

        ArrayAdapter<CharSequence> adapter_floor = ArrayAdapter.createFromResource(this,
                R.array.floor_array, android.R.layout.simple_spinner_item);
        adapter_floor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        floorNrSpinner.setAdapter(adapter_floor);

//        ArrayAdapter<CharSequence> adapter_day = ArrayAdapter.createFromResource(this,
//                R.array.days_array, android.R.layout.simple_spinner_item);
//        adapter_day.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        daySpinner.setAdapter(adapter_day);
        ArrayAdapter<CharSequence> adapter_day2 = ArrayAdapter.createFromResource(this,
                R.array.days_array, android.R.layout.simple_spinner_item);
        adapter_day2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(adapter_day2);



        showSchedules.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                roomNr = roomNrSpinner.getSelectedItem().toString();
                floorNr = floorNrSpinner.getSelectedItem().toString();
                buildingNr = buildingNrSpinner.getSelectedItem().toString();
                day= daySpinner.getSelectedItem().toString();
                System.out.println("DAYY"+day);
                Log.d(roomNr+"","roomnr");
                Log.d(floorNr+"","floor");
                Log.d(buildingNr+"","build");
                System.out.println("MY FLOOR IS:"+floorNr);

                // Code here executes on main thread after user presses button
                Intent showingSchedules = new Intent(SelectRoom.this, DisplaySchedule.class);

                showingSchedules.putExtra("roomNumberSelected",roomNr);
                showingSchedules.putExtra("floorNumberSelected",floorNr);
                showingSchedules.putExtra("buildingNumberSelected",buildingNr);
                showingSchedules.putExtra("daySelected",day);
                showingSchedules.putExtra("token",token);



                startActivity(showingSchedules);


            }
        });
    }
}
