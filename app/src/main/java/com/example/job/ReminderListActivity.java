package com.example.job;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.job.Reminder.CustomReminder;
import com.example.job.clock.ClockAdapter;
import com.example.job.clock.ClockItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ReminderListActivity extends AppCompatActivity {
    ListView clockList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_list);

        User current=Module.getInstance().getUser();
        ArrayList<ClockItem> clocks = current.getClocks();
        ClockAdapter clockAdapter = new ClockAdapter(ReminderListActivity.this, R.layout.clockview_item, clocks);
        clockList = findViewById(R.id.clocklist);
        clockList.setAdapter(clockAdapter);

        findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().finish();
                startActivity(new Intent(ReminderListActivity.this, ReminderSettingsActivity.class));
            }
        });
        findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().finish();
                Intent intent = new Intent(ReminderListActivity.this, ReminderCancelActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.buttonBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().finish();
                finish();
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        User current=Module.getInstance().getUser();
        ArrayList<ClockItem> clocks = current.getClocks();
        ClockAdapter clockAdapter = new ClockAdapter(ReminderListActivity.this, R.layout.clockview_item, clocks);
        clockList.setAdapter(clockAdapter);
    }

}