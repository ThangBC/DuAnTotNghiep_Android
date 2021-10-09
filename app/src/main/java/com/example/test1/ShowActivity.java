package com.example.test1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ShowActivity extends AppCompatActivity {
    Button btnContinue;
    ImageButton imgBack;
    String show;

    RadioButton rdoMaleShow, rdoFemaleShow,rdoEveryOneShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        btnContinue = findViewById(R.id.btnContinue);
        imgBack = findViewById(R.id.imgBack);
        rdoMaleShow = findViewById(R.id.rdoMaleShow);
        rdoFemaleShow = findViewById(R.id.rdoFemaleShow);
        rdoEveryOneShow = findViewById(R.id.rdoEveryOneShow);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String birthday = intent.getStringExtra("birthday");
        String sex = intent.getStringExtra("sex");
        String specialzed = intent.getStringExtra("specialzed");
        String course = intent.getStringExtra("course");
        String addressStudy = intent.getStringExtra("addressStudy");
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (show != null){
                    Intent intent1 = new Intent(ShowActivity.this,InterestsActivity.class);
                    intent1.putExtra("name",name);
                    intent1.putExtra("birthday",birthday);
                    intent1.putExtra("sex",sex);
                    intent1.putExtra("specialized",specialzed);
                    intent1.putExtra("course",course);
                    intent1.putExtra("addressStudy",addressStudy);
                    intent1.putExtra("show",show);
                    startActivity(intent1);
                }else {
                    Toast.makeText(ShowActivity.this,"không được để trống",Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("result",course);
                setResult(RESULT_OK,resultIntent);
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
    }

    public void rdoTapped(View view) {
        int selectedId = view.getId();
        if(selectedId == R.id.rdoFemaleShow){
            updateRdoGroup(rdoFemaleShow);
        }else if(selectedId == R.id.rdoMaleShow) {
            updateRdoGroup(rdoMaleShow);
        }else {
            updateRdoGroup(rdoEveryOneShow);
        }
    }

    public void updateRdoGroup(RadioButton selected){
        rdoMaleShow.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.cus_btn_sex));
        rdoFemaleShow.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.cus_btn_sex));
        rdoEveryOneShow.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.cus_btn_sex));

        selected.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rdo_sex_on));

        show = selected.getText().toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                String result = data.getStringExtra("result");
                if(result == "Nữ"){
                    updateRdoGroup(rdoFemaleShow);
                }else if(result == "Nam") {
                    updateRdoGroup(rdoMaleShow);
                }else {
                    updateRdoGroup(rdoEveryOneShow);
                }
            }
        }
    }

}