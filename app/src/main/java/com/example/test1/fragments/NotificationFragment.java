package com.example.test1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.test1.EditImgActivity;
import com.example.test1.EditProActivity;
import com.example.test1.HomeActivity;
import com.example.test1.R;
import com.example.test1.SettingActivity;
import com.example.test1.adapters.NotificationAdapter;
import com.example.test1.models.Notification;
import com.example.test1.networking.FunctionNotificationFAN;
import com.example.test1.ultilties.Constants;
import com.example.test1.ultilties.PreferenceManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NotificationFragment extends Fragment {

    TextView tv12;
    ProgressBar progressBar;
    ImageView imgLogoHeader;
    RecyclerView notificationRyc;
    FunctionNotificationFAN functionNotificationFAN;
    NotificationAdapter notificationAdapter;
    List<Notification> notificationList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        imgLogoHeader = view.findViewById(R.id.imgLogoHeader);
        notificationRyc = view.findViewById(R.id.notificationRyc);
        progressBar = view.findViewById(R.id.progressBar);
        tv12 = view.findViewById(R.id.textView12);

        notificationList = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(notificationList, getContext());
        notificationRyc.setLayoutManager(new LinearLayoutManager(getContext()));
        notificationRyc.setAdapter(notificationAdapter);
        notificationAdapter.notifyDataSetChanged();
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        notificationRyc.addItemDecoration(itemDecoration);


        functionNotificationFAN = new FunctionNotificationFAN();
        functionNotificationFAN.getListNotification(getContext(), notificationList, notificationRyc,
                notificationAdapter, progressBar, tv12);


        imgLogoHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });

        return view;
    }
}
