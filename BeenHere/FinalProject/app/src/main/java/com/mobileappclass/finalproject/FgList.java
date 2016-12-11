package com.mobileappclass.finalproject;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class FgList extends Fragment implements ListView.OnItemClickListener {

    private List<User> mData = null;
    private Context mContext;
    private UserAdapter mAdapter = null;

    private ArrayList<Record> recordList;
    private FirebaseDatabase recordDatabase;
    private DatabaseReference recordDatabaseReference;

    private ListView listView;
    private myAdapter adapter;


    public FgList() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content2,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);
        listView = (ListView) getActivity().findViewById(R.id.list1);
        listView.setOnItemClickListener(this);
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
                adapter = new myAdapter(recordList,getActivity());
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                /*listener failed or was removed for security reasons*/
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Record item=(Record)adapter.getItem(position);
        Toast.makeText(getActivity(),"IN",Toast.LENGTH_SHORT).show();
        String time= item.getTime();
        String detail= item.getDetail();
        Double longitude= item.getLongitude();
        Double latitude=item.getLatitude();
        String title=item.getTitle();
        String userName=item.getUserName();
        Intent i=new Intent(getActivity(),DetailedActivity.class);
        i.putExtra("time",time);
        i.putExtra("detail",detail);
        i.putExtra("longitude",longitude);
        i.putExtra("latitude",latitude);
        i.putExtra("title",title);
        i.putExtra("userName",userName);
        startActivity(i);

    }
}
