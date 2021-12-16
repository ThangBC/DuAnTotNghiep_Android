package com.example.test1.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test1.R;
import com.example.test1.fragments.NotificationFragment;
import com.example.test1.listeners.UserListener;
import com.example.test1.models.Notification;
import com.example.test1.models.Users;
import com.example.test1.networking.FunctionNotificationFAN;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    List<Notification> notificationList;
    Context context;

    public NotificationAdapter(List<Notification> notificationList, Context context) {
        this.notificationList = notificationList;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_noti, parent, false);
        NotificationAdapter.ViewHolder viewHolder = new NotificationAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {

        if (!notificationList.get(position).getLink().equals("")) {// ẩn textviewlink khi trường link rỗng
            holder.tvLink.setText(notificationList.get(position).getLink());
            holder.tvLink.setVisibility(View.VISIBLE);
            holder.tvLink.setPaintFlags(holder.tvLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }

        holder.tvDate.setText(notificationList.get(position).getDate());
        holder.tvTitle.setText(notificationList.get(position).getTitle());
        holder.tvContent.setText(notificationList.get(position).getContent());
        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FunctionNotificationFAN functionNotificationFAN = new FunctionNotificationFAN();
                functionNotificationFAN.delNotification(context,notificationList.get(position).get_id());
            }
        });
        holder.tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {// khi ấn vào link sẽ ra trang web đó
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(holder.tvLink.getText().toString())));
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView btnDel;
        TextView tvTitle, tvContent, tvLink,tvDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnDel = itemView.findViewById(R.id.btnDel);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvLink = itemView.findViewById(R.id.tvLink);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
