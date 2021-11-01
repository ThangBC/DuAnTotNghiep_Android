package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class RulesActivity extends AppCompatActivity {

    ImageView imgBacktoHello;
    Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        imgBacktoHello = findViewById(R.id.imgBacktoHello);
        btnContinue = findViewById(R.id.btnContinue);
        SharedPreferences sharedPreferences = getSharedPreferences("p1", MODE_PRIVATE);
        boolean firstStart = sharedPreferences.getBoolean("firstStart", true);
        if (firstStart) {
            contiRules();
        }else {
            startActivity(new Intent(RulesActivity.this, LoginActivity.class));
        }
        imgBacktoHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RulesActivity.this, HelloActivity.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    private void contiRules() {
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RulesActivity.this, LoginActivity.class));
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("p1", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }
}