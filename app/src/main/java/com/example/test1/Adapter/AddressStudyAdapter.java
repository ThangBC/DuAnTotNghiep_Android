package com.example.test1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.test1.R;
import com.example.test1.Model.Item;
import com.example.test1.R;

import java.util.List;

public class AddressStudyAdapter extends BaseAdapter {
    private Context context;
    private List<Item> addressStudyList;

    public AddressStudyAdapter(Context context, List<Item> addressStudyList) {
        this.context = context;//sssssss
        this.addressStudyList = addressStudyList;
    }

    @Override
    public int getCount() {
        return addressStudyList != null ? addressStudyList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return addressStudyList.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_spinner, parent, false);
        TextView tvName = convertView.findViewById(R.id.name);

        tvName.setText(addressStudyList.get(position).getName());

        return convertView;
    }
}
