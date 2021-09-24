package com.example.test1.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.test1.InChatActivity;
import com.example.test1.R;
import com.example.test1.UserDetailActivity;

public class LikeFragment extends Fragment {
    ImageView imgX, imgT;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_like, container, false);
        imgX = view.findViewById(R.id.imgX);
        imgT = view.findViewById(R.id.imgTim);
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
