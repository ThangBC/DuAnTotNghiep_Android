package com.example.test1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test1.Fragment.HomeFragment;
import com.example.test1.Model.User;
import com.example.test1.R;
import com.example.test1.UserDetailActivity;

import java.util.List;

public class UserAdapter extends BaseAdapter {

    List<User> userList;
    Context context;

    public UserAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_swpiecards,viewGroup,false);

        TextView tvNameFrgHome = view.findViewById(R.id.tvNameFrgHome);
        TextView tvAgeFrgHome = view.findViewById(R.id.tvAgeFrgHome);
        ImageView imgUserFrgHome = view.findViewById(R.id.imgUserFrgHome);
        ImageView imgLikeFrgHome = view.findViewById(R.id.imgLikeFrgHome);
        ImageView imgDislikeFrgHome = view.findViewById(R.id.imgDislikeFrgHome);
        ImageView imgUserDetailFrgHome = view.findViewById(R.id.imgUserDetailFrgHome);

        tvNameFrgHome.setText(userList.get(i).getName());
        tvAgeFrgHome.setText(String.valueOf(userList.get(i).getAge()));
        imgUserFrgHome.setImageResource(userList.get(i).getImgURL());

        imgLikeFrgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment.flingAdapterView.getTopCardListener().selectRight();
            }
        });
        imgDislikeFrgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment.flingAdapterView.getTopCardListener().selectLeft();
            }
        });
        imgUserDetailFrgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context,UserDetailActivity.class));
            }
        });
        return view;
    }
}
