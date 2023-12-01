package com.example.job.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.job.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatAdapter extends ArrayAdapter<Chat> {
    private Context context;
    private ArrayList<Chat> data;

    public ChatAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Chat> data) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.chat, parent, false);
        }

        TextView name = convertView.findViewById(R.id.chatname);
        TextView content = convertView.findViewById(R.id.chatcontent);
        TextView time = convertView.findViewById(R.id.chattime);

        name.setText(Objects.requireNonNull(getItem(position)).getName());
        content.setText(Objects.requireNonNull(getItem(position)).getContent());
        time.setText(Objects.requireNonNull(getItem(position)).getTime());

        TextView isRead = convertView.findViewById(R.id.message_read);
        if (Objects.requireNonNull(getItem(position)).isRead()) {
            isRead.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }
}
