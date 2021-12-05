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
    List<String> myInterest = new ArrayList<>();
    int countInterest = 0;
    List<String> checkInterest;
    List<Integer> countListCheck = new ArrayList<>();

    public InterestAdapter(Context context, List<String> interestList,List<String> checkInterest, InterestListener interestListener) {
        this.context = context;
        this.interestList = interestList;
        this.interestListener = interestListener;
        this.checkInterest = checkInterest;
        if(this.checkInterest!=null){
            for (int i = 0;i<this.interestList.size();i++){
                for (int j = 0;j<this.checkInterest.size();j++){
                    if(this.interestList.get(i).equalsIgnoreCase(this.checkInterest.get(j))){
                        countListCheck.add(i);
                        countInterest++;
                        myInterest.add(interestList.get(i));
                    }
                }
            }
        }
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

        for (int k = 0;k<countListCheck.size();k++){
            if(countListCheck.get(k).equals(position)){
                holder.ckbSoThich.setBackground(ContextCompat.getDrawable(context, R.drawable.rdo_sex_on));
                holder.ckbSoThich.setChecked(true);
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
                    for (int i =0;i<countListCheck.size();i++){
                        if(position == countListCheck.get(i)){
                            countListCheck.remove(i);
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
