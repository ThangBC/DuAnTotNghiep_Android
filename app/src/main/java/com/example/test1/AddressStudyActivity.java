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

import com.example.test1.Adapter.SpinnerAdapter;
import com.example.test1.Model.Item;

import java.util.ArrayList;
import java.util.List;

public class AddressStudyActivity extends AppCompatActivity {
    Button btnContinue;
    Spinner spnAddress;
    ImageButton imgBack;
    private SpinnerAdapter spinnerAdapter;
    String addressStudy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_study);

        btnContinue = findViewById(R.id.btnContinue);
        imgBack = findViewById(R.id.imgBack);
        spnAddress = findViewById(R.id.spnAddressStudy);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String birthday = intent.getStringExtra("birthday");
        String sex = intent.getStringExtra("sex");
        String specialzed = intent.getStringExtra("specialzed");
        String course = intent.getStringExtra("course");

        spinnerAdapter = new SpinnerAdapter(AddressStudyActivity.this,getList());
        spnAddress.setAdapter(spinnerAdapter);

        spnAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){
                    addressStudy = spinnerAdapter.getItem(position).toString();
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
                    Intent intent1 = new Intent(AddressStudyActivity.this,ShowActivity.class);
                    intent1.putExtra("name",name);
                    intent1.putExtra("birthday",birthday);
                    intent1.putExtra("sex",sex);
                    intent1.putExtra("specialized",specialzed);
                    intent1.putExtra("course",course);
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
                resultIntent.putExtra("result",course);
                setResult(RESULT_OK,resultIntent);
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
    }

    private List<Item> getList() {
        List<Item> addressStudyList = new ArrayList<>();
        addressStudyList.add(new Item("Chọn cơ sở"));
        addressStudyList.add(new Item("Hà Nội"));
        addressStudyList.add(new Item("Hồ Chí Minh"));
        addressStudyList.add(new Item("Đà Nẵng"));
        addressStudyList.add(new Item("Tây Nguyên"));
        addressStudyList.add(new Item("Cần Thơ"));
        return addressStudyList;
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