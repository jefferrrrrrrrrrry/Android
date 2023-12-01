package com.example.job.chat;

public class Chat {
    String name;
    String content;
    String time;
    boolean isRead;

    public Chat(String name, String content, String time) {
        this.name = name;
        this.content = content;
        this.time = time;
        this.isRead = false;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
