package com.example.job.Job;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;

import com.example.job.R;
import com.example.job.chat.Chat;

import java.util.ArrayList;
import java.util.Objects;

public class JobAdapter extends ArrayAdapter<JobItem> {
    private Context context;
    private ArrayList<JobItem> data;

    public JobAdapter(@NonNull Context context, int resource, @NonNull ArrayList<JobItem> data) {
        super(context, resource, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.job_item, parent, false);
        }

        TextView jobname = convertView.findViewById(R.id.job_jobname);
        TextView address = convertView.findViewById(R.id.job_address);
        TextView hrname = convertView.findViewById(R.id.job_hrname);
        TextView salary = convertView.findViewById(R.id.job_salary);

        jobname.setText(Objects.requireNonNull(getItem(position)).getJobname());
        address.setText(Objects.requireNonNull(getItem(position)).getAddress());
        hrname.setText(Objects.requireNonNull(getItem(position)).getHrname());
        salary.setText(Objects.requireNonNull(getItem(position)).getSalary());

        SwitchCompat switch1 = convertView.findViewById(R.id.switch1);

        switch1.setChecked(getItem(position).isFavor());

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) { // open
                    getItem(position).setFavor(true);
                    // TODO 添加收藏的操作
                } else {
                    getItem(position).setFavor(false);
                    // TODO 删除收藏的操作
                }
            }
        });

        Button button = convertView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 与boss联系
            }
        });

        Button button1 = convertView.findViewById(R.id.jump);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = getItem(position).getLink();
                // TODO 跳转到该网页
            }
        });
        return convertView;
    }
}
