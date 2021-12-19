package com.example.test1.signupactivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test1.R;
import com.example.test1.adapters.SpinnerAdapter;
import com.example.test1.ultilties.PreferenceManager;

import java.util.List;

public class CourseActivity extends AppCompatActivity {
    Button btnContinue;
    ImageButton imgBack;
    Spinner spinnerDanhSach;
    String course;
    public static List<String> courseList;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        btnContinue = findViewById(R.id.btnContinue);
        imgBack= findViewById(R.id.imgBack);
        spinnerDanhSach = (Spinner) findViewById(R.id.spnDanhSach);

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this, courseList);
        spinnerDanhSach.setAdapter(spinnerAdapter);

        preferenceManager = new PreferenceManager(getApplicationContext());
        if(preferenceManager.getString("CourseSignUp")!=null){
            int index = selectSpinnerValue(courseList, preferenceManager.getString("CourseSignUp"));
            spinnerDanhSach.setSelection(index, true);
            course = preferenceManager.getString("CourseSignUp");
        }



        spinnerDanhSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){
                    course = courseList.get(position);
                } else {
                    course = null;
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
                    preferenceManager.putString("CourseSignUp",course);
                    startActivity(new Intent(CourseActivity.this,InterestsActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                else {
                    Toast.makeText(CourseActivity.this,"Vui lòng chọn khóa học",Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferenceManager.putString("CourseSignUp",course);
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
    }
    private int selectSpinnerValue(List<String> ListSpinner, String myString) {
        int index = 0;
        for (int i = 0; i < ListSpinner.size(); i++) {
            if (ListSpinner.get(i).equals(myString)) {
                index = i;
                break;
            }
        }
        return index;
    }
}