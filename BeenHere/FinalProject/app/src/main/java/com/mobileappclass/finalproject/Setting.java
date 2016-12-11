package com.mobileappclass.finalproject;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;

import java.util.Map;

public class Setting extends Activity implements View.OnClickListener {

    private EditText ed1;
    private EditText ed2;
    private Button save_btn;
    private String strname;
    private String strprofile;
    private SharedHelper sh;
    private Context mContext;
    private  String a;
    private String b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mContext = getApplicationContext();
        sh = new SharedHelper(mContext);
        bindViews();
        Button back=(Button)findViewById(R.id.settingback);
        back.setOnClickListener(this);
    }

    private void bindViews() {
        ed1 = (EditText)findViewById(R.id.ed1);
        ed2 = (EditText)findViewById(R.id.ed2);
        save_btn = (Button)findViewById(R.id.save_btn);
        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strname = ed1.getText().toString();
                strprofile = ed2.getText().toString();
                sh.save(strname,strprofile);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Map<String,String> data = sh.read();
        a=data.get("username");
        b=data.get("profile");
        ed1.setText(data.get("username"));
        ed2.setText(data.get("profile"));

    }

    @Override
    public void onClick(View view) {
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);

    }
}
