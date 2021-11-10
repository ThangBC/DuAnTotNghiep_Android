package com.example.test1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test1.adapters.SpinnerAdapter;

import java.util.List;

public class CourseActivity extends AppCompatActivity {
    Button btnContinue;
    ImageButton imgBack;
    Spinner spinnerDanhSach;
    String course;
    public static List<String> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        btnContinue = findViewById(R.id.btnContinue);
        imgBack= findViewById(R.id.imgBack);
        spinnerDanhSach = (Spinner) findViewById(R.id.spnDanhSach);


        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String name = intent.getStringExtra("name");
        String birthday = intent.getStringExtra("birthday");
        String sex = intent.getStringExtra("sex");
        String addressStudy = intent.getStringExtra("addressStudy");
        String specialized = intent.getStringExtra("specialized");

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this, courseList);
        spinnerDanhSach.setAdapter(spinnerAdapter);

        spinnerDanhSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){
                    course = courseList.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (course != null){
                    Intent intent1 = new Intent(CourseActivity.this,ShowActivity.class);
                    intent1.putExtra("email",email);
                    intent1.putExtra("name",name);
                    intent1.putExtra("birthday",birthday);
                    intent1.putExtra("sex",sex);
                    intent1.putExtra("addressStudy",addressStudy);
                    intent1.putExtra("specialized",specialized);
                    intent1.putExtra("course",course);
                    startActivity(intent1);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                else {
                    Toast.makeText(CourseActivity.this,"không được để trống",Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("result",specialized);
                setResult(RESULT_OK,resultIntent);
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
    }

}