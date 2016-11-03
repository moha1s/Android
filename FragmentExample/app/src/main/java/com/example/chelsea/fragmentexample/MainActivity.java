package com.example.chelsea.fragmentexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements TopFragment.TopFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void CreateMeme(String top, String bottom) {
        BottomFragment bottomFragment=(BottomFragment) getSupportFragmentManager().findFragmentById(R.id.fragment5);
        bottomFragment.setMeme(top,bottom);

    }
}
