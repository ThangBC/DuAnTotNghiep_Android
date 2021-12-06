package com.example.test1.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.test1.HomeActivity;
import com.example.test1.R;
import com.example.test1.UserDetailActivity;
import com.example.test1.models.Users;
import com.example.test1.networking.FunctionFavoriteFAN;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.ViewHolder>{

    List<Users> likesList;
    Context context;
    int check;

    public LikeAdapter(List<Users> likesList, Context context,int check) {
        this.likesList = likesList;
        this.check =check;
        this.context = context;
    }

    @NonNull
    @Override
    public LikeAdapter.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_like, parent, false);
        LikeAdapter.ViewHolder viewHolder = new LikeAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull  LikeAdapter.ViewHolder holder, int position) {
        holder.tvNameFrgLike.setText(likesList.get(position).getName());
        Glide.with(context).load(likesList.get(position).getImageUrl().get(0)).into(holder.imgLikeFrgLike);
        if(check==2){
            holder.imgbtnLikeFrgLike.setVisibility(View.GONE);
        }

        holder.imgLikeFrgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> newImg = new ArrayList<>();
                for (int j =0;j<likesList.get(position).getImageUrl().toArray().length;j++){
                    newImg.add(likesList.get(position).getImageUrl().get(j));
                }
                int year = Calendar.getInstance().get(Calendar.YEAR);
                Intent intent = new Intent(context, UserDetailActivity.class);
                intent.putStringArrayListExtra("img", newImg);
                intent.putExtra("mail",likesList.get(position).getEmail());
                intent.putExtra("name", likesList.get(position).getName());
                intent.putExtra("age",String.valueOf(year - Integer.parseInt(likesList.get(position).getBirthday().substring(likesList.get(position).getBirthday().length()-4))));
                intent.putExtra("address", likesList.get(position).getFacilities());
                intent.putExtra("description",likesList.get(position).getDescription());
                intent.putExtra("sex", likesList.get(position).getGender());
                intent.putExtra("specialized",likesList.get(position).getSpecialized());
                intent.putExtra("course", likesList.get(position).getCourse());
                intent.putStringArrayListExtra("hobbies", (ArrayList<String>) likesList.get(position).getHobbies());
                context.startActivities(new Intent[]{intent});
            }
        });
        holder.imgbtnLikeFrgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FunctionFavoriteFAN functionFavoriteFAN =new FunctionFavoriteFAN();
                functionFavoriteFAN.updateFavorite(context,HomeActivity.users.getEmail(),likesList.get(position).getEmail());
            }
        });
        holder.imgbtnDislikeFrgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FunctionFavoriteFAN functionFavoriteFAN = new FunctionFavoriteFAN();
                if(check==1){
                    functionFavoriteFAN.deleteFavorite(context,HomeActivity.users.getEmail(),likesList.get(position).getEmail(),"Đã xóa "+likesList.get(position).getName()+" khỏi lời mời kết bạn");
                }else if(check==2) {
                    functionFavoriteFAN.deleteFavorite(context,likesList.get(position).getEmail(),HomeActivity.users.getEmail(),"Đã hủy yêu cầu kết bạn với "+likesList.get(position).getName());
                }
                int newPos = holder.getAdapterPosition();
                likesList.remove(newPos);
                notifyItemRemoved(newPos);
                notifyItemRangeChanged(newPos,likesList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return likesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLikeFrgLike,imgbtnLikeFrgLike,imgbtnDislikeFrgLike;
        TextView tvNameFrgLike;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLikeFrgLike = itemView.findViewById(R.id.imgLikeFrgLike);
            imgbtnLikeFrgLike = itemView.findViewById(R.id.imgbtnLikeFrgLike);
            imgbtnDislikeFrgLike = itemView.findViewById(R.id.imgbtnDislikeFrgLike);
            tvNameFrgLike = itemView.findViewById(R.id.tvNameFrgLike);
        }
    }
}
