package com.example.job.Reminder;

public class CustomReminder {
    private String content;
    private int reminderTime;

    public CustomReminder(String content, int reminderTime) {
        this.content = content;
        this.reminderTime = reminderTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(int reminderTime) {
        this.reminderTime = reminderTime;
    }
}