package com.mobileappclass.assignment3;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Chelsea on 11/4/16.
 */

public class ServerFragment extends Fragment {
    ArrayAdapter<String> arrayAdapter;
    private BroadcastReceiver networkChangeReceiver;
    private severUploadBoardcastReceiver receiver;
    TextView networktype;
    TextView serverstatus;
    ArrayList<String> dataList;
    ArrayList<Information> informationList;
    ListView severListView;
    DatabaseReference refdownload;
    DatabaseReference refupload;
    Button sync;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_server, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        severListView = (ListView) getActivity().findViewById(R.id.listViewServer);
        sync=(Button)getActivity().findViewById(R.id.button);
        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDatafromDatabase();
            }
        });
        dataList = new ArrayList<>();
        informationList = new ArrayList<>();
        refdownload = FirebaseDatabase.getInstance().getReference("Students");
        refupload = FirebaseDatabase.getInstance().getReference("Students").child("tz136");
        arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,dataList);
        severListView.setAdapter(arrayAdapter);
        refdownload.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //newdataList.clear();
                dataList.clear();
                informationList.clear();
                for (DataSnapshot person: dataSnapshot.getChildren()){
                    for(DataSnapshot data : person.getChildren()){
                        String time = (String) data.child("date").getValue();
                        String netid = (String) data.child("netid").getValue();
                        String x = (String)data.child("x").getValue();
                        String y = (String)data.child("y").getValue();
                        Information information= new Information("","","","");
                        information.setDate(time);
                        information.setNetid(netid);
                        information.setX(x);
                        information.setY(y);
                        informationList.add(information);
                    }
                }
                Collections.sort(informationList, new Comparator<Information>() {
                    @Override
                    public int compare(Information o1, Information o2) {
                        return o1.getDate().compareTo(o2.getDate());
                    }
                });
                for(int i=informationList.size()-1;i>informationList.size()-11;i--) {
                    dataList.add(informationList.get(i).getDate() + " " + informationList.get(i).getNetid() + " " + informationList.get(i).getX() + " " + informationList.get(i).getY());
                }
                //Collections.sort(dataList);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        networktype = (TextView)getActivity().findViewById(R.id.network_type);
        serverstatus = (TextView) getActivity().findViewById(R.id.server_status);
        networkChangeReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String network_type="";
                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (activeNetwork != null) { // connected to the internet
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        network_type+="WIFI";
                        new uploadTask().execute();
                    } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        network_type+="MOBILE";
                    }
                } else {
                    network_type+="NO CONNECTION";
                }
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                String wifiName = wifiManager.getConnectionInfo().getSSID();
                if (wifiName != null && !wifiName.contains("unknown ssid")){
                    network_type+=wifiName;
                } else {
                    // network name unknown
                }
                TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
                String networkName = tm.getNetworkOperatorName();
                if (networkName != null){
                    network_type+=networkName;
                } else {
                    // network name unknown
                }
                networktype.setText(network_type);
            }
        };
        getActivity().registerReceiver(networkChangeReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")); // register the receiver
    }


    public void onDetach() {
        super.onDetach();
        getActivity().unregisterReceiver(networkChangeReceiver);
        if (receiver!=null)
            getActivity().unregisterReceiver(receiver);
    }


    public class uploadTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            getDatafromDatabase();
            IntentFilter filter = new IntentFilter();
            filter.addAction("location_update");
            receiver = new severUploadBoardcastReceiver();
            getActivity().registerReceiver(receiver,filter);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            serverstatus.setText("Connected");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }


    private void getDatafromDatabase(){
        Cursor cursor = LocalFragment.myDb.getAllRows();
        while(cursor.moveToNext()){
            String time =  cursor.getString(1);
            String latitude= cursor.getString(2);
            String longitude = cursor.getString(3);
            refupload.child(time).child("date").setValue(time);
            refupload.child(time).child("netid").setValue("tz136");
            refupload.child(time).child("x").setValue(latitude);
            refupload.child(time).child("y").setValue(longitude);
        }
    }

    private class severUploadBoardcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String[] pieces = intent.getExtras().get("coordinates").toString().split("\t");
            refupload.child(pieces[0]).child("date").setValue(pieces[0]);
            refupload.child(pieces[0]).child("netid").setValue("tz136");
            refupload.child(pieces[0]).child("x").setValue(pieces[1]);
            refupload.child(pieces[0]).child("y").setValue(pieces[2]);
        }
    }

};

