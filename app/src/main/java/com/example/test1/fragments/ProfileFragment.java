package com.example.test1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.test1.EditImgActivity;
import com.example.test1.EditProActivity;
import com.example.test1.HomeActivity;
import com.example.test1.R;
import com.example.test1.SettingActivity;

public class ProfileFragment extends Fragment {

    ImageView imgSetting,imgEditImage,imgEditProfile,imgLogoHeader;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        imgSetting = view.findViewById(R.id.imgSetting);
        imgEditImage = view.findViewById(R.id.imgEditImage);
        imgEditProfile = view.findViewById(R.id.imgEditProfile);
        imgLogoHeader = view.findViewById(R.id.imgLogoHeader);

        imgLogoHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),HomeActivity.class));
                HomeActivity.fragment = new HomeFragment();
                HomeActivity.selectedItem = R.id.homeId;
            }
        });

        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SettingActivity.class));
            }
        });

        imgEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EditImgActivity.class));
            }
        });

        imgEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EditProActivity.class));
            }
        });

        return view;
    }

}
