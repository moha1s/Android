package com.mobileappclass.assignment3;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Chelsea on 11/4/16.
 */

public class LocalFragment extends Fragment {

    public static DBAdapter myDb;
    Button clearButton;
    GPSBroadcast receiver;
    DatabaseReference ref;
    public static ArrayList<String> dataList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_local, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        openDB();
        ref = FirebaseDatabase.getInstance().getReference("Students").child("sw712");
        dataList = new ArrayList<>();



        populateListViewFromDB();
        IntentFilter filter = new IntentFilter();
        filter.addAction("location_update");
        receiver = new GPSBroadcast();
        getActivity().registerReceiver(receiver,filter);
        Toast.makeText(getActivity(),"register",Toast.LENGTH_SHORT).show();
        clearButton = (Button)getActivity().findViewById(R.id.button2);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearAll();
            }
        });
    }




    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().unregisterReceiver(receiver);
        Toast.makeText(getActivity(),"unregister",Toast.LENGTH_SHORT).show();
    }

    private void openDB() {
        myDb = new DBAdapter(getActivity());
        myDb.open();
    }

    private void closeDB() {
        myDb.close();
    }

    private class GPSBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String[] pieces = intent.getExtras().get("coordinates").toString().split("\t");
            long newId = myDb.insertRow(pieces[0]+":", pieces[1], pieces[2]);
            populateListViewFromDB();
        }
    }


    public void ClearAll() {
        myDb.deleteAll();
        populateListViewFromDB();
    }


    private void populateListViewFromDB(){
        Cursor cursor = myDb.getAllRows();

        String[] fromFieldNames = new String[]
                {DBAdapter.KEY_TIME, DBAdapter.KEY_LATITUDE, DBAdapter.KEY_LONGITUDE};
        int[] toViewIDs = new int[]
                {R.id.textViewTime, R.id.textViewLatitude, R.id.textViewLongitude};
        SimpleCursorAdapter myCursorAdapter =
                new SimpleCursorAdapter(
                        getActivity(),
                        R.layout.item_layout,
                        cursor,
                        fromFieldNames,
                        toViewIDs
                );
        ListView listView = (ListView) getActivity().findViewById(R.id.listViewLocal);
        listView.setAdapter(myCursorAdapter);
    }
}

