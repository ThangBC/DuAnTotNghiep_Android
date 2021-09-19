package com.example.test1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test1.R;

public class SpecializedActivity extends AppCompatActivity {
    Button btnContinue;
    ImageButton imgback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialized);
        btnContinue = findViewById(R.id.btnContinue);
        imgback = findViewById(R.id.imgBack);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SpecializedActivity.this,CourseActivity.class));
            }
        });

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SpecializedActivity.this,SexActivity.class ));
            }
        });
    }
}