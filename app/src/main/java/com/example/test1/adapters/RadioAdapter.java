package com.example.test1.adapters;

import android.content.Context;
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
import com.example.test1.models.Users;

import java.util.List;

public class RadioAdapter extends RecyclerView.Adapter<RadioAdapter.ViewHolder>{

    List<String> stringList;
    Context context;
    String name;
    public RadioAdapter(List<String> stringList, Context context,String name) {
        this.stringList = stringList;
        this.context = context;
        this.name = name;
    }

    @NonNull
    @Override
    public RadioAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_radio_group, parent, false);
        RadioAdapter.ViewHolder viewHolder = new RadioAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RadioAdapter.ViewHolder holder, int position) {
        holder.rdobtn.setText(stringList.get(position));
        if(holder.rdobtn.getText().toString().equals(name)){
            holder.rdobtn.setBackground(ContextCompat.getDrawable(context, R.drawable.rdo_sex_on));
            holder.rdobtn.setChecked(true);
        }
        holder.rdobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.rdobtn.setBackground(ContextCompat.getDrawable(context, R.drawable.rdo_sex_on));
                Toast.makeText(context, holder.rdobtn.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RadioGroup rdoGrp;
        RadioButton rdobtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rdoGrp = itemView.findViewById(R.id.rdoGrp);
            rdobtn = itemView.findViewById(R.id.rdobtn);
        }
    }
}
