package com.mobileappclass.finalproject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.widget.EditText;


public class FgMe extends Fragment {

    public FgMe() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content4,container,false);
        Bundle nameData=getActivity().getIntent().getExtras();
        if(nameData==null){
            nameData=new Bundle();

        }
        String Name=nameData.getString("Name");
        final TextView t=(TextView)view.findViewById(R.id.name);
        t.setText(Name);

        Bundle sayData=getActivity().getIntent().getExtras();
        if(sayData==null){
            sayData=new Bundle();

        }
        String Say=sayData.getString("Say");
        final TextView t1=(TextView)view.findViewById(R.id.profile);
        t1.setText(Say);


        Button aboutus = (Button)view.findViewById(R.id.aboutus);
        aboutus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), About.class);
                startActivity(intent);

            }
        });
        Button advise = (Button)view.findViewById(R.id.advise);
        advise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent advise = new Intent(getActivity(), Advise.class);
                startActivity(advise);
            }
        });
        Button donate = (Button)view.findViewById(R.id.donate);
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent donate = new Intent(getActivity(), Donate.class);
                startActivity(donate);
            }
        });
        Button setting = (Button)view.findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setting = new Intent(getActivity(), Setting.class);
                startActivity(setting);
            }
        });
        Button post = (Button)view.findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent post = new Intent(getActivity(), Post.class);
                startActivity(post);
            }
        });
        return view;
    }




}
