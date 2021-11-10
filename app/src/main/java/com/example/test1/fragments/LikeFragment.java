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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test1.HomeActivity;
import com.example.test1.R;
import com.example.test1.adapters.LikeAdapter;
import com.example.test1.models.Likes;
import com.example.test1.models.Users;
import com.example.test1.volleys.FunctionFavoriteFAN;
import com.example.test1.volleys.FunctionGetListFAN;

import java.util.ArrayList;
import java.util.List;

public class LikeFragment extends Fragment {
    ImageView imgLogoHeader;
    public static TextView tvCountFavorite;
    public static ProgressBar progressBar;
    public static TextView tv12;
    public static RecyclerView rycLike;
    public static LikeAdapter likeAdapter;
    public static List<Users> likesList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_like, container, false);
        imgLogoHeader = view.findViewById(R.id.imgLogoHeader);
        rycLike= view.findViewById(R.id.rycLike);
        tvCountFavorite = view.findViewById(R.id.tvCountFavorite);
        progressBar = view.findViewById(R.id.progressBar);
        tv12 = view.findViewById(R.id.textView12);

        FunctionFavoriteFAN functionFavoriteFAN = new FunctionFavoriteFAN();
        functionFavoriteFAN.getListFavorite(getActivity(),HomeActivity.users.getEmail());

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
