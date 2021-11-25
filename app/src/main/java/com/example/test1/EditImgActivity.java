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

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EditImgActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btneditimg1, btneditimg2, btneditimg3, btneditimg4, btneditimg5, btneditimg6;
    TextView tvDone;
    ImageView editimg1, editimg2, editimg3, editimg4, editimg5, editimg6;
    File fileimg;
    private static final int REQUEST_CODE = 1;
    List<ImageView> imageViews = new ArrayList<>();
    List<ImageButton> imageButtons = new ArrayList<>();
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

        btneditimg1.setOnClickListener(this);
        btneditimg2.setOnClickListener(this);
        btneditimg3.setOnClickListener(this);
        btneditimg4.setOnClickListener(this);
        btneditimg5.setOnClickListener(this);
        btneditimg6.setOnClickListener(this);

        loading = new Loading();

        getImages(HomeActivity.users.getImages().size());

        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditImgActivity.this, HomeActivity.class));
                HomeActivity.fragment = new ProfileFragment();
                HomeActivity.selectedItem = R.id.proId;
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
                loading.show(getSupportFragmentManager(),"loading");
                functionUserFAN.updateImages(HomeActivity.users.getEmail(),HomeActivity.users.get_id(),fileimg,String.valueOf(fileimg),"no",EditImgActivity.this);
                Log.e("file", String.valueOf(fileimg));
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
        marginParams.setMargins(65, -320, 0, 0);
        btn.setLayoutParams(marginParams);
    }

    public void getLayout(ImageButton btn, ImageView img) {
        img.setImageBitmap(null);
        btn.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_add_24));
        btn.getLayoutParams().width = 100;
        btn.getLayoutParams().height = 100;
        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) btn.getLayoutParams();
        marginParams.setMargins(0, -220, 0, 0);
        btn.setLayoutParams(marginParams);
    }

    public void getImages(int size) {
        String url = "https://poly-dating.herokuapp.com/";
        for (int i = 0; i < size; i++) {
            Glide.with(EditImgActivity.this).load(url + HomeActivity.users.getImages().get(i)).into(imageViews.get(i));
            setLayout(imageButtons.get(i));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btneditimg1:
                pickImage(1, 0);
                break;
            case R.id.btneditimg2:
                pickImage(2, 1);
                break;
            case R.id.btneditimg3:
                pickImage(3, 2);
                break;
            case R.id.btneditimg4:
                pickImage(4, 3);
                break;
            case R.id.btneditimg5:
                pickImage(5, 4);
                break;
            case R.id.btneditimg6:
                pickImage(6, 5);
                break;
        }
    }

    private void pickImage(int number, int i) {
        if (HomeActivity.users.getImages().size() >= number) {// đây là xóa
            loading.show(getSupportFragmentManager(),"loading");
            functionUserFAN.updateImages(HomeActivity.users.getEmail(),HomeActivity.users.get_id(),null,String.valueOf(HomeActivity.users.getImages().get(i)),"yes", EditImgActivity.this);
            Log.e("images", String.valueOf(HomeActivity.users.getImages().get(i)));
        } else {// đây là thêm
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }
}