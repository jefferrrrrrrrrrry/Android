package com.example.job.Reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReminderBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String reminderContent = intent.getStringExtra("content");

        // 显示通知或执行其他提醒操作
        // ...
    }
}