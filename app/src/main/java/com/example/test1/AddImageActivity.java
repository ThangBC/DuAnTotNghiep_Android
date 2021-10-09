package com.example.test1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class AddImageActivity extends AppCompatActivity {
    ImageButton imgBack,btnaddimg1,btnaddimg2,btnaddimg3,btnaddimg4,btnaddimg5,btnaddimg6;
    Button btnContinue;
    ImageView addimg1,addimg2,addimg3,addimg4,addimg5,addimg6;
    int image;

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
        String name = intent.getStringExtra("name");
        String birthday = intent.getStringExtra("birthday");
        String sex = intent.getStringExtra("sex");
        String specialzed = intent.getStringExtra("specialzed");
        String course = intent.getStringExtra("course");
        String addressStudy = intent.getStringExtra("addressStudy");
        String show = intent.getStringExtra("show");
        String interest = intent.getStringExtra("interest");

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddImageActivity.this,HomeActivity.class));
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("result",interest);
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

        addimg1.setOnClickListener(new View.OnClickListener() {
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
            if(data!=null && data!=null){
                Uri uri = data.getData();
                addimg1.setImageURI(uri);
                try {
                    final InputStream imageStream = getContentResolver().openInputStream(uri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    String encodedImage = encodeImage(selectedImage);
                    Log.e("aaa",encodedImage);
                }catch (Exception e){

                }
                btnaddimg1.setVisibility(View.INVISIBLE);
            }
        }
    }

    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

}