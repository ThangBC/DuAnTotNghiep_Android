package com.example.test1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.test1.adapters.UserAdapter;
import com.example.test1.HomeActivity;
import com.example.test1.R;
import com.example.test1.models.Users;
import com.example.test1.networking.FunctionFavoriteFAN;
import com.example.test1.networking.FunctionUserFAN;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    ProgressBar progressBar;
    TextView tv12;
    public static SwipeFlingAdapterView flingAdapterView;
    List<Users> userList = new ArrayList<>();
    public static UserAdapter userAdapter;
    ImageView imgLogoHeader;
    ImageButton imgReload;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        flingAdapterView = view.findViewById(R.id.swipe);
        imgLogoHeader = view.findViewById(R.id.imgLogoHeader);
        progressBar = view.findViewById(R.id.progressBar);
        tv12 = view.findViewById(R.id.textView12);
        imgReload = view.findViewById(R.id.imgbtnreload);


        FunctionUserFAN functionUserFAN = new FunctionUserFAN();
        functionUserFAN.getListUser(getActivity(), userList, tv12,imgReload, progressBar, flingAdapterView);

        imgLogoHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), HomeActivity.class));
                HomeActivity.fragment = new HomeFragment();
                HomeActivity.selectedItem = R.id.homeId;
            }
        });

        flingAdapterView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

            }

            @Override
            public void onLeftCardExit(Object o) {
                Toast.makeText(getActivity(), "Không thích", Toast.LENGTH_SHORT).show();
                userList.remove(0);
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onRightCardExit(Object o) {
                FunctionFavoriteFAN functionFavoriteFAN = new FunctionFavoriteFAN();
                functionFavoriteFAN.insertFavorite(getActivity(), userList.get(0).getEmail(), HomeActivity.users.getEmail());
                userList.remove(0);
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onAdapterAboutToEmpty(int i) {
                if (userList.size() == 0) {
                    tv12.setVisibility(View.VISIBLE);
                    imgReload.setVisibility(View.VISIBLE);
                    tv12.setText("Đã tìm hết, nhấn nút phía dưới để làm mới");
                    imgReload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                            HomeActivity.fragment = new HomeFragment();
                            HomeActivity.selectedItem = R.id.homeId;
                        }
                    });
                }
            }

            @Override
            public void onScroll(float v) {

            }
        });

        return view;
    }
}
