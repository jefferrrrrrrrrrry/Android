package com.example.job.Reminder;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.job.Module;
import com.example.job.R;
import com.example.job.ReminderSettingsActivity;
import com.example.job.clock.ClockItem;

import java.util.ArrayList;
import java.util.HashMap;

public class ReminderManager {
    private AlarmManager alarmMgr;
    private HashMap<CustomReminder, AlarmManager> table = new HashMap<>();
    private PendingIntent alarmIntent;

    public ReminderManager() {
    }

    public void scheduleReminder(Context context, CustomReminder reminder) {
        if(table.containsKey(reminder)){
            Toast.makeText(context ,"已成功设置，无需反复设置", Toast.LENGTH_SHORT).show();
            return;
        }
        // Set the alarm to start at approximately 2:00 p.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, reminder.getReminderHour());
        calendar.set(Calendar.MINUTE, reminder.getReminderMinute());
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderBroadcastReceiver.class);
        intent.putExtra("content", reminder.getContent());
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, table.size(), intent, PendingIntent.FLAG_IMMUTABLE);
        // With setInexactRepeating(), you have to use one of the AlarmManager interval
        // constants--in this case, AlarmManager.INTERVAL_DAY.
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmIntent);
        table.put(reminder, alarmMgr);
        StringBuilder s=new StringBuilder();
        s.append("设置成功!");
        s.append("每天将于"+reminder.getReminderHour()+"时"+reminder.getReminderMinute()+"分提醒您");
        Toast.makeText(context , s.toString(), Toast.LENGTH_SHORT).show();
        Module.getInstance().getUser().getClocks().add(new ClockItem((reminder.getReminderHour()>=10?"":"0")+reminder.getReminderHour()+":"+
                (reminder.getReminderMinute()>=10?"":"0")+reminder.getReminderMinute(),reminder.getContent()));

        // 使用AlarmManager设置定时提醒
        // 参考：https://developer.android.com/training/scheduling/alarms
    }

    public void cancelReminder(Context context, CustomReminder reminder) {
        if (table.containsKey(reminder) && table.get(reminder) != null) {
            table.get(reminder).cancel(alarmIntent);
            table.remove(reminder);
            for (ClockItem t:Module.getInstance().getUser().getClocks()) {
                if(t.getTime().equals((reminder.getReminderHour()>=10?"":"0")+reminder.getReminderHour()+
                        ":"+(reminder.getReminderMinute()>=10?"":"0")+ reminder.getReminderMinute())&&
                t.getInfo().equals(reminder.getContent())){
                    Module.getInstance().getUser().getClocks().remove(t);
                    break;
                }
            }
        }
        // 取消提醒
        // 参考：https://developer.android.com/training/scheduling/alarms
    }

    public HashMap<CustomReminder, AlarmManager> getTable() {
        return table;
    }
}