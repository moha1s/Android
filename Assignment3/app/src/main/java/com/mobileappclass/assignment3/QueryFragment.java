package com.mobileappclass.assignment3;

/**
 * Created by Chelsea on 11/5/16.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class QueryFragment extends Fragment {
    private Spinner spinner;
    private static final String[] option = {"Ascending","Descending"};
    private int selection;
    private EditText net_id;
    private Button enter;
    private ListView listView;
    private ArrayList<String> dataList;
    private ArrayList<Information> informationList;
    private ArrayAdapter<String> listadapter;
    private ArrayAdapter<String> spinneradapet;
    private boolean init;
    private int[] compare_time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_query, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        spinner = (Spinner) getActivity().findViewById(R.id.spinner);
        net_id = (EditText) getActivity().findViewById(R.id.edittext);
        enter = (Button) getActivity().findViewById(R.id.enterbutton);
        listView =(ListView) getActivity().findViewById(R.id.lv);
        dataList = new ArrayList<>();
        informationList = new ArrayList<>();

        listadapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(listadapter);

        spinneradapet = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,option);
        spinner.setAdapter(spinneradapet);
        spinner.setVisibility(View.VISIBLE);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    selection = 0;
                }
                else if(position == 1){
                    selection = 1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selection = 1;
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init = true;
                dataList.clear();
                Toast.makeText(getActivity(), "button works", Toast.LENGTH_SHORT).show();
                if(!("".equals(net_id.getText().toString().trim()))) {
                    String netid = net_id.getText().toString();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Students").child(netid);
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            while (init) {
                                informationList.clear();
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    String time = (String) data.child("date").getValue();
                                    String netid = (String) data.child("netid").getValue();
                                    String x = (String) data.child("x").getValue();
                                    String y = (String) data.child("y").getValue();
                                    Information information= new Information("","","","");
                                    information.setDate(time);
                                    information.setNetid(netid);
                                    information.setX(x);
                                    information.setY(y);
                                    informationList.add(information);
                                }
                                Collections.sort(informationList, new Comparator<Information>() {
                                    @Override
                                    public int compare(Information o1, Information o2) {
                                        return o1.getDate().compareTo(o2.getDate());
                                    }
                                });
                                if(selection ==0)
                                {
                                    Toast.makeText(getActivity(), "ascending", Toast.LENGTH_SHORT).show();
                                    //ascending
                                    for(int i=0;i<informationList.size()-1;i++) {
                                        dataList.add(informationList.get(i).getDate() + " " + informationList.get(i).getNetid() + " " + informationList.get(i).getX() + " " + informationList.get(i).getY());
                                    }
                                    listadapter.notifyDataSetChanged();
                                }
                                else if(selection == 1){
                                    Toast.makeText(getActivity(), "descending", Toast.LENGTH_SHORT).show();
                                    //descending
                                    for(int i=0;i<informationList.size()-1;i++) {
                                        dataList.add(0,informationList.get(i).getDate() + " " + informationList.get(i).getNetid() + " " + informationList.get(i).getX() + " " + informationList.get(i).getY());
                                    }
                                    listadapter.notifyDataSetChanged();
                                }
                                init= false;
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    Toast.makeText(getActivity(), "The netId should not be empty", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
