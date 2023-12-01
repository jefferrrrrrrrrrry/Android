package com.example.job;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.job.clock.ClockAdapter;
import com.example.job.clock.ClockItem;

import java.util.ArrayList;

public class ReminderCancelActivity extends AppCompatActivity {
    ListView listView;
    ClockAdapter clockAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_cancel);
        ArrayList<ClockItem> clocks = Module.getInstance().getUser().getClocks();
        listView=findViewById(R.id.cancel_block);
        clockAdapter = new ClockAdapter(ReminderCancelActivity.this, R.layout.clockview_item, clocks);
        listView.setAdapter(clockAdapter);

        findViewById(R.id.buttonCancelBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getActivity().finish();
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteConfirmationDialog(position);
            }
        });
    }
    private void showDeleteConfirmationDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确认删除?此操作不可逆")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked Yes button
                        deleteReminder(position);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked No button
                        // Do nothing, or add any additional functionality
                    }
                });
        // Create and show the AlertDialog
        builder.create().show();
    }

    private void deleteReminder(int position) {
        Module.getInstance().getUser().getClocks().remove(position);
        clockAdapter.notifyDataSetChanged();
    }
}
