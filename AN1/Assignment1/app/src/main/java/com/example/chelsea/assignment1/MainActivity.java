package com.example.chelsea.assignment1;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.widget.ListView;
import android.widget.ArrayAdapter;
public class MainActivity extends AppCompatActivity {
    EditText ed1;
    EditText ed2;
    Button bt1;
    private ListView list1;
    ArrayAdapter<String> aa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
