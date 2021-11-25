package com.example.test1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test1.adapters.InterestAdapter;
import com.example.test1.interfaces.InterestListener;

import java.util.ArrayList;
import java.util.List;

public class InterestsActivity extends AppCompatActivity implements InterestListener {
    Button btnContinue;
    ImageButton imgBack;
    ArrayList<String> interest = new ArrayList<>();
    TextView tvInterestCount;
    RecyclerView rycInterest;
    public static List<String> interestList;
    int countInterest=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);
        btnContinue = findViewById(R.id.btnContinue);
        imgBack = findViewById(R.id.imgBack);
        tvInterestCount = findViewById(R.id.tvInterestCount);
        rycInterest = findViewById(R.id.rycInterest);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String name = intent.getStringExtra("name");
        String birthday = intent.getStringExtra("birthday");
        String sex = intent.getStringExtra("sex");
        String specialized = intent.getStringExtra("specialized");
        String course = intent.getStringExtra("course");
        String addressStudy = intent.getStringExtra("addressStudy");

        InterestAdapter interestAdapter = new InterestAdapter(this, interestList, this);
        rycInterest.setLayoutManager(new LinearLayoutManager(this));
        rycInterest.setAdapter(interestAdapter);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countInterest>0){
                    Intent intent1 = new Intent(InterestsActivity.this,AddImageActivity.class);
                    intent1.putExtra("email",email);
                    intent1.putExtra("name",name);
                    intent1.putExtra("birthday",birthday);
                    intent1.putExtra("sex",sex);
                    intent1.putExtra("specialized",specialized);
                    intent1.putExtra("course",course);
                    intent1.putExtra("addressStudy",addressStudy);
                    intent1.putExtra("interest",interest);
                    startActivity(intent1);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                else {
                    Toast.makeText(InterestsActivity.this,"không được để trống",Toast.LENGTH_SHORT).show();
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

    @Override
    public void changeInterest(List<String> arr,int count) {
        List<String> interestarr = new ArrayList<>();
        for (int i = 0;i<arr.size();i++){
            interestarr.add(arr.get(i));
        }
        interest = (ArrayList<String>) interestarr;
        Toast.makeText(this, interest.toString(), Toast.LENGTH_SHORT).show();
        countInterest = count;
        btnContinue.setText("Tiếp tục: "+count+"/5");
    }


}