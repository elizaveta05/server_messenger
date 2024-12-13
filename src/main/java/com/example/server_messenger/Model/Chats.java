package com.example.server_messenger.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "chats")
public class Chats {
    @Id
    private Integer chat_id;
    private String user1_id;
    private String user2_id;
    private Date time_created;

    // Геттеры и сеттеры
    public Integer getChatId() {
        return chat_id;
    }

    public void setChatId(Integer chat_id) {
        this.chat_id = chat_id;
    }

    public String getUser1Id() {
        return user1_id;
    }

    public void setUser1Id(String user1_id) {
        this.user1_id = user1_id;
    }

    public String getUser2Id() {
        return user2_id;
    }

    public void setUser2Id(String user2_id) {
        this.user2_id = user2_id;
    }

    public Date getTimeCreated() {
        return time_created;
    }

    public void setTimeCreated(Date time_created) {
        this.time_created = time_created;
    }
}
