package com.example.chelsea.hw2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Chelsea on 10/26/16.
 */

public class ContactsListAdapter extends ArrayAdapter<Contacts> {
    private ArrayList<Contacts> mContactList;

    public ContactsListAdapter(Context context, int textViewResourceId, ArrayList<Contacts> contactsList) {
        super(context, textViewResourceId, contactsList);
        this.mContactList = new ArrayList<>();
        this.mContactList.addAll(contactsList);
    }

    private class ViewHolder {
        TextView name;
        CheckBox check;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list1, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.contacts_Name);
            holder.check = (CheckBox) convertView.findViewById(R.id.checkBox);
            convertView.setTag(holder);
            holder.check.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    Contacts contacts = (Contacts) cb.getTag();
                    contacts.setSelected(!contacts.isSelected());
                    Toast.makeText(getContext(),"Clicked on Checkbox: " + contacts.getName(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Contacts contacts = mContactList.get(position);
        holder.name.setText(contacts.getName());
        holder.check.setChecked(contacts.isSelected());
        holder.check.setTag(contacts);
        return convertView;

    }

}
