package com.example.job.Job;

import static android.content.Context.MODE_PRIVATE;
import static android.widget.Toast.LENGTH_SHORT;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.net.Uri;
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
import com.example.job.User;
import com.example.job.chat.Chat;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

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
        TextView origin = convertView.findViewById(R.id.job_origin);

        String job = null;
        if (Objects.requireNonNull(getItem(position)).getJobname().length() > 12) {
            job = Objects.requireNonNull(getItem(position)).getJobname().substring(0, 11) + "...";
        } else {
            job = Objects.requireNonNull(getItem(position)).getJobname();
        }
        jobname.setText(job);
        address.setText(Objects.requireNonNull(getItem(position)).getAddress());
        hrname.setText(Objects.requireNonNull(getItem(position)).getHrname());
        salary.setText(Objects.requireNonNull(getItem(position)).getSalary());
        String originvalue = "来源：" + Objects.requireNonNull(getItem(position)).getOrigin();
        origin.setText(originvalue);

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
                    notifyDataSetChanged();
                    Toast.makeText(context, "取消收藏成功", LENGTH_SHORT).show();
                    Module.getInstance().transmitfav(getItem(position));
                } else {
                    getItem(position).setFavor(true);
                    favor.setText("取消收藏");
                    notifyDataSetChanged();
                    Toast.makeText(context, "收藏成功", LENGTH_SHORT).show();
                    Module.getInstance().transmitfav(getItem(position));
                }
            }
        });


        Button button = convertView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.icu.util.Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                String time=(calendar.get(Calendar.HOUR_OF_DAY)<10?"0":"")+calendar.get(Calendar.HOUR_OF_DAY)+":"+
                        (calendar.get(Calendar.MINUTE)<10?"0":"")+calendar.get(Calendar.MINUTE)+ ":"+
                        (calendar.get(Calendar.SECOND)<10?"0":"")+calendar.get(Calendar.SECOND);
                boolean succ = Module.getInstance().getUser().addChat(new Chat(getItem(position).getHrname(), textGenerator(), time));
                if (!succ) {
                    Toast.makeText(getContext(), "已与HR联系", LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "已与HR联系，请查看通知", LENGTH_SHORT).show();
                }
            }
        });

        Button button1 = convertView.findViewById(R.id.jump);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = getItem(position).getLink();
                if (link == null) {
                    Toast.makeText(getContext(), "该网站未提供链接", LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(link));
                    getContext().startActivity(intent);
                }
            }
        });
        for (int i = 0; i < data.size(); ++i) {
            if (data.get(i).isFull()) {
                jobname.setTextColor(Color.parseColor("666666"));
                address.setTextColor(Color.parseColor("666666"));
                hrname.setTextColor(Color.parseColor("666666"));
                salary.setTextColor(Color.parseColor("666666"));
                origin.setTextColor(Color.parseColor("666666"));
            }
        }
        return convertView;
    }

    static String [] text = new String[] {
            "您好，欢迎联系我们。请问有什么我可以帮您的吗？",
            "你好，我是公司的HR。您申请的职位很有意思，我想了解更多您的背景和经验。",
            "您好，我是HR，很高兴接到您的信息。请问您对我们公司的职位有什么疑问吗？",
            "你好，感谢您联系我们，我是HR，期待与您分享更多关于我们公司的职位和文化。",
            "你好，我是HR，很高兴你对我们公司的职位感兴趣。请问您现在有时间吗？我们可以电话或视频通话沟通一下。",
            "我是HR，很乐意为您解答关于我们公司的任何问题。",
            "您好，请问我能为您提供什么帮助？我是HR，负责筛选和安排面试的工作。",
            "你好，非常感谢您对我们公司的职位表达兴趣。我是HR，我将会协助您完成后续的招聘流程。"
    };

    public String textGenerator() {
        return text[new Random().nextInt(8)];
    }
}
