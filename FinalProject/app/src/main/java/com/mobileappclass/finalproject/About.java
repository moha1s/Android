package com.mobileappclass.finalproject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class About extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Button aboutback=(Button)findViewById(R.id.aboutback);
        aboutback.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
