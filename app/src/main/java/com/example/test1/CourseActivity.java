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

import com.example.test1.Adapter.SpinnerAdapter;
import com.example.test1.Model.InfoRegister;
import com.example.test1.Model.Item;

import java.util.ArrayList;
import java.util.List;

public class CourseActivity extends AppCompatActivity {
    Button btnContinue;
    ImageButton imgBack;
    Spinner spinnerDanhSach;
    SpinnerAdapter spinnerAdapter;
    String course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        btnContinue = findViewById(R.id.btnContinue);
        imgBack= findViewById(R.id.imgBack);
        spinnerDanhSach = (Spinner) findViewById(R.id.spnDanhSach);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("duLieu");
        InfoRegister infoRegister = (InfoRegister) bundle.getSerializable("infoRegister");

        spinnerAdapter = new SpinnerAdapter(CourseActivity.this,getList());
        spinnerDanhSach.setAdapter(spinnerAdapter);

        spinnerDanhSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){
                    course = spinnerAdapter.getItem(position).toString();
                    Toast.makeText(CourseActivity.this,course,Toast.LENGTH_SHORT).show();
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
                    Intent intent1 = new Intent(CourseActivity.this,AddressStudyActivity.class);
                    Bundle bundle1 = new Bundle();
                    infoRegister.setCourse(course);
                    bundle1.putSerializable("infoRegister",infoRegister);
                    intent1.putExtra("duLieu",bundle1);
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
                startActivity(new Intent(CourseActivity.this,SpecializedActivity.class));
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
    }
    private List<Item> getList() {
        List<Item> courseList = new ArrayList<>();
        courseList.add(new Item("Chọn khóa học"));
        courseList.add(new Item("15.3"));
        courseList.add(new Item("16.3"));
        courseList.add(new Item("17.3"));
        return  courseList;
    }
}