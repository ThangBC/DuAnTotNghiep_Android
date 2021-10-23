package com.example.test1.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test1.fragments.HomeFragment;
import com.example.test1.functions.LoadImage;
import com.example.test1.models.User;
import com.example.test1.R;
import com.example.test1.UserDetailActivity;
import com.example.test1.models.Users;

import java.io.File;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UserAdapter extends BaseAdapter {

    List<Users> userList;
    Context context;

    public UserAdapter(List<Users> userList, Context context) {
        this.userList = userList;
        this.context = context;
        Log.e("Quân rách", String.valueOf(userList.size()));
    }

    @Override
    public int getCount() {
        Log.e("getCount",String.valueOf(userList.size()));
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
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_swpiecards, viewGroup, false);
        TextView tvNameFrgHome = view.findViewById(R.id.tvNameFrgHome);
        TextView tvAgeFrgHome = view.findViewById(R.id.tvAgeFrgHome);
        ImageView imgUserFrgHome = view.findViewById(R.id.imgUserFrgHome);
        ImageView imgLikeFrgHome = view.findViewById(R.id.imgLikeFrgHome);
        ImageView imgDislikeFrgHome = view.findViewById(R.id.imgDislikeFrgHome);
        ImageView imgUserDetailFrgHome = view.findViewById(R.id.imgUserDetailFrgHome);

        Log.e("á đù vậy", "chạy vào đây này, đây này");
        tvNameFrgHome.setText(userList.get(i).getName());
        Log.e("asdasdasdasd", userList.get(i).getBirthDay());
        int year = Calendar.getInstance().get(Calendar.YEAR);
        Log.e("con cac", String.valueOf(userList.get(i).getAvatars().get(0)));
        tvAgeFrgHome.setText(String.valueOf(year - Integer.parseInt( userList.get(i).getBirthDay().substring(6))));
        imgUserFrgHome.setScaleType(ImageView.ScaleType.CENTER_CROP);
        new LoadImage(viewGroup.getContext(), imgUserFrgHome).execute("https://poly-dating.herokuapp.com/"+userList.get(i).getAvatars().get(0));

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
                context.startActivity(new Intent(context, UserDetailActivity.class));
            }
        });
        return view;
    }

}
