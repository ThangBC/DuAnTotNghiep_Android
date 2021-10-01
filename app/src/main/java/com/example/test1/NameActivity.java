package com.example.test1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test1.Model.InfoRegister;
import com.example.test1.R;

public class NameActivity extends AppCompatActivity {
    Button btnContinue;
    EditText edtName;
    String name;
    InfoRegister infoRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        btnContinue = findViewById(R.id.btnContinue);
        edtName = findViewById(R.id.edtName);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = edtName.getText().toString();
                if (name.length() > 0){
                    Intent intent = new Intent(NameActivity.this,BirthdayActivity.class);
                    Bundle bundle = new Bundle();
                    infoRegister = new InfoRegister(name,"","","","","","");
                    bundle.putSerializable("infoRegister",infoRegister);
                    intent.putExtra("duLieu",bundle);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }else {
                    Toast.makeText(NameActivity.this,"không được để trống",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}