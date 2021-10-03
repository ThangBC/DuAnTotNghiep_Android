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
        String name = intent.getStringExtra("name");
        String birthday = intent.getStringExtra("birthday");
        String sex = intent.getStringExtra("sex");

        spinnerAdapter = new SpinnerAdapter(SpecializedActivity.this,getList());
        spnChuyenNganh.setAdapter(spinnerAdapter);

        spnChuyenNganh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){
                    specialzed = spinnerAdapter.getItem(position).toString();
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
                    intent1.putExtra("name",name);
                    intent1.putExtra("birthday",birthday);
                    intent1.putExtra("sex",sex);
                    intent1.putExtra("specialized",specialzed);
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
                Intent resultIntent = new Intent();
                resultIntent.putExtra("result",sex);
                setResult(RESULT_OK,resultIntent);
                finish();
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                String result = data.getStringExtra("result");
                spnChuyenNganh.setPrompt(result);
            }
        }
    }
}