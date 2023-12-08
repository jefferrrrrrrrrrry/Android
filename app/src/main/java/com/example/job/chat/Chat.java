package com.example.job.chat;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return Objects.equals(name, chat.name) && Objects.equals(content, chat.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, content);
    }
}
