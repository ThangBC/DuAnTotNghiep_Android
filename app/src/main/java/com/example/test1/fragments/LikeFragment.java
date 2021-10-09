package com.example.test1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.test1.HomeActivity;
import com.example.test1.InChatActivity;
import com.example.test1.R;

public class LikeFragment extends Fragment {
    ImageView imgX, imgT,imgLogoHeader;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_like, container, false);
        imgX = view.findViewById(R.id.imgX);
        imgT = view.findViewById(R.id.imgTim);
        imgLogoHeader = view.findViewById(R.id.imgLogoHeader);

        imgLogoHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),HomeActivity.class));
                HomeActivity.fragment = new HomeFragment();
                HomeActivity.selectedItem = R.id.homeId;
                Log.e("aaaa","aaaaa");
            }
        });

        imgX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Úm ba la alaba tráp người dùng đã biến mất", Toast.LENGTH_SHORT).show();
            }
        });
        imgT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), InChatActivity.class));
            }
        });
        return view;
    }

}
