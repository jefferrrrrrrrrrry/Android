package com.example.job.clock;

public class ClockItem {
    private String time;
    private String info;

    public ClockItem(String time, String info) {
        this.time = time;
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public String getTime() {
        return time;
    }
}
