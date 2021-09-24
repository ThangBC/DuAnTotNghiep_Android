package com.example.test1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test1.Adapter.AddressStudyAdapter;
import com.example.test1.Model.Item;
import com.example.test1.R;

import java.util.ArrayList;
import java.util.List;

public class AddressStudyActivity extends AppCompatActivity {
    Button btnContinue;
    Spinner spnAddress;
    ImageButton imgBack;
    private AddressStudyAdapter addressStudyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_study);


        btnContinue = findViewById(R.id.btnContinue);
        imgBack = findViewById(R.id.imgBack);
        spnAddress = findViewById(R.id.spnAddressStudy);

        addressStudyAdapter = new AddressStudyAdapter(AddressStudyActivity.this,getList());
        spnAddress.setAdapter(addressStudyAdapter);

        spnAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               Toast.makeText(AddressStudyActivity.this,addressStudyAdapter.getItem(position).toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddressStudyActivity.this,ShowActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddressStudyActivity.this,CourseActivity.class));
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
}