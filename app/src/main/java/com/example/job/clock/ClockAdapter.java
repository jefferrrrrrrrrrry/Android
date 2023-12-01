package com.example.job.clock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.job.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClockAdapter extends ArrayAdapter<ClockItem> {
    private Context context;
    private ArrayList<ClockItem> data;

    public ClockAdapter(Context context, int resource, ArrayList<ClockItem> data) {
        super(context, resource, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.clockview_item, parent, false);
        }

        TextView time = convertView.findViewById(R.id.timetext);
        TextView info = convertView.findViewById(R.id.infotext);

        time.setText(Objects.requireNonNull(getItem(position)).getTime());
        info.setText(Objects.requireNonNull(getItem(position)).getInfo());
        return convertView;
    }

    public void remove(int pos) {
        data.remove(pos);
    }
}