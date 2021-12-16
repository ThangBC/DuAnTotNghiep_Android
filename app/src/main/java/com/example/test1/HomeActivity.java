package com.example.test1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.test1.fragments.ChatFragment;
import com.example.test1.fragments.HomeFragment;
import com.example.test1.fragments.LikeFragment;
import com.example.test1.fragments.NotificationFragment;
import com.example.test1.fragments.ProfileFragment;
import com.example.test1.models.Users;
import com.example.test1.networking.FunctionGetListFAN;
import com.example.test1.ultilties.Constants;
import com.example.test1.ultilties.PreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    public static Users users;
    public long backPressedTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNav = findViewById(R.id.bottomNav);

        FunctionGetListFAN functionGetListVolley = new FunctionGetListFAN();
        functionGetListVolley.getListMaster();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).commit();

        bottomNav.setSelectedItemId(R.id.homeId);

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectFragment = null;

                switch (item.getItemId()) {
                    case R.id.homeId:
                        selectFragment = new HomeFragment();
                        break;
                    case R.id.likeId:
                        selectFragment = new LikeFragment();
                        break;
                    case R.id.chatId:
                        selectFragment = new ChatFragment();
                        break;
                    case R.id.notiId:
                        selectFragment = new NotificationFragment();
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

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            this.finishAffinity();
        } else {
            Toast.makeText(this, "Bấm lần nữa để thoát", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}