package com.example.test1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.test1.R;

public class ShowActivity extends AppCompatActivity {
    Button btnContinue;
    ImageButton imgBack;

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

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowActivity.this,InterestsActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowActivity.this,AddressStudyActivity.class));
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
        rdoMaleShow.setTextColor(Color.BLACK);
        rdoFemaleShow.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.cus_btn_sex));
        rdoFemaleShow.setTextColor(Color.BLACK);
        rdoEveryOneShow.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.cus_btn_sex));
        rdoEveryOneShow.setTextColor(Color.BLACK);

        selected.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rdo_sex_on));
        selected.setTextColor(Color.WHITE);
        Toast.makeText(this, "Hiển thị tôi với: "+selected.getText(), Toast.LENGTH_SHORT).show();
    }

}