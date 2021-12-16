package com.example.test1.signupactivities;

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

import com.example.test1.R;
import com.example.test1.adapters.SpinnerAdapter;
import com.example.test1.ultilties.PreferenceManager;

import java.util.List;

public class SpecializedActivity extends AppCompatActivity {
    Button btnContinue;
    ImageButton imgback;
    Spinner spnChuyenNganh;
    String specialzed;
    public static List<String> specializedList;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialized);
        btnContinue = findViewById(R.id.btnContinue);
        imgback = findViewById(R.id.imgBack);
        spnChuyenNganh = findViewById(R.id.spnChuyenNganh);

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this, specializedList);
        spnChuyenNganh.setAdapter(spinnerAdapter);

        preferenceManager = new PreferenceManager(getApplicationContext());
        if (preferenceManager.getString("specializedSignUp") != null) {
            int index = selectSpinnerValue(specializedList, preferenceManager.getString("specializedSignUp"));
            spnChuyenNganh.setSelection(index, true);
            specialzed = preferenceManager.getString("specializedSignUp");
        }

        spnChuyenNganh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    specialzed = specializedList.get(position);
                }
                else {
                    specialzed = null;
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
                    preferenceManager.putString("specializedSignUp", specialzed);
                    startActivity(new Intent(SpecializedActivity.this, CourseActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    Toast.makeText(SpecializedActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferenceManager.putString("specializedSignUp", specialzed);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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