package com.mobileappclass.finalproject;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

public class Setting extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Button back=(Button)findViewById(R.id.settingback);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i=new Intent(this,MainActivity.class);
        final EditText nameInput=(EditText)findViewById(R.id.ed1);
        String userName = nameInput.getText().toString();
        i.putExtra("Name",userName);
        final EditText sayInput=(EditText)findViewById(R.id.ed2);
        String userSay = sayInput.getText().toString();
        i.putExtra("Say",userSay);
        startActivity(i);

    }
}
