package com.mobileappclass.finalproject;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import static android.content.Context.MODE_PRIVATE;


public class FgPlace extends Fragment {

    private String content;
    private ArrayList<Record> recordList2;
    private ArrayList<Record> selectedList;
    private FirebaseDatabase recordDatabase;
    private DatabaseReference recordDatabaseReference;

    private ListView listView;
    private myAdapter2 adapter;
    public FgPlace() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_content3,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);
        listView = (ListView) getActivity().findViewById(R.id.list2);
        recordDatabase = FirebaseDatabase.getInstance();
        recordDatabaseReference = recordDatabase.getReference("records");

        addValueEventListener(recordDatabaseReference);

    }
    private void addValueEventListener(final DatabaseReference friendsReference) {
        friendsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recordList2 = new ArrayList<>();
                selectedList = new ArrayList<>();
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                while (iterator.hasNext()) {

                    Record value = iterator.next().getValue(Record.class);

                    recordList2.add(value);
                }

                SharedPreferences name = getActivity().getSharedPreferences("mysp",MODE_PRIVATE);
                String username = (name.getString("username",""));

                for(Record record: recordList2)
                {
                    if(record.getUserName().equals(username))
                        selectedList.add(record);
                }

                if(selectedList == null) {
                    Toast.makeText(getActivity().getApplicationContext(), "you have not posted anything yet!",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    adapter = new myAdapter2(selectedList, getActivity());
                    listView.setAdapter(adapter);
                }

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) {

                        Intent mIntent3 = new Intent();
                        mIntent3.setClass(getActivity(), DetailedActivity.class);
                        mIntent3.putExtra("longitude",selectedList.get(position).getLongitude());
                        mIntent3.putExtra("latitude",selectedList.get(position).getLatitude());
                        mIntent3.putExtra("title",selectedList.get(position).getTitle());
                        mIntent3.putExtra("userName",selectedList.get(position).getUserName());
                        mIntent3.putExtra("time",selectedList.get(position).getTime());
                        mIntent3.putExtra("detail",selectedList.get(position).getDetail());
                        startActivity(mIntent3);

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                /*listener failed or was removed for security reasons*/
            }
        });
    }

}
