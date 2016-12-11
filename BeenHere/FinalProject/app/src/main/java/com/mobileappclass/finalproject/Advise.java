package com.mobileappclass.finalproject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
public class Advise extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advise);
        Button adviseback=(Button)findViewById(R.id.adviseback);
        adviseback.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(this,MainActivity.class);
        finish();
    }
}
