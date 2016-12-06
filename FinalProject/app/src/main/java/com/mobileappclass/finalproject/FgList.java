package com.mobileappclass.finalproject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


public class FgList extends Fragment {

    private String content;
    public FgList() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content2,container,false);
        ListView list = (ListView) view.findViewById(R.id.list1);
        return view;
    }
}
