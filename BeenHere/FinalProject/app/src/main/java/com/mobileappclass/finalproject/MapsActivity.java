package com.mobileappclass.finalproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private DatabaseReference refdownload;

    private GoogleMap mMap;
    private Location loc;

    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private static final int REQUEST_CODE_LOCATION = 2;
    private int runCount;

    private List<Record> recordList;

    public List<MarkerOptions> markerOptionses;
    public List<Marker> markers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.mobileappclass.finalproject.R.layout.activity_maps);
        recordList = new ArrayList<>();
        refdownload= FirebaseDatabase.getInstance().getReference("records");
        refdownload.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recordList.clear();

                for (DataSnapshot data:dataSnapshot.getChildren()){
                    String time=(String) data.child("time").getValue();
                    String detail=(String) data.child("detail").getValue();
                    Double longitude=(Double) data.child("longitude").getValue();
                    Double latitude=(Double) data.child("latitude").getValue();
                    String title=(String) data.child("title").getValue();
                    String userName=(String) data.child("userName").getValue();
                    recordList.add(new Record( longitude,  latitude, time,  title,  userName,  detail));
                }
            }




            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Serializable args = getIntent().getSerializableExtra("passList");
        //receive the list from mainActivity
        //recordList = (ArrayList<Record>) args;


        //test
        /*recordList.add(new Record(-74.15796536, 40.749, "play basketball", "Kobe", "2016-12-6 23:48:10pm", "who don't come becomes my grandchild"));
        recordList.add(new Record(-74.15696536, 40.75085, "play basketball", "Kobe", "2016-12-6 23:48:10pm", "who don't come becomes my grandchild"));
        recordList.add(new Record(-74.15596536, 40.75186, "play basketball", "Kobe", "2016-12-6 23:48:10pm", "who don't come becomes my grandchild"));
        recordList.add(new Record(-74.15496536, 40.75287, "play basketball", "Kobe", "2016-12-6 23:48:10pm", "who don't come becomes my grandchild"));
        recordList.add(new Record(-74.15396536, 40.75388447, "play basketball", "Kobe", "2016-12-6 23:48:10pm", "who don't come becomes my grandchild"));
        recordList.add(new Record(-74.15296536, 40.75489447, "play basketball", "Kobe", "2016-12-6 23:48:10pm", "who don't come becomes my grandchild"));*/

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // permission check
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
            //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_ACCESS_COARSE_LOCATION);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION);
        }

        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);


        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                int index = markers.indexOf(marker);
                Record record = recordList.get(index);
                Intent intent = new Intent(getApplicationContext(), DetailedActivity.class);
                intent.putExtra("longitude", record.longitude);
                intent.putExtra("latitude", record.latitude);
                intent.putExtra("title", record.title);
                intent.putExtra("userName", record.userName);
                intent.putExtra("time", record.time);
                intent.putExtra("detail", record.detail);
                startActivity(intent);
            }
        });

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (loc == null) // fall back to network if GPS is not available
        {
            loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        double currentLat = loc.getLatitude();
        double currentLng = loc.getLongitude();
        LatLng currentLocation = new LatLng(currentLat, currentLng);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, (float) 15.0));

        /*recordList.add(new Record(currentLng - 0.005, currentLat + 0.001, "play basketball", "Kobe", "2016-12-6 23:48:10pm", "who don't come becomes my grandchild"));
        recordList.add(new Record(currentLng + 0.004, currentLat - 0.002, "good things", "James", "2016-12-6 23:48:10pm", "who don't come becomes my grandchild"));
        recordList.add(new Record(currentLng - 0.003, currentLat + 0.003, "picnic", "Tracy", "2016-12-6 23:48:10pm", "who don't come becomes my grandchild"));
        recordList.add(new Record(currentLng + 0.002, currentLat - 0.004, "swimming", "Kobe", "2016-12-6 23:48:10pm", "who don't come becomes my grandchild"));
        recordList.add(new Record(currentLng - 0.001, currentLat - 0.005, "play basketball", "Kobe", "2016-12-6 23:48:10pm", "who don't come becomes my grandchild"));*/


        int numOfMarkers = recordList.size();
        markerOptionses = new ArrayList<>();
        markers = new ArrayList<>();
        for (Record record : recordList) {
            double longitude = record.longitude;
            double latitude = record.latitude;
            String title = record.title;
            String userName = record.userName;
            String time = record.time;
            LatLng newLatlng = new LatLng(latitude, longitude);
            markerOptionses.add(new MarkerOptions().position(newLatlng).title(title).snippet(time));
        }
        for (MarkerOptions markerOptions : markerOptionses) {
            Marker newMarker = mMap.addMarker(markerOptions);
            markers.add(newMarker);
        }

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                // add code here

                for(Marker marker : markers)
                {
                    marker.remove();
                }
                int numOfMarkers = recordList.size();
                markerOptionses = new ArrayList<>();
                markers = new ArrayList<>();
                for(Record record : recordList)
                {
                    double longitude = record.longitude;
                    double latitude = record.latitude;
                    String title = record.title;
                    String userName = record.userName;
                    String time = record.time;
                    LatLng newLatLng = new LatLng(latitude, longitude);
                    markerOptionses.add(new MarkerOptions().position(newLatLng).title(title).snippet(time));
                }
                for(MarkerOptions markerOptions : markerOptionses)
                {
                    Marker newMarker = mMap.addMarker(markerOptions);
                    markers.add(newMarker);
                }

                handler.postDelayed(this, 10000);
            }
        };
        handler.postDelayed(runnable, 10000);




        // test
//        final Handler handler = new Handler();
//        runCount = 0;// 全局变量，用于判断是否是第一次执行
//        Runnable runnable = new Runnable() {
//
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                if (runCount == 1) {// 第一次执行则关闭定时执行操作
//                // 在此处添加执行的代码
//                    for(Marker marker : markers)
//                    {
//                        marker.remove();
//                    }
//                    handler.removeCallbacks(this);
//                }
//                handler.postDelayed(this, 3000);
//                runCount++;
//            }
//
//        };
//        handler.postDelayed(runnable, 1000);// 打开定时器，执行操作

    }

}