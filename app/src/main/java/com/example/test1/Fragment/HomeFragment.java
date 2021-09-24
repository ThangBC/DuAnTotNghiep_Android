package com.example.test1.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.example.test1.Adapter.UserAdapter;
import com.example.test1.Model.User;
import com.example.test1.R;
import com.example.test1.UserDetailActivity;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public static SwipeFlingAdapterView flingAdapterView;
    List<User> userList;
    UserAdapter userAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home,container,false);
        flingAdapterView = view.findViewById(R.id.swipe);

        userList = new ArrayList<>();
        userList.add(new User(R.drawable.nam,"Nguyễn Hải Nam",20));
        userList.add(new User(R.drawable.quan,"Nguyễn Hoàng Quân",20));
        userList.add(new User(R.drawable.banduyn,"Nguyễn Thị Duyên",20));
        userAdapter = new UserAdapter(userList,getActivity());
        flingAdapterView.setAdapter(userAdapter);


        flingAdapterView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                userList.remove(0);
                userAdapter.notifyDataSetChanged();
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
