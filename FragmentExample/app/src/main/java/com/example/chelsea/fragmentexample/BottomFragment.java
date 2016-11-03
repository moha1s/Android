package com.example.chelsea.fragmentexample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
/**
 * Created by Chelsea on 11/2/16.
 */

public class BottomFragment extends Fragment {

    private static TextView topText;
    private static TextView bottomText;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_fragment,container,false);
        topText=(TextView)view.findViewById(R.id.topText);
        bottomText=(TextView)view.findViewById(R.id.bottomText);
        return view;
    }

    public void setMeme(String top,String bottom){
        topText.setText(top);
        bottomText.setText(bottom);
    }

}
