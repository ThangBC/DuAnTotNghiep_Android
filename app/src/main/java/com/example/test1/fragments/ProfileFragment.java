package com.example.test1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.test1.EditImgActivity;
import com.example.test1.EditProActivity;
import com.example.test1.HomeActivity;
import com.example.test1.R;
import com.example.test1.SettingActivity;
import com.example.test1.ultilties.Constants;
import com.example.test1.ultilties.PreferenceManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class ProfileFragment extends Fragment {

    ImageView imgSetting,imgEditImage,imgEditProfile,imgLogoHeader,imgMainPro;
    TextView tvNamePro,tvAgePro;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        imgSetting = view.findViewById(R.id.imgSetting);
        imgEditImage = view.findViewById(R.id.imgEditImage);
        imgEditProfile = view.findViewById(R.id.imgEditProfile);
        imgLogoHeader = view.findViewById(R.id.imgLogoHeader);
        imgMainPro = view.findViewById(R.id.imgMainPro);
        tvNamePro = view.findViewById(R.id.tvNamePro);
        tvAgePro = view.findViewById(R.id.tvAgePro);

        int year = Calendar.getInstance().get(Calendar.YEAR);

        tvNamePro.setText(HomeActivity.users.getName());
        tvAgePro.setText(String.valueOf(year - Integer.parseInt(HomeActivity.users.getBirthday().substring(0,4))));
        Glide.with(getActivity()).load(String.valueOf(HomeActivity.users.getImageUrl().get(0))).into(imgMainPro);
        imgLogoHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),HomeActivity.class));
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
