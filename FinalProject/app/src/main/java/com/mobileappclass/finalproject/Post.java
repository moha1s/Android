package com.mobileappclass.finalproject;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.content.Intent;
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

public class Post extends Activity implements View.OnClickListener {

    private List<Bitmap> data = new ArrayList<Bitmap>();
    private GridView mGridView;
    private String photoPath;
    private PostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

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

        Button postback=(Button)findViewById(R.id.postback);
        postback.setOnClickListener(this);
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
        startActivity(i);

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
