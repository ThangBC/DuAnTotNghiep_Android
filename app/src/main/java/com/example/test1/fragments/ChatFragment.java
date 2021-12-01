package com.example.test1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.test1.HomeActivity;
import com.example.test1.InChatActivity;
import com.example.test1.R;

public class ChatFragment extends Fragment {

    LinearLayout lnrChat;
    ImageView imgLogoHeader;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat,container,false);
        lnrChat = view.findViewById(R.id.lnrChat);
        imgLogoHeader = view.findViewById(R.id.imgLogoHeader);

        imgLogoHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),HomeActivity.class));
            }
        });

        lnrChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), InChatActivity.class));
            }
        });

        return view;
    }

}
