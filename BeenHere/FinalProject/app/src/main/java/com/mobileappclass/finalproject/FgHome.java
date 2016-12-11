package com.mobileappclass.finalproject;

/**
 * Created by Chelsea on 11/27/16.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FgHome extends Fragment {

    private String content;
    public FgHome() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content1,container,false);
        return view;
    }
}
