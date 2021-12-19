package com.example.test1.signupactivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.test1.R;
import com.example.test1.ultilties.PreferenceManager;

public class SexActivity extends AppCompatActivity {
    Button btnContinue;
    ImageButton imgBack;
    String sex;
    RadioButton rdoMale, rdoFemale;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sex);
        btnContinue = findViewById(R.id.btnContinue);
        imgBack = findViewById(R.id.imgBack);
        rdoMale = findViewById(R.id.rdoMale);
        rdoFemale = findViewById(R.id.rdoFemale);

        preferenceManager = new PreferenceManager(getApplicationContext());
        if (preferenceManager.getString("genderSignUp") != null) {
            if (preferenceManager.getString("genderSignUp").equals("Nam")) {
                updateRdoGroup(rdoMale);
            } else {
                updateRdoGroup(rdoFemale);
            }
        }

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sex != null) {
                    preferenceManager.putString("genderSignUp", sex);
                    startActivity(new Intent(SexActivity.this, AddressStudyActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    Toast.makeText(SexActivity.this, "Vui lòng chọn giới tính", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferenceManager.putString("genderSignUp", sex);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    public void rdoTapped(View view) {
        int selectedId = view.getId();
        if (selectedId == R.id.rdoFemale) {
            updateRdoGroup(rdoFemale);
        } else {
            updateRdoGroup(rdoMale);
        }
    }

    public void updateRdoGroup(RadioButton selected) {
        rdoMale.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cus_btn_sex));
        rdoFemale.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cus_btn_sex));
        selected.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rdo_sex_on));

        sex = selected.getText().toString();
    }
}