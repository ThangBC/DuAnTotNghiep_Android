package com.example.test1;

import android.content.Context;
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

import com.example.test1.Model.InfoRegister;
import com.example.test1.R;

public class SexActivity extends AppCompatActivity {
    Button btnContinue;
    ImageButton imgBack;
    String sex;
    RadioButton rdoMale, rdoFemale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sex);
        btnContinue = findViewById(R.id.btnContinue);
        imgBack = findViewById(R.id.imgBack);
        rdoMale = findViewById(R.id.rdoMale);
        rdoFemale = findViewById(R.id.rdoFemale);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("duLieu");
        InfoRegister infoRegister = (InfoRegister) bundle.getSerializable("infoRegister");

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sex != null){
                    Intent intent1 = new Intent(SexActivity.this,SpecializedActivity.class);
                    Bundle bundle1 = new Bundle();
                    infoRegister.setSex(sex);
                    bundle1.putSerializable("infoRegister",infoRegister);
                    intent1.putExtra("duLieu",bundle1);
                    startActivity(intent1);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }else {
                    Toast.makeText(SexActivity.this,"không được để trống",Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SexActivity.this,BirthdayActivity .class));
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
    }

    public void rdoTapped(View view) {
        int selectedId = view.getId();
        if(selectedId == R.id.rdoFemale){
            updateRdoGroup(rdoFemale);
        }else {
            updateRdoGroup(rdoMale);
        }
    }

    public void updateRdoGroup(RadioButton selected){
        rdoMale.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.cus_btn_sex));
        rdoMale.setTextColor(Color.BLACK);
        rdoFemale.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.cus_btn_sex));
        rdoFemale.setTextColor(Color.BLACK);
        selected.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rdo_sex_on));
        selected.setTextColor(Color.WHITE);
        Toast.makeText(this, "Giới tính: "+selected.getText(), Toast.LENGTH_SHORT).show();
        sex = selected.getText().toString();
    }
}