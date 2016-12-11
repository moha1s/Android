package com.mobileappclass.finalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chelsea on 12/6/16.
 */

public class SharedHelper {

    private Context mContext;

    public SharedHelper() {
    }

    public SharedHelper(Context mContext) {
        this.mContext = mContext;
    }



    public void save(String username, String profile) {
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", username);
        editor.putString("profile", profile);
        editor.commit();
        Toast.makeText(mContext, "Saved", Toast.LENGTH_SHORT).show();
    }

    //定义一个读取SP文件的方法
    public Map<String, String> read() {
        Map<String, String> data = new HashMap<>();
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        data.put("username", sp.getString("username", ""));
        data.put("profile", sp.getString("profile", ""));
        return data;
    }
}
