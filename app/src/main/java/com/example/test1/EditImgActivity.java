package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test1.Fragment.ProfileFragment;

public class EditImgActivity extends AppCompatActivity {
    ImageButton btneditimg1,btneditimg2,btneditimg3,btneditimg4,btneditimg5,btneditimg6;
    TextView tvDone;
    ImageView editimg1,editimg2,editimg3,editimg4,editimg5,editimg6;

    private static final int REQUEST_CODE = 1;

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

        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EditImgActivity.this,HomeActivity.class));
                HomeActivity.fragment = new ProfileFragment();
                HomeActivity.selectedItem = R.id.proId;
            }
        });

        btneditimg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        editimg1.setOnClickListener(new View.OnClickListener() {
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
                editimg1.setImageURI(uri);
                btneditimg1.setVisibility(View.INVISIBLE);
            }

        }
    }
}