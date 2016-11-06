package com.example.chelsea.practice;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

public class Meat extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meat);

        Bundle data=getIntent().getExtras();
        if (data==null){
            return;
        }
        String a=data.getString("apple");
         TextView t=(TextView)findViewById(R.id.textView2);
        t.setText(a);


    }
    public void onClick(View view){
        Intent i=new Intent(this,MainActivity.class);

        startActivity(i);
    }




}
