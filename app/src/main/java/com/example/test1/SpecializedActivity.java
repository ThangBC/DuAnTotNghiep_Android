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

public class SpecializedActivity extends AppCompatActivity {
    Button btnContinue;
    ImageButton imgback;
    Spinner spnChuyenNganh;
    String specialzed;
    SpinnerAdapter spinnerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialized);
        btnContinue = findViewById(R.id.btnContinue);
        imgback = findViewById(R.id.imgBack);
        spnChuyenNganh = findViewById(R.id.spnChuyenNganh);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("duLieu");
        InfoRegister infoRegister = (InfoRegister) bundle.getSerializable("infoRegister");

        spinnerAdapter = new SpinnerAdapter(SpecializedActivity.this,getList());
        spnChuyenNganh.setAdapter(spinnerAdapter);

        spnChuyenNganh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){
                    specialzed = spinnerAdapter.getItem(position).toString();
                    Toast.makeText(SpecializedActivity.this,specialzed,Toast.LENGTH_SHORT).show();
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
                    Bundle bundle1 = new Bundle();
                    infoRegister.setSpecialized(specialzed);
                    bundle1.putSerializable("infoRegister",infoRegister);
                    intent1.putExtra("duLieu",bundle1);
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
                startActivity(new Intent(SpecializedActivity.this,SexActivity.class ));
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
    }
    private List<Item> getList(){
        List<Item> specializedList = new ArrayList<>();
        specializedList.add(new Item("Chọn chuyên ngành"));
        specializedList.add(new Item("Công nghệ thông tin"));
        specializedList.add(new Item("Kinh tế"));
        specializedList.add(new Item("Du lịch - Nhà hàng - Khách sạn"));
        specializedList.add(new Item("Cơ khí - (Điện) tự động hóa"));
        specializedList.add(new Item("Thẩm mỹ làm đẹp"));
        return specializedList ;
    }
}