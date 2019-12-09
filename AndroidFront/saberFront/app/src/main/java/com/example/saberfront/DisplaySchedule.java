package com.example.saberfront;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DisplaySchedule extends AppCompatActivity {
    TextView first, second, third,fourth,fifth, afterhours;
    String firstSlot,secondSlot,thirdSlot,fourthSlot,fifthSlot, afterhoursSlot;
    //Button cancel;
    //Button save;
    Button b1,b2,b3,b4,b5,b6;
    View contextView;
    Snackbar mySnackbar;
    Snackbar cant;
    String day, roomNr, floorNr, buildingNr, room;
    String URL;
    String []slotChanged = new String[6];
    String token;
    int sourceClick =0;
    TextView title;
    private RequestQueue mRequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_schedule);
        //save = findViewById(R.id.saveButton);
        //cancel = findViewById(R.id.cancelButton);
        title = findViewById(R.id.title);


        for(int i=0; i<slotChanged.length;i++)
            slotChanged[i]="-1";

        contextView= findViewById(R.id.button1);
        mySnackbar =  Snackbar.make(contextView, R.string.snackmsg, Snackbar.LENGTH_SHORT);
        cant =  Snackbar.make(contextView, R.string.snackmsg2, Snackbar.LENGTH_SHORT);

        first = findViewById(R.id.firstText);
        second = findViewById(R.id.secondText);
        third = findViewById(R.id.thirdText);
        fourth = findViewById(R.id.fourthText);
        fifth = findViewById(R.id.fifthText);
        afterhours = findViewById(R.id.afterhoursText);

        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);
        b4 = findViewById(R.id.button4);
        b5 = findViewById(R.id.button5);
        b6 = findViewById(R.id.button6);

        Intent myIntent = getIntent();

        roomNr = myIntent.getStringExtra("roomNumberSelected"); // will return "FirstKeyValue"
        floorNr= myIntent.getStringExtra("floorNumberSelected");
        day= myIntent.getStringExtra("daySelected");
        buildingNr= myIntent.getStringExtra("buildingNumberSelected");
        token = myIntent.getStringExtra("token");
        System.out.println("room: "+ roomNr +"build "+buildingNr + "floor "+floorNr+"day "+ day);
        if(Integer.parseInt(roomNr) <10) {
            title.setText("Schedule of Room: " + buildingNr + floorNr + "0"+roomNr+" on "+day+", you can add tutorials to free slots");
        }
        else{
            title.setText("Schedule of Room: " + buildingNr + floorNr +roomNr +"on "+day);

        }


        mRequestQueue = Volley.newRequestQueue(this);

        room = buildingNr+floorNr+roomNr;

        jsonParse();

        b1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                sourceClick=1;
                if(b1.getText().equals("ADD")){

                    myPost();
                    jsonParse();
                }
                else{
                    System.out.println("DELETEEEEE");
                }


            }});
        b2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                sourceClick=2;
                if(b2.getText().equals("ADD")) {

                    myPost();
                    jsonParse();
                }
                else{
                    System.out.println("DELETEEEEE");
                }


            }});
        b3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                sourceClick=3;

                if(b3.getText().equals("ADD")){

                    myPost();
                    jsonParse();}
                else{
                    System.out.println("DELETEEEEE");
                }


            }});
        b4.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                sourceClick=4;
                if(b4.getText().equals("ADD")){

                    myPost();
                    jsonParse();
                }
                else{
                    System.out.println("DELETEEEEE");

                }


            }});
        b5.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                sourceClick=5;
                if(b5.getText().equals("ADD")) {

                    myPost();
                    jsonParse();
                }
                else{
                    System.out.println("DELETEEEEE");
                }


            }});
        b6.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                sourceClick=6;
                if(b6.getText().equals("ADD")){
                    myPost();
                    jsonParse();}


            }});




    }
    private void myPost(){
        String slot="";
        switch(sourceClick){
            case(1):slot="1st";break;
            case(2):slot="2nd";break;
            case(3):slot="3rd";break;
            case(4):slot="4th";break;
            case(5):slot="5th";break;
            case(6):slot="After Hours";break;
        }


        String url = "https://saberapp.herokuapp.com/api/reservations";
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("floor",floorNr);
        params.put("building",buildingNr);
        params.put("number",roomNr);
        params.put("slot",slot);
        params.put("additional_info","android insertion");
        params.put("tutorial",1);
        params.put("day",day);

        JSONObject myparams = new JSONObject(params);

        Log.d(myparams.toString(),"my parameters");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, myparams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response.has("data")){
                            try {
                                if(response.getString("data").equals("This slot is already reserved")){
                                    cant.show();
                                    switch(sourceClick){
                                        case(1):b1.setEnabled(false);break;
                                        case(2):b2.setEnabled(false);break;
                                        case(3):b3.setEnabled(false);break;
                                        case(4):b4.setEnabled(false);break;
                                        case(5):b5.setEnabled(false);break;
                                        case(6):b6.setEnabled(false);break;
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                cant.show();
                            }
                        }
                        System.out.println(response.toString());
                        mySnackbar.show();



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type","application/json");
                headers.put("Accept","application/json");
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        mRequestQueue.add(request);

    }



    private void jsonParse() {

        String url = "https://saberapp.herokuapp.com/api/dayReservations?building="+buildingNr+"&floor="+floorNr+"&number="+roomNr+"&day="+day;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            System.out.println(response.toString());


                            for (int i = 0; i < response.length(); i++) {
                                JSONObject res = response.getJSONObject(i);
                                String slot_num = res.getString("slot");
                                System.out.println("MY SLOT NUM"+slot_num);
                                boolean tut = res.getBoolean("tutorial");

                                String slot="";
                                if(tut){
                                    System.out.println(tut+"HEREE");
                                    slot ="tutorial";
                                }
                                else{
                                    System.out.println(tut+"there");
                                    slot ="student reservation";
                                }
                                if(slot_num.equals("1st")){
                                    firstSlot = slot;
                                    b1.setEnabled(false);
                                }
                                if(slot_num.equals("2nd")){
                                    secondSlot = slot;
                                    System.out.println("SECOND");
                                    b2.setEnabled(false);


                                }
                                if(slot_num.equals("3rd")){
                                    thirdSlot = slot;
                                    b3.setEnabled(false);
                                }
                                if(slot_num.equals("4th")){
                                    fourthSlot = slot;
                                    b4.setEnabled(false);

                                }
                                if(slot_num.equals("5th")){
                                    fifthSlot = slot;
                                    b5.setEnabled(false);

                                }
                                if(slot_num.equals("After Hours")) {
                                    afterhoursSlot = slot;
                                    b6.setEnabled(false);

                                }



                            }
                            first.setText(firstSlot);
                            second.setText(secondSlot);
                            third.setText(thirdSlot);
                            fourth.setText(fourthSlot);
                            fifth.setText(fifthSlot);
                            afterhours.setText(afterhoursSlot);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type","application/json");
                headers.put("Accept","application/json");
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        mRequestQueue.add(request);
    }





}
