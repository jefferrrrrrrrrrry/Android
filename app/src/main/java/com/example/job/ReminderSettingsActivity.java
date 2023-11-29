package com.example.job;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

public class ReminderSettingsActivity extends AppCompatActivity {
    Button buttonPickTime;
    private int selectedHour;
    private int selectedMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_settings);
        buttonPickTime = findViewById(R.id.buttonPickTime);

        buttonPickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });
    }
    private void showTimePickerDialog() {
        // 获取当前时间
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        // 创建时间选择器对话框
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // 用户设置的时间
                        selectedHour = hourOfDay;
                        selectedMinute = minute;

                        // 在这里可以更新UI显示选择的时间
                        // 例如：textViewSelectedTime.setText(String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute));
                    }
                },
                currentHour,
                currentMinute,
                true // 是否为24小时制
        );

        // 显示时间选择器对话框
        timePickerDialog.show();
    }

}