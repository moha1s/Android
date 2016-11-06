package com.mobileappclass.assignment3;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
public class MainActivity extends ActionBarActivity {
    FragmentManager fragmentManager;
    DBAdapter myDb;
    Fragment fragment;
    private LocalFragment local;
    private ServerFragment server;
    private QueryFragment query;
    private FragmentTransaction transaction;

    @Override
    protected void onStart() {
        super.onStart();
        Intent i =new Intent(this,GPS_Service.class);
        startService(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        local =(LocalFragment) getFragmentManager().findFragmentById(R.id.localActivityFragment);
        server = (ServerFragment) getFragmentManager().findFragmentById(R.id.serverActivityFragment);
        query = (QueryFragment) getFragmentManager().findFragmentById(R.id.queryFragment);
        transaction = getFragmentManager().beginTransaction();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportActionBar().hide();
        }
        else{
            getSupportActionBar().show();
            transaction.show(local);
            transaction.hide(server);
            transaction.hide(query);
            transaction.commit();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        transaction = getFragmentManager().beginTransaction();
        if (item.getItemId() == R.id.action_local) {
            transaction.show(local);
            transaction.hide(server);
            transaction.hide(query);
            transaction.commit();
        }
        else if (item.getItemId() == R.id.action_server) {
            transaction.hide(local);
            transaction.show(server);
            transaction.hide(query);
            transaction.commit();
        }
        else if (item.getItemId() == R.id.action_query){
            transaction.hide(local);
            transaction.hide(server);
            transaction.show(query);
            transaction.commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent i =new Intent(this,GPS_Service.class);
        stopService(i);
    }

    private void openDB() {
        myDb = new DBAdapter(this);
        myDb.open();
    }

    private void closeDB() {
        myDb.close();
    }
}