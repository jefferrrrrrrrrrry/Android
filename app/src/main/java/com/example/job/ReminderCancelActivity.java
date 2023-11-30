package com.example.job;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.job.clock.ClockAdapter;
import com.example.job.clock.ClockItem;

import java.util.ArrayList;

public class ReminderCancelActivity extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_cancel);
        // ArrayList<ClockItem> clocks = (ArrayList<ClockItem>) this.getIntent().getSerializableExtra("clocks");
        findViewById(R.id.cancel_block);
        //ClockAdapter clockAdapter = new ClockAdapter(ReminderCancelActivity.this, R.layout.clockview_item, clocks);
        // listView.setAdapter(clockAdapter);

        findViewById(R.id.buttonCancelBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().finish();
                finish();
            }
        });
    }
}