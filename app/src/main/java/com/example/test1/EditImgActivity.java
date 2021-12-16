package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.test1.fragments.ProfileFragment;
import com.example.test1.functions.Loading;
import com.example.test1.networking.FunctionUserFAN;
import com.example.test1.ultilties.Constants;
import com.example.test1.ultilties.PreferenceManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EditImgActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btneditimg1, btneditimg2, btneditimg3, btneditimg4, btneditimg5, btneditimg6,
            btneditclose1, btneditclose2, btneditclose3, btneditclose4, btneditclose5, btneditclose6;
    TextView tvDone;
    ImageView editimg1, editimg2, editimg3, editimg4, editimg5, editimg6;
    File fileimg;
    private static final int REQUEST_CODE = 1;
    List<ImageView> imageViews = new ArrayList<>();
    List<ImageButton> imageButtons = new ArrayList<>();
    List<ImageButton> imageEditClose = new ArrayList<>();
    FunctionUserFAN functionUserFAN = new FunctionUserFAN();
    Loading loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_img);
        tvDone = findViewById(R.id.tvDone);
        btneditimg1 = findViewById(R.id.btneditimg1);
        btneditimg2 = findViewById(R.id.btneditimg2);
        btneditimg3 = findViewById(R.id.btneditimg3);
        btneditimg4 = findViewById(R.id.btneditimg4);
        btneditimg5 = findViewById(R.id.btneditimg5);
        btneditimg6 = findViewById(R.id.btneditimg6);
        btneditclose1 = findViewById(R.id.btneditclose1);
        btneditclose2 = findViewById(R.id.btneditclose2);
        btneditclose3 = findViewById(R.id.btneditclose3);
        btneditclose4 = findViewById(R.id.btneditclose4);
        btneditclose5 = findViewById(R.id.btneditclose5);
        btneditclose6 = findViewById(R.id.btneditclose6);
        editimg1 = findViewById(R.id.editimg1);
        editimg2 = findViewById(R.id.editimg2);
        editimg3 = findViewById(R.id.editimg3);
        editimg4 = findViewById(R.id.editimg4);
        editimg5 = findViewById(R.id.editimg5);
        editimg6 = findViewById(R.id.editimg6);

        imageViews.add(editimg1);
        imageViews.add(editimg2);
        imageViews.add(editimg3);
        imageViews.add(editimg4);
        imageViews.add(editimg5);
        imageViews.add(editimg6);
        imageButtons.add(btneditimg1);
        imageButtons.add(btneditimg2);
        imageButtons.add(btneditimg3);
        imageButtons.add(btneditimg4);
        imageButtons.add(btneditimg5);
        imageButtons.add(btneditimg6);
        imageEditClose.add(btneditclose1);
        imageEditClose.add(btneditclose2);
        imageEditClose.add(btneditclose3);
        imageEditClose.add(btneditclose4);
        imageEditClose.add(btneditclose5);
        imageEditClose.add(btneditclose6);

        btneditimg1.setOnClickListener(this);
        btneditimg2.setOnClickListener(this);
        btneditimg3.setOnClickListener(this);
        btneditimg4.setOnClickListener(this);
        btneditimg5.setOnClickListener(this);
        btneditimg6.setOnClickListener(this);
        btneditclose1.setOnClickListener(this);
        btneditclose2.setOnClickListener(this);
        btneditclose3.setOnClickListener(this);
        btneditclose4.setOnClickListener(this);
        btneditclose5.setOnClickListener(this);
        btneditclose6.setOnClickListener(this);

        loading = new Loading();

        getImages(HomeActivity.users.getImageUrl().size());

        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (data != null) {
                Uri uri = data.getData();
                fileimg = new File(getRealPathFromURI(uri));
                loading.show(getSupportFragmentManager(), "loading");
                functionUserFAN.updateImages(HomeActivity.users.getEmail(), fileimg, String.valueOf(fileimg), "false", EditImgActivity.this);
            }
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public void getImages(int size) {
        for (int i = 0; i < size; i++) {
            Glide.with(EditImgActivity.this).load(HomeActivity.users.getImageUrl().get(i)).into(imageViews.get(i));
            imageButtons.get(i).setVisibility(View.GONE);
            imageEditClose.get(i).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btneditimg1:
            case R.id.btneditimg2:
            case R.id.btneditimg3:
            case R.id.btneditimg4:
            case R.id.btneditimg5:
            case R.id.btneditimg6:
                pickImage();
                break;
            case R.id.btneditclose1:
                closeImage(0);
                break;
            case R.id.btneditclose2:
                closeImage(1);
                break;
            case R.id.btneditclose3:
                closeImage(2);
                break;
            case R.id.btneditclose4:
                closeImage(3);
                break;
            case R.id.btneditclose5:
                closeImage(4);
                break;
            case R.id.btneditclose6:
                closeImage(5);
                break;
        }
    }

    private void closeImage(int i) {
        loading.show(getSupportFragmentManager(), "loading");
        functionUserFAN.updateImages(HomeActivity.users.getEmail(), null, String.valueOf(HomeActivity.users.getImageUrl().get(i)), "true", EditImgActivity.this);
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE);
    }
}