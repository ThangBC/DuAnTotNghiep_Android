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

import java.util.List;

public class AddressStudyActivity extends AppCompatActivity {
    Button btnContinue;
    Spinner spnAddress;
    ImageButton imgBack;
    String addressStudy;
    public static List<String> addressStudyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_study);

        btnContinue = findViewById(R.id.btnContinue);
        imgBack = findViewById(R.id.imgBack);
        spnAddress = findViewById(R.id.spnAddressStudy);


        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String name = intent.getStringExtra("name");
        String birthday = intent.getStringExtra("birthday");
        String sex = intent.getStringExtra("sex");

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(this, addressStudyList);
        spnAddress.setAdapter(spinnerAdapter);

        spnAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){
                    addressStudy = addressStudyList.get(position);
                    Toast.makeText(AddressStudyActivity.this, addressStudy, Toast.LENGTH_SHORT).show();
                }else {
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
                if(addressStudy != null) {
                    Intent intent1 = new Intent(AddressStudyActivity.this,SpecializedActivity.class);
                    intent1.putExtra("email",email);
                    intent1.putExtra("name",name);
                    intent1.putExtra("birthday",birthday);
                    intent1.putExtra("sex",sex);
                    intent1.putExtra("addressStudy",addressStudy);
                    startActivity(intent1);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }else {
                    Toast.makeText(AddressStudyActivity.this,"không được để trống",Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("result",sex);
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
                spnAddress.setPrompt(result);
            }
        }
    }

}