package com.example.server_messenger.Model.DTO;

import java.sql.Timestamp;

// DTO для передачи данных о сообщениях
public class MessagesDTO {

    private Integer messageId; // Идентификатор сообщения
    private Integer chatId; // Идентификатор чата
    private String userSend; // Пользователь-отправитель
    private String messageText; // Текст сообщения
    private Timestamp timeStamp; // Время отправки сообщения

    // Геттеры и сеттеры
    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public String getUserSend() {
        return userSend;
    }

    public void setUserSend(String userSend) {
        this.userSend = userSend;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public java.sql.Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }
}
