package com.example.test1.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

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
    List<String> myInterest = new ArrayList<>();
    int countInterest = 0;
    List<String> checkInterest;

    public InterestAdapter(Context context, List<String> interestList,List<String> checkInterest, InterestListener interestListener) {
        this.context = context;
        this.interestList = interestList;
        this.interestListener = interestListener;
        this.checkInterest = checkInterest;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_interest, parent, false);
        InterestAdapter.ViewHolder viewHolder = new InterestAdapter.ViewHolder(view);
        viewHolder.setIsRecyclable(false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InterestAdapter.ViewHolder holder, int position) {

        holder.ckbSoThich.setText(interestList.get(position));

        if(checkInterest!=null){
            Log.e("Đếm đi","count1");
            for (int i = 0;i<checkInterest.size();i++){
                if(holder.ckbSoThich.getText().toString().equals(checkInterest.get(i))){
                    holder.ckbSoThich.setBackground(ContextCompat.getDrawable(context, R.drawable.rdo_sex_on));
                    holder.ckbSoThich.setChecked(true);
                    countInterest++;
                    myInterest.add(interestList.get(position));
                    Log.e("Đếm tiếp","count2");
                }
            }
        }

        holder.ckbSoThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countInterest < 5) {
                    if (holder.ckbSoThich.isChecked()) {
                        myInterest.add(interestList.get(position));
                        holder.ckbSoThich.setBackground(ContextCompat.getDrawable(context, R.drawable.rdo_sex_on));
                        Log.e("hmmmm", "gmmmm");
                        countInterest++;
                    } else {
                        myInterest.remove(interestList.get(position));
                        holder.ckbSoThich.setBackground(ContextCompat.getDrawable(context, R.drawable.cus_btn_sex));
                        countInterest--;
                    }
                } else {
                    if (holder.ckbSoThich.isChecked()) {
                        holder.ckbSoThich.setChecked(false);
                    } else {
                        myInterest.remove(interestList.get(position));
                        holder.ckbSoThich.setBackground(ContextCompat.getDrawable(context, R.drawable.cus_btn_sex));
                        countInterest--;
                    }
                }
                if(checkInterest!=null){
                    for (int i =0;i<checkInterest.size();i++){
                        if(!holder.ckbSoThich.isChecked() && holder.ckbSoThich.getText().toString().equals(checkInterest.get(i))){
                            checkInterest.remove(i);
                        }
                    }
                }
                interestListener.changeInterest(myInterest, countInterest);
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
