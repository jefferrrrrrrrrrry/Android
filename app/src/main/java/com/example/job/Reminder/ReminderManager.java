package com.example.job.Reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;

import java.util.ArrayList;
import java.util.HashMap;

public class ReminderManager {
    private AlarmManager alarmMgr;
    private HashMap<CustomReminder,AlarmManager>table=new HashMap<>();
    private PendingIntent alarmIntent;
    public void scheduleReminder(Context context, CustomReminder reminder) {
        // Set the alarm to start at approximately 2:00 p.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, reminder.getReminderHour());
        calendar.set(Calendar.MINUTE, reminder.getReminderMinute());
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderBroadcastReceiver.class);
        intent.putExtra("content", reminder.getContent());
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        // With setInexactRepeating(), you have to use one of the AlarmManager interval
        // constants--in this case, AlarmManager.INTERVAL_DAY.
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmIntent);
        table.put(reminder,alarmMgr);
        // 使用AlarmManager设置定时提醒
        // 参考：https://developer.android.com/training/scheduling/alarms
    }

    public void cancelReminder(Context context, CustomReminder reminder) {
        if(table.containsKey(reminder)&&table.get(reminder)!=null){
            table.get(reminder).cancel(alarmIntent);
            table.remove(reminder);
        }
        // 取消提醒
        // 参考：https://developer.android.com/training/scheduling/alarms
    }
}