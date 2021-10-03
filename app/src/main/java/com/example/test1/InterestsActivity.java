package com.example.test1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class InterestsActivity extends AppCompatActivity {
    Button btnContinue;
    ImageButton imgBack;
    String interest;

    CheckBox ckbDulich, ckbChoiGame,ckbDocSach,ckbMuaSam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);
        btnContinue = findViewById(R.id.btnContinue);
        imgBack = findViewById(R.id.imgBack);
        ckbDulich = findViewById(R.id.ckbDuLich);
        ckbChoiGame = findViewById(R.id.ckbChoiGame);
        ckbDocSach = findViewById(R.id.ckbDocSach);
        ckbMuaSam = findViewById(R.id.ckbMuaSam);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String birthday = intent.getStringExtra("birthday");
        String sex = intent.getStringExtra("sex");
        String specialzed = intent.getStringExtra("specialzed");
        String course = intent.getStringExtra("course");
        String addressStudy = intent.getStringExtra("addressStudy");
        String show = intent.getStringExtra("show");

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (interest.length() > 10){
                    Intent intent1 = new Intent(InterestsActivity.this,AddImageActivity.class);
                    intent1.putExtra("name",name);
                    intent1.putExtra("birthday",birthday);
                    intent1.putExtra("sex",sex);
                    intent1.putExtra("specialized",specialzed);
                    intent1.putExtra("course",course);
                    intent1.putExtra("addressStudy",addressStudy);
                    intent1.putExtra("show",show);
                    intent1.putExtra("interest",interest);
                    startActivity(intent1);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("result",show);
                setResult(RESULT_OK,resultIntent);
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
    }

    public void ckbTapped(View view) {
        String s = "Sở thích: ";
        if(ckbDulich.isChecked()){
            ckbDulich.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rdo_sex_on));
            s+="\nDu lịch";
        }else{
            ckbDulich.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.cus_btn_sex));
            s+="";
        }

        if(ckbChoiGame.isChecked()){
            ckbChoiGame.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rdo_sex_on));
            s+="\nChơi game";
        }else{
            ckbChoiGame.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.cus_btn_sex));
            s+="";
        }

        if(ckbDocSach.isChecked()){
            ckbDocSach.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rdo_sex_on));
            s+="\nĐọc sách";
        }else{
            ckbDocSach.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.cus_btn_sex));
            s+="";
        }

        if(ckbMuaSam.isChecked()){
            ckbMuaSam.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rdo_sex_on));
            s+="\nMua sắm";
        }else{
            ckbMuaSam.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.cus_btn_sex));
            s+="";
        }
        interest = s;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                String result = data.getStringExtra("result");
                if (result == "Du lịch"){
                    ckbDulich.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rdo_sex_on));
                }
                if (result == "Chơi game"){
                    ckbChoiGame.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rdo_sex_on));
                }
                if (result == "Đọc sách"){
                    ckbDocSach.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rdo_sex_on));
                }
                if (result == "Mua sắm"){
                    ckbMuaSam.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rdo_sex_on));
                }
            }
        }
    }
}