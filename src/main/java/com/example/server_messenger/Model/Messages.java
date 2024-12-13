package com.example.server_messenger.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.w3c.dom.Text;

import java.sql.Timestamp;

@Entity
@Table(name = "messages")
public class Messages {
    @Id
    private Integer message_id;
    private Integer chat_id;
    private String user_send;
    private Text message_text; // Исправлено на message_text
    private Timestamp time_created;

    // Геттеры и сеттеры
    public Integer getMessageId() {
        return message_id;
    }

    public void setMessageId(Integer message_id) {
        this.message_id = message_id;
    }

    public Integer getChatId() {
        return chat_id;
    }

    public void setChatId(Integer chat_id) {
        this.chat_id = chat_id;
    }

    public String getUserSend() {
        return user_send;
    }

    public void setUserSend(String user_send) {
        this.user_send = user_send;
    }

    public Text getMessageText() {
        return message_text;
    }

    public void setMessageText(Text message_text) {
        this.message_text = message_text;
    }

    public Timestamp getTimeCreated() {
        return time_created;
    }

    public void setTimeCreated(Timestamp time_created) {
        this.time_created = time_created;
    }
}
