package com.example.test1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.test1.fragments.ChatFragment;
import com.example.test1.fragments.HomeFragment;
import com.example.test1.fragments.LikeFragment;
import com.example.test1.fragments.ProfileFragment;
import com.example.test1.models.Users;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    public static int selectedItem = R.id.homeId;
    public static Fragment fragment = new HomeFragment();
    public static Users users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNav = findViewById(R.id.bottomNav);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();

        bottomNav.setSelectedItemId(selectedItem);
        
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectFragment = null;

                switch (item.getItemId()){
                    case R.id.homeId:
                        selectFragment = new HomeFragment();
                        break;
                    case R.id.likeId:
                        selectFragment = new LikeFragment();
                        break;
                    case R.id.chatId:
                        selectFragment = new ChatFragment();
                        break;
                    case R.id.proId:
                        selectFragment = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                        selectFragment).commit();
                return true;
            }
        });
    }
}