package com.example.test1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.test1.Model.Item;
import com.example.test1.R;
import com.example.test1.R;

import java.util.List;

public class CourseAdapter extends BaseAdapter {
    private Context context;
    private List<Item> courseList;

    public CourseAdapter(Context context, List<Item> courseList) {
        this.context = context;
        this.courseList = courseList;
    }

    @Override
    public int getCount() {
        return courseList != null ? courseList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return courseList.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_spinner, parent, false);
        TextView tvName = convertView.findViewById(R.id.name);

        tvName.setText(courseList.get(position).getName());

        return convertView;
    }
}
