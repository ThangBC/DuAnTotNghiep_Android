package com.example.test1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.test1.InChatActivity;
import com.example.test1.adapters.LikeAdapter;
import com.example.test1.adapters.UserAdapter;
import com.example.test1.HomeActivity;
import com.example.test1.R;
import com.example.test1.models.Users;
import com.example.test1.networking.FunctionFriendsFAN;
import com.example.test1.networking.FunctionUserFAN;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    ProgressBar progressBar;
    TextView tv12;
    public static SwipeFlingAdapterView flingAdapterView;
    List<Users> userList = new ArrayList<>();
    UserAdapter userAdapter;
    ImageView imgLogoHeader;
    ImageButton imgReload;
    List<String> usersListCheck1 = new ArrayList<>();
    List<String> usersListCheck2 = new ArrayList<>();
    List<String> usersListCheck3 = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        flingAdapterView = view.findViewById(R.id.swipe);
        imgLogoHeader = view.findViewById(R.id.imgLogoHeader);
        progressBar = view.findViewById(R.id.progressBar);
        tv12 = view.findViewById(R.id.textView12);
        imgReload = view.findViewById(R.id.imgbtnreload);

        userAdapter = new UserAdapter(userList, getContext(), usersListCheck1, usersListCheck2, usersListCheck3);
        flingAdapterView.setAdapter(userAdapter);

        FunctionUserFAN functionUserFAN = new FunctionUserFAN();
        functionUserFAN.checkListUser1(getActivity(), userList, tv12, imgReload, progressBar, userAdapter,
                usersListCheck1, usersListCheck2, usersListCheck3,flingAdapterView);

        imgLogoHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });

        flingAdapterView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

            }

            @Override// đây là không thích
            public void onLeftCardExit(Object o) {
                userList.remove(0);
                userAdapter.notifyDataSetChanged();
            }

            @Override// đây là thích
            public void onRightCardExit(Object o) {

                for (int i = 0; i < usersListCheck1.size(); i++) {
                    if (userList.get(0).getEmail().equals(usersListCheck1.get(i))) {
                        Toast.makeText(getActivity(), "chạy vào nhắn tin", Toast.LENGTH_SHORT).show();
                        userList.remove(0);
                        userAdapter.notifyDataSetChanged();
                        return;
                    }
                }
                FunctionFriendsFAN functionFriendsFAN = new FunctionFriendsFAN();
                functionFriendsFAN.insertFriends(getActivity(), userList.get(0).getEmail(), 0);
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
                            getContext().startActivity(new Intent(getContext(), HomeActivity.class));
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
