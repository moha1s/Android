package com.example.chelsea.practice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
     public void onClick(View view){
         Intent i=new Intent(this,Meat.class);
          EditText e=(EditText)findViewById(R.id.editText1);
         String str=e.getText().toString();
         i.putExtra("apple",str);
         startActivity(i);
     }

}
