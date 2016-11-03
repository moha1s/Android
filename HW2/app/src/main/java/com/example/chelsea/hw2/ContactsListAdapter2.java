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

public class ContactsListAdapter2 extends ArrayAdapter<Contacts> {
    private ArrayList<Contacts> mContactList;

    public ContactsListAdapter2(Context context, int textViewResourceId, ArrayList<Contacts> contactsList) {
        super(context, textViewResourceId, contactsList);
        this.mContactList = new ArrayList<>();
        this.mContactList.addAll(contactsList);
    }

    private class ViewHolder {
        TextView name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list2, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.contacts_Name2);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Contacts contacts = mContactList.get(position);
        holder.name.setText(contacts.getName());
        return convertView;

    }

}
