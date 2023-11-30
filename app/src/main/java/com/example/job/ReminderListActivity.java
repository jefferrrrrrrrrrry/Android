package com.example.job;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.job.clock.ClockAdapter;
import com.example.job.clock.ClockItem;

import java.io.Serializable;
import java.util.ArrayList;

public class ReminderListActivity extends AppCompatActivity {
    ListView clockList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_list);

        ArrayList<ClockItem> clocks = new ArrayList<>();
        ClockAdapter clockAdapter = new ClockAdapter(ReminderListActivity.this, R.layout.clockview_item, clocks);
        clockList = findViewById(R.id.clocklist);
        clockList.setAdapter(clockAdapter);

        clocks.add(new ClockItem("12:30", "提示信息"));
        clocks.add(new ClockItem("13:30", "111"));
        clocks.add(new ClockItem("14:30", "222"));
        // TODO: 只需要操作ArrayList<ClockItem>即可

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
                // intent.putExtra("clocks", clocks);
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
}