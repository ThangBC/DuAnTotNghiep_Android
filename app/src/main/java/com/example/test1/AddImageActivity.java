package com.example.test1;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.test1.firebase.fcm.MyFirebaseMessagingService;
import com.example.test1.functions.Loading;
import com.example.test1.models.Users;
import com.example.test1.networking.FunctionUserFAN;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddImageActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton imgBack, btnaddimg1, btnaddimg2, btnaddimg3, btnaddimg4, btnaddimg5, btnaddimg6;
    Button btnContinue;
    ImageView addimg1, addimg2, addimg3, addimg4, addimg5, addimg6;
    List<File> image = new ArrayList<>();
    File fileimg;
    public static int REQUEST_CODE = 1;
    public static Loading loading;
    List<ImageView> imageViews = new ArrayList<>();
    List<ImageButton> imageButtons = new ArrayList<>();

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
        String token = sp.getString("token","");

        loading = new Loading();

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading.show(getSupportFragmentManager(),"loading");
                Users users = new Users(email, name, image, interest, birthday, sex, addressStudy, specialized, course,token);

                FunctionUserFAN functionUserVolley = new FunctionUserFAN();
                functionUserVolley.insertUser(AddImageActivity.this, users);

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (data != null) {
                Uri uri = data.getData();
                fileimg = new File(getRealPathFromURI(uri));
                image.add(fileimg);
                ListImg();
                Log.e("path", String.valueOf(fileimg));
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

    public void setLayout(ImageButton btn) {
        btn.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_close_24));
        btn.getLayoutParams().width = 45;
        btn.getLayoutParams().height = 45;
        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) btn.getLayoutParams();
        marginParams.setMargins(50, -260, 0, 0);
        btn.setLayoutParams(marginParams);
    }

    public void getLayout(ImageButton btn, ImageView img) {
        img.setImageBitmap(null);
        btn.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_add_24));
        btn.getLayoutParams().width = 100;
        btn.getLayoutParams().height = 100;
        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) btn.getLayoutParams();
        marginParams.setMargins(0, -180, 0, 0);
        btn.setLayoutParams(marginParams);
    }

    public void ListImg() {

        for (int i = 0; i < image.size(); i++) {
            imageViews.get(i).setImageBitmap(BitmapFactory.decodeFile(image.get(i).getAbsolutePath()));
            setLayout(imageButtons.get(i));
            if (i < 5) {
                getLayout(imageButtons.get(i + 1), imageViews.get(i + 1));
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnaddimg1:
                pickImage(1, 0);
                break;
            case R.id.btnaddimg2:
                pickImage(2, 1);
                break;
            case R.id.btnaddimg3:
                pickImage(3, 2);
                break;
            case R.id.btnaddimg4:
                pickImage(4, 3);
                break;
            case R.id.btnaddimg5:
                pickImage(5, 4);
                break;
            case R.id.btnaddimg6:
                pickImage(6, 5);
                break;
        }
    }

    private void pickImage(int number, int i) {
        if (image.size() >= number) {
            if (image.size() == number) {
                getLayout(btnaddimg1, addimg1);
            }
            image.remove(i);
            ListImg();
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }
}