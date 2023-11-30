package com.example.job.chat;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends ArrayAdapter<Chat> {
    private Context context;
    private ArrayList<Chat> data;

    public ChatAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Chat> objects) {
        super(context, resource, objects);
        this.context = context;
        this.data = data;
    }
}
