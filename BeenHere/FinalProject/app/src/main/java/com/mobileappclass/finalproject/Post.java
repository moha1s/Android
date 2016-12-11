package com.mobileappclass.finalproject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Post extends Activity implements View.OnClickListener {

    private List<Bitmap> data = new ArrayList<Bitmap>();
    private GridView mGridView;
    private String photoPath;
    private PostAdapter adapter;
    private EditText tittle_et;
    private EditText content_et;
    private List<Record> recordList;
    private FirebaseDatabase recordDatabase;
    private DatabaseReference recordDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        tittle_et = (EditText) findViewById(R.id.tittle_et);
        content_et = (EditText) findViewById(R.id.content_et);
        recordDatabase = FirebaseDatabase.getInstance();
        recordDatabaseReference = recordDatabase.getReference("records");
        addValueEventListener(recordDatabaseReference);

        Bitmap bp = BitmapFactory.decodeResource(getResources(), R.drawable.add);
        data.add(bp);

        mGridView = (GridView) findViewById(R.id.gridView1);

        adapter = new PostAdapter(getApplicationContext(), data, mGridView);
        mGridView.setAdapter(adapter);

        mGridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (data.size() == 10) {
                    Toast.makeText(Post.this, "9 Images Already!", Toast.LENGTH_SHORT).show();
                } else {
                    if (position == data.size() - 1) {
                        Toast.makeText(Post.this, "Add Images", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, 0x1);
                    } else {
                        Toast.makeText(Post.this, "Press the Number " + (position + 1) + " image", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        mGridView.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dialog(position);
                return true;
            }
        });

        Button postback = (Button) findViewById(R.id.postback);
        postback.setOnClickListener(this);
    }

    private void addValueEventListener(final DatabaseReference friendsReference) {
        friendsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recordList = new ArrayList<>();

                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                while (iterator.hasNext()) {

                    Record value = iterator.next().getValue(Record.class);

                    recordList.add(value);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                /*listener failed or was removed for security reasons*/
            }
        });

    }

    public void AddItem(View view) {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location loc;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        String postTitle = tittle_et.getText().toString();
        String postContent = content_et.getText().toString();
        SharedPreferences name = getSharedPreferences("mysp",MODE_PRIVATE);
        String username = (name.getString("username",""));
        String postUsername =username;
        if (loc == null) // fall back to network if GPS is not available
        {
            loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        double postLongitude = loc.getLongitude();
        double postLatitude = loc.getLatitude();
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.US);
        String postTime = dateFormat.format( now );
        String key = recordDatabaseReference.push().getKey();
        Record record = new Record(postLongitude,postLatitude, postTime, postTitle, postUsername, postContent);
        recordDatabaseReference.child(key).setValue(record);
        Toast.makeText(getApplicationContext(), "Post has been sent!",
                Toast.LENGTH_SHORT).show();



    }

    protected void dialog(final int position) {
        AlertDialog.Builder builder = new Builder(Post.this);
        builder.setMessage("ȷ���Ƴ������ͼƬ��");
        builder.setTitle("��ʾ");
        builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                data.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    @Override
    public void onClick(View view) {
        Intent i=new Intent(this,MainActivity.class);
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x1 && resultCode == RESULT_OK) {
            if (data != null) {

                ContentResolver resolver = getContentResolver();
                try {
                    Uri uri = data.getData();

                    String[] proj = { MediaStore.Images.Media.DATA };
                    Cursor cursor = managedQuery(uri, proj, null, null, null);

                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();

                    photoPath = cursor.getString(column_index);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(photoPath)) {
            Bitmap newBp = BitmapUtils.decodeSampledBitmapFromFd(photoPath, 300, 300);
            data.remove(data.size() - 1);
            Bitmap bp = BitmapFactory.decodeResource(getResources(), R.drawable.add);
            data.add(newBp);
            data.add(bp);
            photoPath = null;
            adapter.notifyDataSetChanged();
        }
    }






}
