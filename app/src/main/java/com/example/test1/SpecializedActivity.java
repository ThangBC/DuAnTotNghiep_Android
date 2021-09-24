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

import com.example.test1.Adapter.SpecializedAdapter;
import com.example.test1.Model.Item;
import com.example.test1.R;

import java.util.ArrayList;
import java.util.List;

public class SpecializedActivity extends AppCompatActivity {
    Button btnContinue, btnCN1,btnCN2,btnCN3,btnCN4;
    ImageButton imgback;
    Spinner spnChuyenNganh;
    SpecializedAdapter specializedAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialized);
        btnContinue = findViewById(R.id.btnContinue);
        imgback = findViewById(R.id.imgBack);
        spnChuyenNganh = findViewById(R.id.spnChuyenNganh);

        specializedAdapter = new SpecializedAdapter(SpecializedActivity.this,getList());
        spnChuyenNganh.setAdapter(specializedAdapter);

        spnChuyenNganh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SpecializedActivity.this,specializedAdapter.getItem(position).toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SpecializedActivity.this,CourseActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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