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

import com.bumptech.glide.Glide;
import com.example.test1.HomeActivity;
import com.example.test1.fragments.HomeFragment;
import com.example.test1.R;
import com.example.test1.UserDetailActivity;
import com.example.test1.models.Users;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UserAdapter extends BaseAdapter {

    List<Users> userList;
    Context context;
    public static int imgsize = 0;
    List<String> usersListCheck1;
    List<String> usersListCheck2;
    List<String> usersListCheck3;

    public UserAdapter(List<Users> userList, Context context, List<String> usersListCheck1, List<String> usersListCheck2, List<String> usersListCheck3) {
        this.userList = userList;
        this.context = context;
        this.usersListCheck1 = usersListCheck1;
        this.usersListCheck2 = usersListCheck2;
        this.usersListCheck3 = usersListCheck3;
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
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_swpiecards, viewGroup, false);
        TextView tvNameFrgHome = view.findViewById(R.id.tvNameFrgHome);
        TextView tvAgeFrgHome = view.findViewById(R.id.tvAgeFrgHome);
        TextView tvCountImg = view.findViewById(R.id.tvCoutImg);

        ImageView imgUserFrgHome = view.findViewById(R.id.imgUserFrgHome);
        ImageView imgLikeFrgHome = view.findViewById(R.id.imgLikeFrgHome);
        ImageView imgDislikeFrgHome = view.findViewById(R.id.imgDislikeFrgHome);
        ImageView imgUserDetailFrgHome = view.findViewById(R.id.imgUserDetailFrgHome);
        View vLeftImg = view.findViewById(R.id.vLeftImg);
        View vRightImg = view.findViewById(R.id.vRigthImg);


        UserAdapter.imgsize = 0;
        tvCountImg.setText((imgsize + 1) + "/" + userList.get(i).getImageUrl().size());// số lượng và vị trí ảnh
        Log.e("á đù vậy", "chạy vào đây này, đây này");
        tvNameFrgHome.setText(userList.get(i).getName());
        Log.e("asdasdasdasd", userList.get(i).getBirthday());
        int year = Calendar.getInstance().get(Calendar.YEAR);

        tvAgeFrgHome.setText(String.valueOf(year - Integer.parseInt(userList.get(i).getBirthday().substring(0,4))));
        Glide.with(context).load(userList.get(i).getImageUrl().get(0)).into(imgUserFrgHome);

        for (int j = 0; j < usersListCheck1.size(); j++) {// đổi thành icon chat của những user đã là bạn bè mình
            Log.e("size list 1", usersListCheck1.get(j));
            Log.e("equal", usersListCheck1.get(j)+" | "+userList.get(i).getEmail());
            if (userList.get(i).getEmail().equals(usersListCheck1.get(j))) {
                imgLikeFrgHome.setImageResource(R.drawable.ic_baseline_chat_24);
            }
        }
        for (int j = 0; j < usersListCheck2.size(); j++) {// đổi thành icon v của những user đã thích mình
            if (userList.get(i).getEmail().equals(usersListCheck2.get(j))) {
                imgLikeFrgHome.setImageResource(R.drawable.ic_baseline_check_24);
            }
        }
        for (int j = 0; j < usersListCheck3.size(); j++) {// đổi thành icon ... của những user mình đã thích
            if (userList.get(i).getEmail().equals(usersListCheck3.get(j))) {
                imgLikeFrgHome.setImageResource(R.drawable.ic_baseline_more_horiz_24);
            }
        }

        vLeftImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserAdapter.imgsize > 0) {// bấm sang trái, ảnh sẽ lùi --
                    UserAdapter.imgsize--;
                }
                tvCountImg.setText((imgsize + 1) + "/" + userList.get(i).getImageUrl().size());
                Glide.with(context).load(userList.get(i).getImageUrl().get(imgsize)).into(imgUserFrgHome);
            }
        });
        vRightImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {// bấm sang phải ảnh sẽ tiến ++
                if (UserAdapter.imgsize < userList.get(i).getImageUrl().size() - 1) {
                    UserAdapter.imgsize++;
                    Log.e("Chạy vào đây", String.valueOf(imgsize));
                }
                tvCountImg.setText((imgsize + 1) + "/" + userList.get(i).getImageUrl().size());
                Glide.with(context).load(userList.get(i).getImageUrl().get(imgsize)).into(imgUserFrgHome);
            }
        });

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
        imgUserDetailFrgHome.setOnClickListener(new View.OnClickListener() {// truyền dữ liệu sang màn activity detail
            @Override
            public void onClick(View view) {
                ArrayList<String> newImg = new ArrayList<>();
                for (int j = 0; j < userList.get(i).getImageUrl().size(); j++) {
                    newImg.add(userList.get(i).getImageUrl().get(j));
                }
                Intent intent = new Intent(context, UserDetailActivity.class);
                intent.putStringArrayListExtra("img", newImg);
                intent.putExtra("mail", userList.get(i).getEmail());
                intent.putExtra("name", userList.get(i).getName());
                intent.putExtra("age", String.valueOf(year - Integer.parseInt(userList.get(i).getBirthday().substring(0,4))));
                intent.putExtra("address", userList.get(i).getFacilities());
                intent.putExtra("description", userList.get(i).getDescription());
                intent.putExtra("sex", userList.get(i).getGender());
                intent.putExtra("specialized", userList.get(i).getSpecialized());
                intent.putExtra("course", userList.get(i).getCourse());
                intent.putStringArrayListExtra("hobbies", (ArrayList<String>) userList.get(i).getHobbies());
                context.startActivities(new Intent[]{intent});
            }
        });
        return view;
    }

}