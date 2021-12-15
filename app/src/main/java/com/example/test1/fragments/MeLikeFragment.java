package com.example.test1.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.test1.HomeActivity;
import com.example.test1.R;
import com.example.test1.adapters.LikeAdapter;
import com.example.test1.models.Users;
import com.example.test1.networking.FunctionFriendsFAN;

import java.util.ArrayList;
import java.util.List;

public class MeLikeFragment extends Fragment {

    TextView tvCountFavorite, tv12;
    ProgressBar progressBar;
    RecyclerView rycLike;
    public static LikeAdapter likeAdapter;
    List<Users> likesList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        tvCountFavorite = view.findViewById(R.id.tvCountFavorite);
        progressBar = view.findViewById(R.id.progressBar);
        tv12 = view.findViewById(R.id.textView12);
        rycLike = view.findViewById(R.id.rycListFavorite);

        tvCountFavorite.setText("0 lượt đã thích");

        FunctionFriendsFAN functionFriendsFAN = new FunctionFriendsFAN();
        functionFriendsFAN.getListOfRequestSend(getActivity(), likesList, rycLike,
                progressBar, tvCountFavorite, tv12);

        return view;
    }
}
