package com.example.test1;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.test1.models.InfoRegister;
import com.example.test1.volleys.FunctionUserVolley;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddImageActivity extends AppCompatActivity {
    ImageButton imgBack,btnaddimg1,btnaddimg2,btnaddimg3,btnaddimg4,btnaddimg5,btnaddimg6;
    Button btnContinue;
    ImageView addimg1,addimg2,addimg3,addimg4,addimg5,addimg6;
    List<File> image = new ArrayList<>();

    private static final int REQUEST_CODE = 1;

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

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String name = intent.getStringExtra("name");
        String birthday = intent.getStringExtra("birthday");
        String sex = intent.getStringExtra("sex");
        String specialized = intent.getStringExtra("specialized");
        String course = intent.getStringExtra("course");
        String addressStudy = intent.getStringExtra("addressStudy");
        String show = intent.getStringExtra("show");
        ArrayList<String> interest = intent.getStringArrayListExtra("interest");



        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String [] favarr = interest.toArray(new String[interest.size()]);

                InfoRegister infoRegister = new InfoRegister(email,name,birthday,sex,specialized,course,addressStudy,favarr,image);
                Log.e("abc",infoRegister.getEmail()+"\n"+infoRegister.getName()+"\n"+infoRegister.getBirthday()
                        +"\n"+infoRegister.getSex()+"\n"+infoRegister.getSpecialized()+"\n"+infoRegister.getCourse()+"\n"+infoRegister.getAddressStudy()
                        +"\n"+infoRegister.getInterests()+"\n"+infoRegister.getImages());
                FunctionUserVolley functionUserVolley = new FunctionUserVolley();
                functionUserVolley.insertUserVolley_POST(AddImageActivity.this,infoRegister);
                startActivity(new Intent(AddImageActivity.this,HomeActivity.class));
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putStringArrayListExtra("result",interest);
                setResult(RESULT_OK,resultIntent);
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

        btnaddimg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
        btnaddimg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,REQUEST_CODE);

            }
        });
        btnaddimg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
        btnaddimg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
        btnaddimg5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
        btnaddimg6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE ){
            if(data!=null){
                Uri uri = data.getData();
                addimg1.setImageURI(uri);
                File img1 = new File(getRealPathFromURI(uri));
                image.add(img1);
                Log.e("abz",getRealPathFromURI(uri));
                btnaddimg1.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_baseline_close_24));
                btnaddimg1.getLayoutParams().width = 20;
                btnaddimg1.getLayoutParams().height =20;
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

}