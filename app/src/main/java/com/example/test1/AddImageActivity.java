package com.example.test1;

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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.test1.functions.Loading;
import com.example.test1.models.Users;
import com.example.test1.networking.FunctionUserFAN;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddImageActivity extends AppCompatActivity {

    ImageButton imgBack, btnaddimg1, btnaddimg2, btnaddimg3, btnaddimg4, btnaddimg5, btnaddimg6;
    Button btnContinue;
    ImageView addimg1, addimg2, addimg3, addimg4, addimg5, addimg6;
    List<File> image = new ArrayList<>();
    File fileimg;
    public static int REQUEST_CODE = 1;
    public static Loading loading;

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
        ArrayList<String> show = intent.getStringArrayListExtra("show");
        ArrayList<String> interest = intent.getStringArrayListExtra("interest");

        loading = new Loading(this);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading.startLoadingDialog();
                Users users = new Users(email,name,image,interest,birthday,sex,addressStudy,specialized,course,show);

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

        btnaddimg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (image.size()>=1) {
                    getLayout(btnaddimg1,addimg1);
                    image.remove(0);
                    ListImg();
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            }
        });
        btnaddimg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (image.size()>=2) {
                    getLayout(btnaddimg2,addimg2);
                    image.remove(1);
                    ListImg();
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQUEST_CODE);
                }

            }
        });
        btnaddimg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (image.size()>=3) {
                    getLayout(btnaddimg3,addimg3);
                    image.remove(2);
                    ListImg();
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            }
        });
        btnaddimg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (image.size()>=4) {
                    getLayout(btnaddimg4,addimg4);
                    image.remove(3);
                    ListImg();
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            }
        });
        btnaddimg5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (image.size()>=5) {
                    getLayout(btnaddimg5,addimg5);
                    image.remove(4);
                    ListImg();
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            }
        });
        btnaddimg6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (image.size()>=6) {
                    getLayout(btnaddimg6,addimg6);
                    image.remove(5);
                    ListImg();
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            }
        });
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

                Log.e("ahoho", String.valueOf(image.size()));
                Log.e("abz", getRealPathFromURI(uri));
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

    public void ListImg(){
        if(image.size()>=1){
            Bitmap myBitmap = BitmapFactory.decodeFile(image.get(0).getAbsolutePath());
            addimg1.setImageBitmap(myBitmap);
            setLayout(btnaddimg1);
            getLayout(btnaddimg2,addimg2);
            getLayout(btnaddimg3,addimg3);
            getLayout(btnaddimg4,addimg4);
            getLayout(btnaddimg5,addimg5);
            getLayout(btnaddimg6,addimg6);
        }
        if(image.size()>=2){
            Bitmap myBitmap = BitmapFactory.decodeFile(image.get(0).getAbsolutePath());
            addimg1.setImageBitmap(myBitmap);
            Bitmap myBitmap2 = BitmapFactory.decodeFile(image.get(1).getAbsolutePath());
            addimg2.setImageBitmap(myBitmap2);
            setLayout(btnaddimg1);
            setLayout(btnaddimg2);
            getLayout(btnaddimg3,addimg3);
            getLayout(btnaddimg4,addimg4);
            getLayout(btnaddimg5,addimg5);
            getLayout(btnaddimg6,addimg6);
        }
        if(image.size()>=3){
            Bitmap myBitmap = BitmapFactory.decodeFile(image.get(0).getAbsolutePath());
            addimg1.setImageBitmap(myBitmap);
            Bitmap myBitmap2 = BitmapFactory.decodeFile(image.get(1).getAbsolutePath());
            addimg2.setImageBitmap(myBitmap2);
            Bitmap myBitmap3 = BitmapFactory.decodeFile(image.get(2).getAbsolutePath());
            addimg3.setImageBitmap(myBitmap3);
            setLayout(btnaddimg1);
            setLayout(btnaddimg2);
            setLayout(btnaddimg3);
            getLayout(btnaddimg4,addimg4);
            getLayout(btnaddimg5,addimg5);
            getLayout(btnaddimg6,addimg6);
        }
        if(image.size()>=4){
            Bitmap myBitmap = BitmapFactory.decodeFile(image.get(0).getAbsolutePath());
            addimg1.setImageBitmap(myBitmap);
            Bitmap myBitmap2 = BitmapFactory.decodeFile(image.get(1).getAbsolutePath());
            addimg2.setImageBitmap(myBitmap2);
            Bitmap myBitmap3 = BitmapFactory.decodeFile(image.get(2).getAbsolutePath());
            addimg3.setImageBitmap(myBitmap3);
            Bitmap myBitmap4 = BitmapFactory.decodeFile(image.get(3).getAbsolutePath());
            addimg4.setImageBitmap(myBitmap4);
            setLayout(btnaddimg1);
            setLayout(btnaddimg2);
            setLayout(btnaddimg3);
            setLayout(btnaddimg4);
            getLayout(btnaddimg5,addimg5);
            getLayout(btnaddimg6,addimg6);
        }
        if(image.size()>=5){
            Bitmap myBitmap = BitmapFactory.decodeFile(image.get(0).getAbsolutePath());
            addimg1.setImageBitmap(myBitmap);
            Bitmap myBitmap2 = BitmapFactory.decodeFile(image.get(1).getAbsolutePath());
            addimg2.setImageBitmap(myBitmap2);
            Bitmap myBitmap3 = BitmapFactory.decodeFile(image.get(2).getAbsolutePath());
            addimg3.setImageBitmap(myBitmap3);
            Bitmap myBitmap4 = BitmapFactory.decodeFile(image.get(3).getAbsolutePath());
            addimg4.setImageBitmap(myBitmap4);
            Bitmap myBitmap5 = BitmapFactory.decodeFile(image.get(4).getAbsolutePath());
            addimg5.setImageBitmap(myBitmap5);
            setLayout(btnaddimg1);
            setLayout(btnaddimg2);
            setLayout(btnaddimg3);
            setLayout(btnaddimg4);
            setLayout(btnaddimg5);
            getLayout(btnaddimg6,addimg6);
        }
        if(image.size()==6){
            Bitmap myBitmap = BitmapFactory.decodeFile(image.get(0).getAbsolutePath());
            addimg1.setImageBitmap(myBitmap);
            Bitmap myBitmap2 = BitmapFactory.decodeFile(image.get(1).getAbsolutePath());
            addimg2.setImageBitmap(myBitmap2);
            Bitmap myBitmap3 = BitmapFactory.decodeFile(image.get(2).getAbsolutePath());
            addimg3.setImageBitmap(myBitmap3);
            Bitmap myBitmap4 = BitmapFactory.decodeFile(image.get(3).getAbsolutePath());
            addimg4.setImageBitmap(myBitmap4);
            Bitmap myBitmap5 = BitmapFactory.decodeFile(image.get(4).getAbsolutePath());
            addimg5.setImageBitmap(myBitmap5);
            Bitmap myBitmap6 = BitmapFactory.decodeFile(image.get(5).getAbsolutePath());
            addimg6.setImageBitmap(myBitmap6);
            setLayout(btnaddimg1);
            setLayout(btnaddimg2);
            setLayout(btnaddimg3);
            setLayout(btnaddimg4);
            setLayout(btnaddimg5);
            setLayout(btnaddimg6);
        }
    }

}