package com.mobileappclass.finalproject;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.content.Intent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


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

    //Firebase Configuration
    private DatabaseReference recordDatabaseReference;
    private FirebaseDatabase recordDatabase;
    private ArrayList<Record> recordList;

    private static final int REQUEST_CODE_LOCATION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        fManager = getFragmentManager();
        bindViews();
        txt_home.performClick();
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);
        }

        recordDatabase = FirebaseDatabase.getInstance();
        recordDatabaseReference = recordDatabase.getReference("records");
        addValueEventListener(recordDatabaseReference);
    }

    private void addValueEventListener(final DatabaseReference friendsReference) {
        friendsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recordList = new ArrayList<>();

                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                while (iterator.hasNext()) {

                    Record value = iterator.next().getValue(Record.class);

                    recordList.add(value);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                /*listener failed or was removed for security reasons*/
            }
        });
    }

    private void bindViews() {
        txt_home = (TextView) findViewById(R.id.txt_home);
        txt_list = (TextView) findViewById(R.id.txt_list);
        txt_place = (TextView) findViewById(R.id.txt_place);
        txt_me = (TextView) findViewById(R.id.txt_me);

        //hehe

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

    public void on_content1_button_click(View view)
    {
        Intent intent = new Intent(this, MapsActivity.class);
        // TODO: 12/6/16 send type name to map activity
        //intent.putExtra("passList", recordList);
        startActivity(intent);
    }
}