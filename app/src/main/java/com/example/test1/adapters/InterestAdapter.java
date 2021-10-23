package com.example.test1.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test1.interfaces.InterestListener;
import com.example.test1.R;

import java.util.ArrayList;
import java.util.List;

public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.ViewHolder> {

    Context context;
    List<String> interestList;
    InterestListener interestListener;
    List<String> interestList1 = new ArrayList<>();
    int countInterest = 0;

    public InterestAdapter(Context context, List<String> interestList, InterestListener interestListener) {
        this.context = context;
        this.interestList = interestList;
        this.interestListener = interestListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_interest, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InterestAdapter.ViewHolder holder, int position) {
        holder.ckbSoThich.setText(interestList.get(position));
        holder.ckbSoThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(countInterest<5){
                    if (holder.ckbSoThich.isChecked()) {
                        interestList1.add(interestList.get(position));
                        holder.ckbSoThich.setBackground(ContextCompat.getDrawable(context, R.drawable.rdo_sex_on));
                        countInterest++;
                    } else {
                        interestList1.remove(interestList.get(position));
                        holder.ckbSoThich.setBackground(ContextCompat.getDrawable(context, R.drawable.cus_btn_sex));
                        countInterest--;
                    }
                }else {
                    if(holder.ckbSoThich.isChecked()){
                        holder.ckbSoThich.setChecked(false);
                    }else{
                        interestList1.remove(interestList.get(position));
                        holder.ckbSoThich.setBackground(ContextCompat.getDrawable(context, R.drawable.cus_btn_sex));
                        countInterest--;
                    }
                }
                Log.e("aaa", String.valueOf(countInterest));
                interestListener.changeInterest(interestList1, countInterest);
            }
        });
    }

    @Override
    public int getItemCount() {
        return interestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox ckbSoThich;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ckbSoThich = itemView.findViewById(R.id.ckbSoThich);
        }
    }
}
