package com.example.chelsea.hw2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Scanner;
/**
 * Created by Chelsea on 10/26/16.
 */

public class EditProfileActivity extends AppCompatActivity {
    public static final String CHECK_LIST = "CheckList";
    private EditText name;
    private EditText number;
    private ListView lvContacts;
    private ArrayList<Contacts> mContactsList;
    private ContactsListAdapter3 clAdapter;
    private boolean []checkstatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        name = (EditText) findViewById(R.id.edit_name);
        number = (EditText) findViewById(R.id.edit_number);
        lvContacts = (ListView) findViewById(R.id.listView22);

        mContactsList = new ArrayList<>();
        readData();
        if(savedInstanceState!=null) {
            checkstatus = savedInstanceState.getBooleanArray(CHECK_LIST);
            if (checkstatus != null) {
                for (int i = 0; i < checkstatus.length; i++) {
                    mContactsList.get(i).setSelected(checkstatus[i]);
                }
            }
        }
        clAdapter = new ContactsListAdapter3(this, R.layout.list3, mContactsList);
        lvContacts.setAdapter(clAdapter);




    }


    public void addPerson (View v) {
        Intent intent = new Intent(EditProfileActivity.this,MainActivity.class);
        String contactname = name.getText().toString();
        String contactnumber = number.getText().toString();
        String contactrelationship = "";

        if(mContactsList.size()!=0) {
            for (int i = 0; i < mContactsList.size(); i++) {
                if (mContactsList.get(i).isSelected()) {
                    String addrelationship = mContactsList.get(i).getRelationship();
                    if(addrelationship.equals("")){
                        addrelationship += contactname;
                    }
                    else {
                        addrelationship += "\b" + contactname;
                    }
                    mContactsList.get(i).setRelationship(addrelationship);
                    contactrelationship += mContactsList.get(i).getName() + "\b";
                }
            }
            if(contactrelationship.length()!=0) {
                contactrelationship = contactrelationship.substring(0, contactrelationship.length() - 1);
            }
        }
        mContactsList.add(new Contacts(contactname,contactnumber,contactrelationship));
        String data="";
        for (int i = 0; i < mContactsList.size()-1; i++) {
            data += mContactsList.get(i).getName() + "\t" + mContactsList.get(i).getNumber() + "\t" + mContactsList.get(i).getRelationship() + "\n";
        }
        data += mContactsList.get(mContactsList.size() - 1).getName() + "\t" + mContactsList.get(mContactsList.size() - 1).getNumber() + "\t" + mContactsList.get(mContactsList.size() - 1).getRelationship();

        //name.setText("");
        //number.setText("");
        //for (int i = 0; i < mContactsList.size(); i++) {
        //    mContactsList.get(i).setSelected(false);
        //}
        //clAdapter.notifyDataSetChanged();
        //lvContacts.setAdapter(clAdapter);
        saveData();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            FragmentMain frag = (FragmentMain) this.getFragmentManager().findFragmentById(R.id.mainActivityFragment);
            frag.updateFragment(data);
            Intent intent1= new Intent(EditProfileActivity.this,EditProfileActivity.class);
            startActivity(intent1);
        }
        else {
            intent.putExtra("databack",data);
            startActivity(intent);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        checkstatus = new boolean[mContactsList.size()];
        for (int i = 0; i < mContactsList.size(); i++) {
            checkstatus[i]=mContactsList.get(i).isSelected();
        }
        outState.putBooleanArray(CHECK_LIST,checkstatus);
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
        Toast.makeText(this,"Saved!", Toast.LENGTH_SHORT).show();
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

    public class ContactsListAdapter3 extends ArrayAdapter<Contacts> {
        private ArrayList<Contacts> mContactList;

        public ContactsListAdapter3(Context context, int textViewResourceId, ArrayList<Contacts> contactsList) {
            super(context, textViewResourceId, contactsList);
            this.mContactList = new ArrayList<>();
            this.mContactList.addAll(contactsList);
        }

        private class ViewHolder {
            TextView name;
            CheckBox check;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.list3, null);
                holder.name = (TextView) convertView.findViewById(R.id.contacts_Name3);
                holder.check = (CheckBox) convertView.findViewById(R.id.checkBox3);
                convertView.setTag(holder);
                holder.check.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        if(!mContactList.get(position).isSelected()) {
                            mContactList.get(position).setSelected(true);
                            int x=position;
                            for(int i=position-1;i>=0;i--){
                                if(!mContactList.get(i).isSelected()){
                                    mContactList.add(i,mContactList.get(x));
                                    mContactList.remove(x+1);
                                    x--;

                                }
                            }
                        }
                        else{
                            mContactList.get(position).setSelected(false);
                            int x=position;
                            for(int i=position+1;i<mContactList.size();i++){
                                if(mContactList.get(i).isSelected()){
                                    mContactList.add(x,mContactList.get(i));
                                    mContactList.remove(i+1);
                                    x++;

                                }
                            }
                        }

                        clAdapter.notifyDataSetChanged();
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }


            holder.name.setText(mContactList.get(position).getName());
            holder.check.setChecked(mContactList.get(position).isSelected());
            return convertView;

        }

    }


}
