package com.example.job.chat;

public class Chat {
    String name;
    String content;
    String tel;

    public Chat(String name, String content, String tel) {
        this.name = name;
        this.content = content;
        this.tel = tel;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public String getTel() {
        return tel;
    }
}
