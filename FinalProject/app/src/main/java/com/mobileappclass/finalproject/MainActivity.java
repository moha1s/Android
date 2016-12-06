package com.mobileappclass.finalproject;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //UI Object
    private TextView txt_home;
    private TextView txt_list;
    private TextView txt_place;
    private TextView txt_me;


    //Fragment Object
    private FgHome fg1;
    private FgList fg2;
    private FgPlace fg3;
    private FgMe fg4;
    private FragmentManager fManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        fManager = getFragmentManager();
        bindViews();
        txt_home.performClick();
    }

    private void bindViews() {
        txt_home = (TextView) findViewById(R.id.txt_home);
        txt_list = (TextView) findViewById(R.id.txt_list);
        txt_place = (TextView) findViewById(R.id.txt_place);
        txt_me = (TextView) findViewById(R.id.txt_me);



        txt_home.setOnClickListener(this);
        txt_list.setOnClickListener(this);
        txt_place.setOnClickListener(this);
        txt_me.setOnClickListener(this);
    }


    private void setSelected(){
        txt_home.setSelected(false);
        txt_list.setSelected(false);
        txt_place.setSelected(false);
        txt_me.setSelected(false);
    }


    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fg1 != null)fragmentTransaction.hide(fg1);
        if(fg2 != null)fragmentTransaction.hide(fg2);
        if(fg3 != null)fragmentTransaction.hide(fg3);
        if(fg4 != null)fragmentTransaction.hide(fg4);
    }


    @Override
    public void onClick(View v) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (v.getId()){
            case R.id.txt_home:
                setSelected();
                txt_home.setSelected(true);
                if(fg1 == null){
                    fg1 = new FgHome();
                    fTransaction.add(R.id.ly_content,fg1);
                }else{
                    fTransaction.show(fg1);
                }
                break;
            case R.id.txt_list:
                setSelected();
                txt_list.setSelected(true);
                if(fg2 == null){
                    fg2 = new FgList();
                    fTransaction.add(R.id.ly_content,fg2);
                }else{
                    fTransaction.show(fg2);
                }
                break;
            case R.id.txt_place:
                setSelected();
                txt_place.setSelected(true);
                if(fg3 == null){
                    fg3 = new FgPlace();
                    fTransaction.add(R.id.ly_content,fg3);
                }else{
                    fTransaction.show(fg3);
                }
                break;
            case R.id.txt_me:
                setSelected();
                txt_me.setSelected(true);
                if(fg4 == null){
                    fg4 = new FgMe();
                    fTransaction.add(R.id.ly_content,fg4);
                }else{
                    fTransaction.show(fg4);
                }
                break;
        }
        fTransaction.commit();
    }
}