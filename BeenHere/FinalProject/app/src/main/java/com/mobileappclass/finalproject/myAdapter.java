package com.mobileappclass.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Shang Yang on 2016/12/8.
 */

public class myAdapter extends BaseAdapter {
    public ArrayList<Record> list;
    private Context context;
    private LayoutInflater inflater = null;

    public myAdapter(ArrayList<Record> list, Context context) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_item, null);
            holder.title = (TextView) convertView.findViewById(R.id.user_name);
            holder.content= (TextView) convertView.findViewById(R.id.content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(list.get(position).getUserName());
        holder.content.setText(list.get(position).getTitle());

        return convertView;
    }


    public final class ViewHolder{
        public TextView title;
        public TextView content;
    }
}
