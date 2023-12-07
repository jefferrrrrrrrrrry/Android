package com.example.job.Job;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.job.Module;
import com.example.job.R;
import com.example.job.chat.Chat;

import java.util.ArrayList;
import java.util.Objects;

public class JobAdapter extends ArrayAdapter<JobItem> {
    private Context context;
    private ArrayList<JobItem> data;
    Button favor;

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

        favor = convertView.findViewById(R.id.favor);
        if (getItem(position).isFavor()) {
            favor.setText("取消收藏");
        } else {
            favor.setText("收藏");
        }
        favor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getItem(position).isFavor()) {
                    getItem(position).setFavor(false);
                    favor.setText("收藏");
                    Toast.makeText(context, "取消收藏成功", Toast.LENGTH_SHORT).show();
                    Module.getInstance().transmitfav(getItem(position));
                    // TODO: 移除收藏夹
                } else {
                    getItem(position).setFavor(true);
                    favor.setText("取消收藏");
                    Toast.makeText(context, "收藏成功", Toast.LENGTH_SHORT).show();
                    // TODO: 加入收藏夹
                    Module.getInstance().transmitfav(getItem(position));
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
