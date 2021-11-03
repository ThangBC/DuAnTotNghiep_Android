package com.example.test1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.test1.functions.Loading;
import com.example.test1.models.Users;
import com.example.test1.volleys.FunctionGetListVolley;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public static ProgressBar progressBar;
    public static TextView tv12;
    public static SwipeFlingAdapterView flingAdapterView;
    public static List<Users> userList;
    public static UserAdapter userAdapter;
    ImageView imgLogoHeader;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        flingAdapterView = view.findViewById(R.id.swipe);
        imgLogoHeader = view.findViewById(R.id.imgLogoHeader);
        progressBar = view.findViewById(R.id.progressBar);
        tv12 = view.findViewById(R.id.textView12);

        FunctionGetListVolley functionGetListVolley = new FunctionGetListVolley();
        functionGetListVolley.getListUser_GET(getActivity());

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
                userList.remove(0);
                userAdapter.notifyDataSetChanged();
                if(userList.size()==0){
                    tv12.setText("Hết mất rùi");
                    tv12.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onLeftCardExit(Object o) {

                Toast.makeText(getActivity(), "Không thích", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onRightCardExit(Object o) {
                Toast.makeText(getActivity(), "Thích", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int i) {

            }

            @Override
            public void onScroll(float v) {

            }
        });

        return view;
    }
}
