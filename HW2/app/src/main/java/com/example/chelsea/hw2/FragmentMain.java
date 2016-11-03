package com.example.chelsea.hw2;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Chelsea on 10/26/16.
 */

public class FragmentMain extends Fragment {
    public static final String CHECK_LIST = "CheckList";
    private ListView lvContacts;
    private Button deleteButton;
    private Button addButton;
    private ContactsListAdapter clAdapter1;
    private ArrayList<Contacts> mContactsList;
    private Activity myActivity;
    private boolean []checkstatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myActivity = getActivity();
        deleteButton = (Button)myActivity.findViewById(R.id.delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteContacts();
            }
        });
        addButton = (Button)myActivity.findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContactsActivity();
            }
        });
        lvContacts = (ListView)myActivity.findViewById(R.id.listView);
        mContactsList = new ArrayList<>();
        readData();
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
                Intent intent = new Intent(getActivity(),ShowProfileActivity.class);
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


    public void addContactsActivity() {
        Intent intent = new Intent(getActivity(),EditProfileActivity.class);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        checkstatus = new boolean[mContactsList.size()];
        for (int i = 0; i < mContactsList.size(); i++) {
            checkstatus[i]=mContactsList.get(i).isSelected();
        }
        outState.putBooleanArray(CHECK_LIST,checkstatus);
    }

    public void deleteContacts() {
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
        SharedPreferences shared = myActivity.getSharedPreferences("contactInfo", Context.MODE_PRIVATE);
        String read_data = shared.getString("contactdata","");
        Intent intent = new Intent(getActivity(),EditProfileActivity.class);
        intent.putExtra("data",read_data);
        startActivity(intent);
    }

    public void populateList() {
        clAdapter1 = new ContactsListAdapter(getActivity(), R.layout.list1, mContactsList);
        lvContacts.setAdapter(clAdapter1);
    }

    public void readData () {
        SharedPreferences shared = myActivity.getSharedPreferences("contactInfo", Context.MODE_PRIVATE);
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

    public void saveData () {
        SharedPreferences shared = myActivity.getSharedPreferences("contactInfo", Context.MODE_PRIVATE);
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
        Toast.makeText(getActivity(),"Saved!", Toast.LENGTH_SHORT).show();
    }

    public void updateFragment(String data){

        String read_data=data;

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
        populateList();
        saveData();
    }

}

