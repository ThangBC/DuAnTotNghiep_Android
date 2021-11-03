package com.example.test1.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test1.R;
import com.example.test1.models.Likes;

import java.util.List;

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.ViewHolder>{

    List<Likes> likesList;
    Context context;

    public LikeAdapter(List<Likes> likesList, Context context) {
        Log.e("like", String.valueOf(likesList.size()));
        this.likesList = likesList;
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
        Log.e("chạy vào đây","ú la la");
        holder.tvNameFrgLike.setText(likesList.get(position).getName());
        holder.imgLikeFrgLike.setImageResource(likesList.get(position).getImage());
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
