package com.example.saberfront;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class AdminAfterLogin extends AppCompatActivity {
    Button next;
    Button del;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_after_login);
        Intent myIntent = getIntent();
        token = myIntent.getStringExtra("token");

        next= (Button) findViewById(R.id.next);
        del = findViewById(R.id.delete);
        Log.d("Reached","msg");
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                //startActivity(new Intent(AdminAfterLogin.this, SelectRoom.class));
                Intent roomSelectionActivity = new Intent (AdminAfterLogin.this, SelectRoom.class);
                roomSelectionActivity.putExtra("token",token);

                startActivity(roomSelectionActivity);

            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                //startActivity(new Intent(AdminAfterLogin.this, SelectRoom.class));
                Intent dataDeletionActivity = new Intent (AdminAfterLogin.this, ClearingReservationsAct.class);
                dataDeletionActivity.putExtra("token",token);

                startActivity(dataDeletionActivity);

            }
        });
    }
}
