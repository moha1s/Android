package com.example.chelsea.hw2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    public static final String CHECK_LIST = "CheckList";
    private ListView lvContacts;
    private ContactsListAdapter clAdapter;
    private ArrayList<Contacts> mContactsList;
    private boolean []checkstatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvContacts = (ListView)findViewById(R.id.listView);
        mContactsList = new ArrayList<>();
        readData();
        addContacts();
        if(savedInstanceState!=null) {
            checkstatus = savedInstanceState.getBooleanArray(CHECK_LIST);
        }
        if(checkstatus!=null){
            for(int i=0;i<checkstatus.length;i++){
                mContactsList.get(i).setSelected(checkstatus[i]);
            }
        }
        populateList();
        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contacts contacts = (Contacts) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this,ShowProfileActivity.class);
                intent.putExtra("name",contacts.getName());
                intent.putExtra("number",contacts.getNumber());
                intent.putExtra("relationship",contacts.getRelationship());
                String data="";
                if(mContactsList.size()!=0) {
                    for (int i = 0; i < mContactsList.size()-1; i++) {
                        data += mContactsList.get(i).getName() + "\t" + mContactsList.get(i).getNumber() + "\t" + mContactsList.get(i).getRelationship() + "\n";
                    }
                    data += mContactsList.get(mContactsList.size() - 1).getName() + "\t" + mContactsList.get(mContactsList.size() - 1).getNumber() + "\t" + mContactsList.get(mContactsList.size() - 1).getRelationship();
                }
                intent.putExtra("data",data);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Toast.makeText(this,Integer.toString(mContactsList.size()),Toast.LENGTH_SHORT).show();
        checkstatus = new boolean[mContactsList.size()];
        for (int i = 0; i < mContactsList.size(); i++) {
            checkstatus[i]=mContactsList.get(i).isSelected();
        }
        outState.putBooleanArray(CHECK_LIST,checkstatus);
    }


    private void populateList() {
        clAdapter = new ContactsListAdapter(this, R.layout.list1, mContactsList);
        lvContacts.setAdapter(clAdapter);
    }

    public void deleteContacts(View v) {
        for(int i=0;i<mContactsList.size();i++){
            if(mContactsList.get(i).isSelected()) {
                for(int j=0;j<mContactsList.size();j++) {

                    String modifyrelationship= mContactsList.get(j).getRelationship();
                    if(modifyrelationship!="") {
                        String[] people = modifyrelationship.split("\b");
                        modifyrelationship="";
                        for(int x=0;x<people.length;x++){
                            if(!people[x].equals(mContactsList.get(i).getName())) {
                                modifyrelationship += people[x] + "\b";
                            }
                        }
                        if(modifyrelationship.length()!=0) {
                            modifyrelationship = modifyrelationship.substring(0, modifyrelationship.length() - 1);
                        }
                    }
                    mContactsList.get(j).setRelationship(modifyrelationship);
                }
                mContactsList.remove(i);
                i--;
            }
        }
        saveData();
        populateList();
    }

    public void addContactsActivity(View v) {
        Intent intent = new Intent(MainActivity.this,EditProfileActivity.class);
        String data="";
        if(mContactsList.size()!=0) {
            for (int i = 0; i < mContactsList.size()-1; i++) {
                data += mContactsList.get(i).getName() + "\t" + mContactsList.get(i).getNumber() + "\t" + mContactsList.get(i).getRelationship() + "\n";
            }
            data += mContactsList.get(mContactsList.size() - 1).getName() + "\t" + mContactsList.get(mContactsList.size() - 1).getNumber() + "\t" + mContactsList.get(mContactsList.size() - 1).getRelationship();
        }
        intent.putExtra("data",data);
        startActivity(intent);
    }

    public void addContacts() {
        Intent intent_edit = getIntent();
        String read_data = intent_edit.getStringExtra("databack");
        if(read_data!=null) {
            mContactsList.clear();
            Scanner scanner = new Scanner(read_data);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] pieces = line.split("\t");
                if(pieces.length==2){
                    mContactsList.add(new Contacts(pieces[0], pieces[1], ""));
                }
                else {
                    mContactsList.add(new Contacts(pieces[0], pieces[1], pieces[2]));
                }
            }
            scanner.close();
        }
        saveData();
    }

    public void saveData () {
        SharedPreferences shared = getSharedPreferences("contactInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        String data="";

        if(mContactsList.size()!=0) {
            for (int i = 0; i < mContactsList.size()-1; i++) {
                data += mContactsList.get(i).getName() + "\t" + mContactsList.get(i).getNumber() + "\t" + mContactsList.get(i).getRelationship() + "\n";
            }
            data += mContactsList.get(mContactsList.size() - 1).getName() + "\t" + mContactsList.get(mContactsList.size() - 1).getNumber() + "\t" + mContactsList.get(mContactsList.size() - 1).getRelationship();
        }
        editor.putString("contactdata", data);
        editor.commit();
        Toast.makeText(this,"Added!", Toast.LENGTH_SHORT).show();
    }

    public void readData () {
        SharedPreferences shared = this.getSharedPreferences("contactInfo", Context.MODE_PRIVATE);
        String read_data = shared.getString("contactdata","");
        if(read_data!="") {
            mContactsList.clear();
            Scanner scanner = new Scanner(read_data);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] pieces = line.split("\t");
                if (pieces.length == 2) {
                    mContactsList.add(new Contacts(pieces[0], pieces[1], ""));
                } else{
                    mContactsList.add(new Contacts(pieces[0], pieces[1], pieces[2]));
                }
            }
            scanner.close();
        }
    }
}
