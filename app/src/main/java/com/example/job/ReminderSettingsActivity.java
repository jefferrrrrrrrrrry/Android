package com.example.job;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.job.Reminder.CustomReminder;

import org.w3c.dom.Text;

public class ReminderSettingsActivity extends AppCompatActivity {
    Button buttonPickTime;
    Button buttonConfirm;
    Button buttonBack;
    EditText text;
    private int selectedHour;
    private int selectedMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_settings);
        buttonPickTime = findViewById(R.id.buttonPickTime);
        buttonConfirm = findViewById(R.id.buttonConfirm);
        buttonBack = findViewById(R.id.buttonBack);
        text=findViewById(R.id.reminderText);

        buttonPickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User current=Module.getInstance().getUser();
                current.getReminderManager().scheduleReminder(getApplicationContext(),new CustomReminder(text.getText().toString(),selectedHour,selectedMinute));
                //finish();
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
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