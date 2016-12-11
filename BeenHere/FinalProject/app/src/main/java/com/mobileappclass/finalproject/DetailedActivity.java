package com.mobileappclass.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class DetailedActivity extends AppCompatActivity {

    private TextView textView_longitude;
    private TextView textView_latitude;
    private TextView textView_title;
    private TextView textView_userName;
    private TextView textView_time;
    private TextView textView_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        Intent intent = getIntent();

        textView_longitude = (TextView)findViewById(R.id.activity_detailed_longitude);
        textView_latitude = (TextView)findViewById(R.id.activity_detailed_latitude);
        textView_title = (TextView)findViewById(R.id.activity_detailed_title);
        textView_userName = (TextView)findViewById(R.id.activity_detailed_userName);
        textView_time = (TextView)findViewById(R.id.activity_detailed_time);
        textView_detail = (TextView)findViewById(R.id.activity_detailed_detail);

        double longitude  = intent.getDoubleExtra("longitude", 0.0);
        double latitude  = intent.getDoubleExtra("latitude", 0.0);
        String title = intent.getStringExtra("title");
        String userName = intent.getStringExtra("userName");
        String time = intent.getStringExtra("time");
        String detail = intent.getStringExtra("detail");

        textView_longitude.setText(String.valueOf(longitude));
        textView_latitude.setText(String.valueOf(latitude));
        textView_title.setText(title);
        textView_userName.setText(userName);
        textView_time.setText(time);
        textView_detail.setText(detail);


    }
}
