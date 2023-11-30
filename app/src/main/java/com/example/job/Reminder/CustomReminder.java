package com.example.job.Reminder;

import java.util.Objects;

public class CustomReminder {
    private String content;
    private int reminderHour;
    private int reminderMinute;

    public CustomReminder(String content, int reminderHour, int reminderMinute) {
        this.content = content;
        this.reminderHour = reminderHour;
        this.reminderMinute = reminderMinute;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReminderHour() {
        return reminderHour;
    }

    public void setReminderHour(int reminderHour) {
        this.reminderHour = reminderHour;
    }

    public int getReminderMinute() {
        return reminderMinute;
    }

    public void setReminderMinute(int reminderMinute) {
        this.reminderMinute = reminderMinute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomReminder that = (CustomReminder) o;
        return reminderHour == that.reminderHour && reminderMinute == that.reminderMinute && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, reminderHour, reminderMinute);
    }
}