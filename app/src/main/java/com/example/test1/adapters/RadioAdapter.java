package com.example.test1.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test1.R;
import com.example.test1.SettingActivity;
import com.example.test1.interfaces.InterestListener;
import com.example.test1.models.Users;

import java.util.List;

public class RadioAdapter extends RecyclerView.Adapter<RadioAdapter.ViewHolder> {

    List<String> stringList;
    Context context;
    String name;
    private int lastSelectedPosition = -1;
    InterestListener interestListener;

    public RadioAdapter(List<String> stringList, Context context, String name,InterestListener interestListener) {
        this.stringList = stringList;
        this.context = context;
        this.name = name;
        this.interestListener = interestListener;
    }

    @NonNull
    @Override
    public RadioAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_radio_group, parent, false);
        RadioAdapter.ViewHolder viewHolder = new RadioAdapter.ViewHolder(view);
        viewHolder.setIsRecyclable(false);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RadioAdapter.ViewHolder holder, int position) {
        holder.rdoSetting.setText(stringList.get(position));

        if (holder.rdoSetting.getText().toString().equals(name) && lastSelectedPosition == -1) {// tự động tích vào những trường mình đang có
            holder.rdoSetting.setBackground(ContextCompat.getDrawable(context, R.drawable.rdo_sex_on));
            holder.rdoSetting.setChecked(true);
        }

        if (lastSelectedPosition == position) {
            holder.rdoSetting.setBackground(ContextCompat.getDrawable(context, R.drawable.rdo_sex_on));
            holder.rdoSetting.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RadioButton rdoSetting;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rdoSetting = itemView.findViewById(R.id.rdoSetting);
            rdoSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {// lấy ra position của radio đó
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();
                    interestListener.changeSelectedIsShow(rdoSetting.getText().toString());
                }
            });
        }
    }
}
