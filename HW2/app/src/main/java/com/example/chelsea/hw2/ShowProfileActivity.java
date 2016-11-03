package com.example.chelsea.hw2;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Scanner;
/**
 * Created by Chelsea on 10/26/16.
 */

public class ShowProfileActivity extends AppCompatActivity {
    private TextView nameText;
    private TextView numberText;
    private ListView lvContacts;
    private ArrayList<Contacts> mContactsList;
    private ArrayList<Contacts> relationshiplist;
    private ContactsListAdapter2 clAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);
        nameText = (TextView) findViewById(R.id.edit_name);
        numberText = (TextView) findViewById(R.id.edit_number);
        lvContacts = (ListView) findViewById(R.id.listView2);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String number = intent.getStringExtra("number");
        String relationship = intent.getStringExtra("relationship");
        String read_data = intent.getStringExtra("data");
        mContactsList = new ArrayList<>();
        if(read_data!="") {
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

        nameText.setText(name);
        numberText.setText(number);



        relationshiplist = new ArrayList<>();
        if(relationship!="") {
            String[] people = relationship.split("\b");

            for (int i = 0; i < people.length; i++) {
                for (int j = 0; j < mContactsList.size(); j++) {
                    if (people[i].equals(mContactsList.get(j).getName()) ) {
                        relationshiplist.add(mContactsList.get(j));
                    }
                }
            }
        }
        clAdapter = new ContactsListAdapter2(this, R.layout.list2, relationshiplist);
        lvContacts.setAdapter(clAdapter);
        lvContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contacts contacts = (Contacts) parent.getItemAtPosition(position);
                Intent intent = new Intent(ShowProfileActivity.this,ShowProfileActivity.class);
                intent.putExtra("name",contacts.getName());
                intent.putExtra("number",contacts.getNumber());
                intent.putExtra("relationship",contacts.getRelationship());
                String data="";
                if(mContactsList.size()!=0) {
                    for (int i = 0; i < mContactsList.size() - 1; i++) {
                        data += mContactsList.get(i).getName() + "\t" + mContactsList.get(i).getNumber() + "\t" + mContactsList.get(i).getRelationship() + "\n";
                    }
                    data += mContactsList.get(mContactsList.size() - 1).getName() + "\t" + mContactsList.get(mContactsList.size() - 1).getNumber() + "\t" + mContactsList.get(mContactsList.size() - 1).getRelationship();
                }
                intent.putExtra("data",data);
                startActivity(intent);
            }
        });

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            FragmentMain frag = (FragmentMain) this.getFragmentManager().findFragmentById(R.id.mainActivityFragment);
        }


    }
}
