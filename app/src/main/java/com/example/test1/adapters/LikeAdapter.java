package com.example.test1.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.test1.HomeActivity;
import com.example.test1.InChatActivity;
import com.example.test1.R;
import com.example.test1.UserDetailActivity;
import com.example.test1.listeners.UserListener;
import com.example.test1.models.User;
import com.example.test1.models.Users;
import com.example.test1.networking.FunctionFriendsFAN;
import com.example.test1.ultilties.Constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.ViewHolder> {

    List<Users> likesList;
    Context context;
    int check;
    UserListener userListener;

    public LikeAdapter(List<Users> likesList, Context context, UserListener userListener, int check) {
        this.likesList = likesList;
        this.check = check;
        this.context = context;
        this.userListener = userListener;
    }

    @NonNull
    @Override
    public LikeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_like, parent, false);
        LikeAdapter.ViewHolder viewHolder = new LikeAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LikeAdapter.ViewHolder holder, int position) {
        holder.tvNameFrgLike.setText(likesList.get(position).getName());
        Glide.with(context).load(likesList.get(position).getImageUrl().get(0)).into(holder.imgLikeFrgLike);
        if (check == 1) {
            holder.imgbtnLikeFrgLike.setVisibility(View.VISIBLE);
            holder.imgbtnLikeFrgLike.setImageResource(R.drawable.ic_baseline_check_24);
        } else if (check == 2) {
            holder.imgbtnLikeFrgLike.setVisibility(View.GONE);
        } else {
            holder.imgbtnLikeFrgLike.setVisibility(View.VISIBLE);
            holder.imgbtnLikeFrgLike.setImageResource(R.drawable.ic_baseline_chat_24);
        }

        holder.imgLikeFrgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> newImg = new ArrayList<>();
                for (int j = 0; j < likesList.get(position).getImageUrl().toArray().length; j++) {
                    newImg.add(likesList.get(position).getImageUrl().get(j));
                }
                int year = Calendar.getInstance().get(Calendar.YEAR);
                Intent intent = new Intent(context, UserDetailActivity.class);
                intent.putStringArrayListExtra("img", newImg);
                intent.putExtra("mail", likesList.get(position).getEmail());
                intent.putExtra("name", likesList.get(position).getName());
                intent.putExtra("age", String.valueOf(year - Integer.parseInt(likesList.get(position).getBirthday().substring(likesList.get(position).getBirthday().length() - 4))));
                intent.putExtra("address", likesList.get(position).getFacilities());
                intent.putExtra("description", likesList.get(position).getDescription());
                intent.putExtra("sex", likesList.get(position).getGender());
                intent.putExtra("specialized", likesList.get(position).getSpecialized());
                intent.putExtra("course", likesList.get(position).getCourse());
                intent.putStringArrayListExtra("hobbies", (ArrayList<String>) likesList.get(position).getHobbies());
                context.startActivities(new Intent[]{intent});
            }
        });
        holder.imgbtnLikeFrgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check == 1) {
                    FunctionFriendsFAN functionFriendsFAN = new FunctionFriendsFAN();
                    functionFriendsFAN.insertFriends(context, HomeActivity.users.getEmail(), likesList.get(position).getEmail(), 1);
                } else if (check == 2) {

                } else {
                    if (userListener != null) {
                        User user = new User();
                        user.email = likesList.get(position).getEmail();
                        user.name = likesList.get(position).getName();
                        user.image = likesList.get(position).getImageUrl().get(0);
                        user.token = likesList.get(position).getToken();
                        user.id = likesList.get(position).get_id();// id ở firebase
                        userListener.onUserClicked(user);
                    }
                }
            }
        });
        holder.imgbtnDislikeFrgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FunctionFriendsFAN functionFriendsFAN = new FunctionFriendsFAN();
                if (check == 1) {// đây là từ chối
                    functionFriendsFAN.deleteFriends(context, HomeActivity.users.getEmail(), likesList.get(position).getEmail()
                            , "Từ chối lời mời của " + likesList.get(position).getName());
                } else if (check == 2) {// đây là xóa yêu cầu
                    functionFriendsFAN.deleteFriends(context, HomeActivity.users.getEmail(), likesList.get(position).getEmail()
                            , "Hủy yêu cầu kết bạn với " + likesList.get(position).getName());
                } else {// đây là xóa bạn bè
                    functionFriendsFAN.deleteFriends(context, HomeActivity.users.getEmail(), likesList.get(position).getEmail()
                            , "Xóa " + likesList.get(position).getName() + " khỏi danh sách bạn bè");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return likesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLikeFrgLike, imgbtnLikeFrgLike, imgbtnDislikeFrgLike;
        TextView tvNameFrgLike;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLikeFrgLike = itemView.findViewById(R.id.imgLikeFrgLike);
            imgbtnLikeFrgLike = itemView.findViewById(R.id.imgbtnLikeFrgLike);
            imgbtnDislikeFrgLike = itemView.findViewById(R.id.imgbtnDislikeFrgLike);
            tvNameFrgLike = itemView.findViewById(R.id.tvNameFrgLike);
        }
    }

    public void filterList(List<Users> filterList) {
        likesList = filterList;
        notifyDataSetChanged();
    }

}
