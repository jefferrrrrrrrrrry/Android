package com.example.job.Reminder;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.os.Build;
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
    private HashMap<CustomReminder, AlarmManager> AlarmTable = new HashMap<>();
    private HashMap<CustomReminder, PendingIntent> IntentTable = new HashMap<>();

    public void scheduleReminder(Context context, CustomReminder reminder) {
        if(AlarmTable.containsKey(reminder)){
            Toast.makeText(context ,"已成功设置，无需反复设置", Toast.LENGTH_SHORT).show();
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, reminder.getReminderHour());
        calendar.set(Calendar.MINUTE, reminder.getReminderMinute());
        calendar.set(Calendar.SECOND, 0);
        //-------------------------------------------------------------------------------
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderBroadcastReceiver.class);
        intent.putExtra("content", reminder.getContent());
        intent.putExtra("hour", reminder.getReminderHour());
        intent.putExtra("minute", reminder.getReminderMinute());
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, AlarmTable.size(), intent, PendingIntent.FLAG_IMMUTABLE);

        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);


        //-------------------------------------------------------------------------------
        AlarmTable.put(reminder, alarmMgr);
        IntentTable.put(reminder,alarmIntent);
        StringBuilder s=new StringBuilder();
        System.out.println("设置成功!");
        s.append("设置成功!");
        s.append("每天将于"+reminder.getReminderHour()+"时"+reminder.getReminderMinute()+"分提醒您");
        Toast.makeText(context , s.toString(), Toast.LENGTH_SHORT).show();
        Module.getInstance().getUser().getClocks().add(new ClockItem((reminder.getReminderHour()>=10?"":"0")+reminder.getReminderHour()+":"+
                (reminder.getReminderMinute()>=10?"":"0")+reminder.getReminderMinute(),reminder.getContent()));

        // 使用AlarmManager设置定时提醒
        // 参考：https://developer.android.com/training/scheduling/alarms
    }

    public void cancelReminder(Context context, CustomReminder reminder,int position) {
        if (AlarmTable.containsKey(reminder) && AlarmTable.get(reminder) != null) {
            AlarmTable.get(reminder).cancel(IntentTable.get(reminder));
            AlarmTable.remove(reminder);
            Module.getInstance().getUser().getClocks().remove(position);
        }
        // 取消提醒
        // 参考：https://developer.android.com/training/scheduling/alarms
    }
}