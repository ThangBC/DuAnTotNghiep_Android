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
        Bundle bundle = intent.getBundleExtra("duLieu");
        InfoRegister infoRegister = (InfoRegister) bundle.getSerializable("infoRegister");

        spinnerAdapter = new SpinnerAdapter(AddressStudyActivity.this,getList());
        spnAddress.setAdapter(spinnerAdapter);

        spnAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){
                    addressStudy = spinnerAdapter.getItem(position).toString();
                    Toast.makeText(AddressStudyActivity.this,addressStudy,Toast.LENGTH_SHORT).show();
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
                    Bundle bundle1 = new Bundle();
                    infoRegister.setAddressStudy(addressStudy);
                    bundle1.putSerializable("infoRegister",infoRegister);
                    intent1.putExtra("duLieu",bundle1);
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