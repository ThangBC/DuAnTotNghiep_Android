package com.example.test1.signupactivities;

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

import com.example.test1.R;
import com.example.test1.adapters.InterestAdapter;
import com.example.test1.listeners.InterestListener;
import com.example.test1.ultilties.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

public class InterestsActivity extends AppCompatActivity implements InterestListener {
    Button btnContinue;
    ImageButton imgBack;
    ArrayList<String> interest = new ArrayList<>();
    TextView tvInterestCount;
    RecyclerView rycInterest;
    public static List<String> interestList;
    int countInterest = 0;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);
        btnContinue = findViewById(R.id.btnContinue);
        imgBack = findViewById(R.id.imgBack);
        tvInterestCount = findViewById(R.id.tvInterestCount);
        rycInterest = findViewById(R.id.rycInterest);

        preferenceManager = new PreferenceManager(getApplicationContext());
        if (preferenceManager.getInt("sizeInterestSignUp") != 0) {
            for (int i = 0;i<preferenceManager.getInt("sizeInterestSignUp");i++){
                interest.add(preferenceManager.getString("interestSignUp"+(i+1)));
                countInterest++;
            }
            btnContinue.setText("Tiếp tục: " + countInterest + "/5");
        }

        InterestAdapter interestAdapter = new InterestAdapter(this, interestList, interest, this);
        rycInterest.setLayoutManager(new LinearLayoutManager(this));
        rycInterest.setAdapter(interestAdapter);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countInterest > 0) {
                    for (int i = 0;i<interest.size();i++){
                        preferenceManager.putString("interestSignUp"+(i+1),interest.get(i));
                    }
                    preferenceManager.putInt("sizeInterestSignUp",interest.size());
                    startActivity(new Intent(InterestsActivity.this, AddImageActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    Toast.makeText(InterestsActivity.this, "Hãy chọn ít nhất 1 sở thích", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0;i<interest.size();i++){
                    preferenceManager.putInt("sizeInterestSignUp",interest.size());
                    preferenceManager.putString("interestSignUp"+(i+1),interest.get(i));
                }
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    @Override
    public void changeInterest(List<String> arr, int count) {
        List<String> interestarr = new ArrayList<>();
        for (int i = 0; i < arr.size(); i++) {
            interestarr.add(arr.get(i));
        }
        interest = (ArrayList<String>) interestarr;
        countInterest = count;
        btnContinue.setText("Tiếp tục: " + count + "/5");
    }

    @Override
    public void changeSelectedIsShow(String selected) {

    }


}