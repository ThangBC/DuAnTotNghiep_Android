package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.test1.Fragment.ChatFragment;
import com.example.test1.Fragment.HomeFragment;

public class InChatActivity extends AppCompatActivity {

    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_chat);
        imgBack = findViewById(R.id.imgBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InChatActivity.this,HomeActivity.class));
                HomeActivity.fragment = new ChatFragment();
                HomeActivity.selectedItem = R.id.chatId;
            }
        });
    }
}