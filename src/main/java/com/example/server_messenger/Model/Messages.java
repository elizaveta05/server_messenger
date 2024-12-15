package com.example.server_messenger.Model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "messages")
public class Messages {

    //В базе будет создано по 2 записи по одному сообщению

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer message_id; //Идентификатор сообщения
    private Integer chat_id; //Идентификатор чата в котором содержится сообщение,
    // так как записи чата будет 2, то поэтому эта переменная будет меняться
    private String sender_user; //Пользователь отправитель
    private String message_text; //Текст сообщения
    private Timestamp time_stamp; //Время отправки сообщения

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
        return sender_user;
    }

    public void setUserSend(String sender_user) {
        this.sender_user = sender_user;
    }

    public String getMessageText() {
        return message_text;
    }

    public void setMessageText(String message_text) {
        this.message_text = message_text;
    }

    public Timestamp getTime_stamp() {
        return time_stamp;
    }

    public void setTimeCreated(Timestamp time_stamp) {
        this.time_stamp = time_stamp;
    }
}
