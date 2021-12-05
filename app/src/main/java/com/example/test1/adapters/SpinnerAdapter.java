package com.example.test1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.test1.R;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;

    public SpinnerAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_spinner, parent, false);
        TextView tvName = convertView.findViewById(R.id.name);

        tvName.setText(list.get(position));

        return convertView;
    }
}
