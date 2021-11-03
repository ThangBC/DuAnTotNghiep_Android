package com.example.test1.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test1.HomeActivity;
import com.example.test1.InChatActivity;
import com.example.test1.R;
import com.example.test1.adapters.LikeAdapter;
import com.example.test1.models.Likes;

import java.util.ArrayList;
import java.util.List;

public class LikeFragment extends Fragment {
    ImageView imgLogoHeader;
    RecyclerView rycLike;
    LikeAdapter likeAdapter;
    List<Likes> likesList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_like, container, false);
        imgLogoHeader = view.findViewById(R.id.imgLogoHeader);
        rycLike= view.findViewById(R.id.rycLike);

        likesList.add(new Likes("Quân rách 1",R.drawable.quan));
        likesList.add(new Likes("Quân rách 2",R.drawable.quan));
        likesList.add(new Likes("Quân rách 3",R.drawable.quan));
        likesList.add(new Likes("Quân rách 4",R.drawable.quan));

        likeAdapter = new LikeAdapter(likesList,getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
        rycLike.setLayoutManager(gridLayoutManager);
        rycLike.setAdapter(likeAdapter);


        imgLogoHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),HomeActivity.class));
                HomeActivity.fragment = new HomeFragment();
                HomeActivity.selectedItem = R.id.homeId;
                Log.e("aaaa","aaaaa");
            }
        });
        return view;
    }

}
