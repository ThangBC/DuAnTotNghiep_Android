package com.example.test1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test1.HomeActivity;
import com.example.test1.R;
import com.example.test1.adapters.LikeAdapter;
import com.example.test1.models.Users;
import com.example.test1.networking.FunctionFavoriteFAN;
import com.example.test1.networking.FunctionFriendsFAN;

import java.util.ArrayList;
import java.util.List;

public class ListFriendsFragment extends Fragment {
    public static TextView tvCountListFriend,tv12;
    public static ProgressBar progressBar;
    public static RecyclerView rycListFriend;
    public static LikeAdapter likeAdapter;
    public static List<Users> likesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_friend, container, false);
        tvCountListFriend = view.findViewById(R.id.tvCountListFriend);
        progressBar = view.findViewById(R.id.progressBar);
        tv12 = view.findViewById(R.id.textView12);
        rycListFriend = view.findViewById(R.id.rycListFriend);

        FunctionFriendsFAN functionFriendsFAN = new FunctionFriendsFAN();
        functionFriendsFAN.getListFriends(getActivity(), HomeActivity.users.getEmail());
        return view;
    }
}
