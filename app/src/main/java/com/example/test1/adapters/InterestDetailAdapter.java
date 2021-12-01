package com.example.test1.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test1.R;

import java.util.List;

public class InterestDetailAdapter  extends RecyclerView.Adapter<InterestDetailAdapter.ViewHolder> {

    Context context;
    List<String> interestDetailList;

    public InterestDetailAdapter(Context context, List<String> interestDetailList) {
        this.context = context;
        this.interestDetailList = interestDetailList;
    }

    @NonNull
    @Override
    public InterestDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_interest_detail, parent, false);
        return new InterestDetailAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InterestDetailAdapter.ViewHolder holder, int position) {
        holder.tvSoThicDetail.setText(interestDetailList.get(position));
    }

    @Override
    public int getItemCount() {
        return interestDetailList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSoThicDetail;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSoThicDetail = itemView.findViewById(R.id.tvSoThicDetail);
        }
    }
}
