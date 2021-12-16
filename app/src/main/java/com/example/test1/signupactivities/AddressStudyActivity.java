package com.example.test1.signupactivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class AddressStudyActivity extends AppCompatActivity {
    Button btnContinue;
    Spinner spnAddress;
    ImageButton imgBack;
    String addressStudy;
    public static List<String> addressStudyList;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_study);

        btnContinue = findViewById(R.id.btnContinue);
        imgBack = findViewById(R.id.imgBack);
        spnAddress = findViewById(R.id.spnAddressStudy);

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this, addressStudyList);
        spnAddress.setAdapter(spinnerAdapter);

        preferenceManager = new PreferenceManager(getApplicationContext());
        if (preferenceManager.getString("facilitiesSignUp") != null) {
            int index = selectSpinnerValue(addressStudyList, preferenceManager.getString("facilitiesSignUp"));
            spnAddress.setSelection(index, true);
            addressStudy = preferenceManager.getString("facilitiesSignUp");
        }

        spnAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    addressStudy = addressStudyList.get(position);
                } else {
                    addressStudy = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addressStudy != null) {
                    preferenceManager.putString("facilitiesSignUp", addressStudy);
                    startActivity(new Intent(AddressStudyActivity.this, SpecializedActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    Toast.makeText(AddressStudyActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferenceManager.putString("facilitiesSignUp", addressStudy);
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