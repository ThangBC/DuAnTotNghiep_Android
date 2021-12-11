package com.example.test1.signupactivities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.test1.R;
import com.example.test1.functions.Loading;
import com.example.test1.models.Users;
import com.example.test1.networking.FunctionUserFAN;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddImageActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton imgBack, btnaddimg1, btnaddimg2, btnaddimg3, btnaddimg4, btnaddimg5, btnaddimg6, btnclose1, btnclose2, btnclose3, btnclose4, btnclose5, btnclose6;
    Button btnContinue;
    ImageView addimg1, addimg2, addimg3, addimg4, addimg5, addimg6;
    public static final int REQUEST_CODE = 1;

    List<ImageView> imageViews = new ArrayList<>();
    List<ImageButton> imageButtons = new ArrayList<>();
    List<ImageButton> imageButtonsClose = new ArrayList<>();
    List<File> image = new ArrayList<>();
    List<Uri> uriList = new ArrayList<>();

    FunctionUserFAN functionUserVolley;
    Loading loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);
        imgBack = findViewById(R.id.imgBack);
        btnContinue = findViewById(R.id.btnContinue);
        btnaddimg1 = findViewById(R.id.btnaddimg1);
        btnaddimg2 = findViewById(R.id.btnaddimg2);
        btnaddimg3 = findViewById(R.id.btnaddimg3);
        btnaddimg4 = findViewById(R.id.btnaddimg4);
        btnaddimg5 = findViewById(R.id.btnaddimg5);
        btnaddimg6 = findViewById(R.id.btnaddimg6);
        btnclose1 = findViewById(R.id.btnclose1);
        btnclose2 = findViewById(R.id.btnclose2);
        btnclose3 = findViewById(R.id.btnclose3);
        btnclose4 = findViewById(R.id.btnclose4);
        btnclose5 = findViewById(R.id.btnclose5);
        btnclose6 = findViewById(R.id.btnclose6);
        addimg1 = findViewById(R.id.addimg1);
        addimg2 = findViewById(R.id.addimg2);
        addimg3 = findViewById(R.id.addimg3);
        addimg4 = findViewById(R.id.addimg4);
        addimg5 = findViewById(R.id.addimg5);
        addimg6 = findViewById(R.id.addimg6);

        imageViews.add(addimg1);
        imageViews.add(addimg2);
        imageViews.add(addimg3);
        imageViews.add(addimg4);
        imageViews.add(addimg5);
        imageViews.add(addimg6);
        imageButtons.add(btnaddimg1);
        imageButtons.add(btnaddimg2);
        imageButtons.add(btnaddimg3);
        imageButtons.add(btnaddimg4);
        imageButtons.add(btnaddimg5);
        imageButtons.add(btnaddimg6);
        imageButtonsClose.add(btnclose1);
        imageButtonsClose.add(btnclose2);
        imageButtonsClose.add(btnclose3);
        imageButtonsClose.add(btnclose4);
        imageButtonsClose.add(btnclose5);
        imageButtonsClose.add(btnclose6);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("MyToken", Context.MODE_PRIVATE);
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String name = intent.getStringExtra("name");
        String birthday = intent.getStringExtra("birthday");
        String sex = intent.getStringExtra("sex");
        String specialized = intent.getStringExtra("specialized");
        String course = intent.getStringExtra("course");
        String addressStudy = intent.getStringExtra("addressStudy");
        ArrayList<String> interest = intent.getStringArrayListExtra("interest");
        String token = sp.getString("token", "");

        loading = new Loading();

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading.show(getSupportFragmentManager(), "loading");
                Users users = new Users(email, name, image, interest, birthday, sex, addressStudy, specialized, course, token);
                functionUserVolley = new FunctionUserFAN();
                functionUserVolley.insertUser(AddImageActivity.this, users, loading);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putStringArrayListExtra("result", interest);
                setResult(RESULT_OK, resultIntent);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        btnaddimg1.setOnClickListener(this);
        btnaddimg2.setOnClickListener(this);
        btnaddimg3.setOnClickListener(this);
        btnaddimg4.setOnClickListener(this);
        btnaddimg5.setOnClickListener(this);
        btnaddimg6.setOnClickListener(this);
        btnclose1.setOnClickListener(this);
        btnclose2.setOnClickListener(this);
        btnclose3.setOnClickListener(this);
        btnclose4.setOnClickListener(this);
        btnclose5.setOnClickListener(this);
        btnclose6.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            Uri uri = data.getData();
            image.add(new File(getRealPathFromURI(uri)));
            Log.e("image",getRealPathFromURI(uri));
            uriList.add(uri);
            ListImg();
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

    public void ListImg() {
        for (int i = 0; i < image.size(); i++) {
            Glide.with(this).load(uriList.get(i)).into(imageViews.get(i));
            imageButtons.get(i).setVisibility(View.GONE);
            imageButtonsClose.get(i).setVisibility(View.VISIBLE);
            if (i < 5) {
                imageViews.get(i + 1).setImageURI(null);
                imageButtons.get(i + 1).setVisibility(View.VISIBLE);
                imageButtonsClose.get(i + 1).setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnaddimg1:
            case R.id.btnaddimg2:
            case R.id.btnaddimg3:
            case R.id.btnaddimg4:
            case R.id.btnaddimg5:
            case R.id.btnaddimg6:
                pickImage();
                break;
            case R.id.btnclose1:
                closeImage(1, 0);
                break;
            case R.id.btnclose2:
                closeImage(2, 1);
                break;
            case R.id.btnclose3:
                closeImage(3, 2);
                break;
            case R.id.btnclose4:
                closeImage(4, 3);
                break;
            case R.id.btnclose5:
                closeImage(5, 4);
                break;
            case R.id.btnclose6:
                closeImage(6, 5);
                break;
        }
    }

    private void closeImage(int number, int i) {
        if (image.size() == number) {
            btnaddimg1.setVisibility(View.VISIBLE);
            addimg1.setImageBitmap(null);
            btnclose1.setVisibility(View.GONE);
        }
        image.remove(i);
        uriList.remove(i);
        ListImg();
    }

    private void pickImage() {
        if(ContextCompat.checkSelfPermission(AddImageActivity.this
                ,Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_CODE);
        }else {
            ActivityCompat.requestPermissions(AddImageActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE);
            } else {
                Toast.makeText(this, "Thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }
}