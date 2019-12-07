package com.example.saberfront;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class AdminAfterLogin extends AppCompatActivity {
Button next;
Button del;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_after_login);

        next= (Button) findViewById(R.id.next);
        del = findViewById(R.id.delete);
        Log.d("Reached","msg");
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                //startActivity(new Intent(AdminAfterLogin.this, SelectRoom.class));

            }
        });
    }
}
