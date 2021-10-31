package com.example.test1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test1.adapters.SpinnerAdapter;
import com.example.test1.volleys.FunctionGetListVolley;

import java.util.ArrayList;
import java.util.List;

public class SpecializedActivity extends AppCompatActivity {
    Button btnContinue;
    ImageButton imgback;
    Spinner spnChuyenNganh;
    String specialzed;
    public static List<String> specializedList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialized);
        btnContinue = findViewById(R.id.btnContinue);
        imgback = findViewById(R.id.imgBack);
        spnChuyenNganh = findViewById(R.id.spnChuyenNganh);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String name = intent.getStringExtra("name");
        String birthday = intent.getStringExtra("birthday");
        String sex = intent.getStringExtra("sex");
        String addressStudy = intent.getStringExtra("addressStudy");

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this, specializedList);
        spnChuyenNganh.setAdapter(spinnerAdapter);

        spnChuyenNganh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){
                    specialzed = specializedList.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (specialzed != null) {
                    Intent intent1 = new Intent(SpecializedActivity.this,CourseActivity.class);
                    intent1.putExtra("email",email);
                    intent1.putExtra("name",name);
                    intent1.putExtra("birthday",birthday);
                    intent1.putExtra("sex",sex);
                    intent1.putExtra("addressStudy",addressStudy);
                    intent1.putExtra("specialized",specialzed);
                    startActivity(intent1);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                else {
                    Toast.makeText(SpecializedActivity.this,"không được để trống",Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("result",addressStudy);
                setResult(RESULT_OK,resultIntent);
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                String result = data.getStringExtra("result");
                spnChuyenNganh.setPrompt(result);
            }
        }
    }



}