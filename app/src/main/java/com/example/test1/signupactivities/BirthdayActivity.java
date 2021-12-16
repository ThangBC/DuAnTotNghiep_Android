package com.example.test1.signupactivities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test1.R;
import com.example.test1.ultilties.PreferenceManager;

import java.util.Calendar;
import java.util.Date;

public class BirthdayActivity extends AppCompatActivity {
    Button btnContinue;
    ImageButton imgBack;
    EditText edtDate;
    DatePickerDialog.OnDateSetListener onDateSetListener;
    String date;
    int year, value;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);
        btnContinue = findViewById(R.id.btnContinue);
        imgBack = findViewById(R.id.imgBack);
        edtDate = findViewById(R.id.edtDate);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        preferenceManager = new PreferenceManager(getApplicationContext());
        if(preferenceManager.getString("birthdaySignUp")!=null){
            edtDate.setText(preferenceManager.getString("birthdaySignUp"));
            date = preferenceManager.getString("birthdaySignUp");
            value = year - Integer.parseInt(preferenceManager.getString("birthdaySignUp").substring(preferenceManager.getString("birthdaySignUp").length()-4)) ;
        }


        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        BirthdayActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onDateSetListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
        });
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int Year, int month, int dayOfMonth) {
                month = month + 1;
                date = dayOfMonth + "/" + month + "/" + Year;
                value = year - Year;
                Log.d("value", "onDateSet: "+ value);
                edtDate.setText(date);
            }
        };

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (date != null && value >= 18){
                    preferenceManager.putString("birthdaySignUp",date);
                    startActivity(new Intent(BirthdayActivity.this,SexActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }else if(date == null) {
                    Toast.makeText(BirthdayActivity.this,"Không được để trống",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(BirthdayActivity.this,"Bạn chưa đủ 18 tuổi",Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferenceManager.putString("birthdaySignUp",date);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }
}