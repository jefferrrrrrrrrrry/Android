package com.example.job.chat;

public class Chat {
    String name;
    String content;
    String time;

    public Chat(String name, String content, String time) {
        this.name = name;
        this.content = content;
        this.time = time;
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
}
