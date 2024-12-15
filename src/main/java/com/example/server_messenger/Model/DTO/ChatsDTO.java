package com.example.server_messenger.Model.DTO;

import com.google.cloud.Timestamp;

// DTO для передачи данных о чатах
public class ChatsDTO {

    private Integer chatId; // Идентификатор чата
    private String chatUserOwner; // Владелец чата
    private String otherUser; // Другой участник чата
    private Timestamp timeCreated; // Время создания чата

    public ChatsDTO(Integer chatId, String chatUserOwner, String otherUser) {
        this.chatId = chatId;
        this.chatUserOwner = chatUserOwner;
        this.otherUser = otherUser;
    }

    public ChatsDTO(Integer chatId, String chatUserOwner, String otherUser, Timestamp timeCreated) {
        this.chatId = chatId;
        this.chatUserOwner = chatUserOwner;
        this.otherUser = otherUser;
        this.timeCreated = timeCreated;
    }
    // Геттеры и сеттеры
    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public String getChatUserOwner() {
        return chatUserOwner;
    }

    public void setChatUserOwner(String chatUserOwner) {
        this.chatUserOwner = chatUserOwner;
    }

    public String getOtherUser() {
        return otherUser;
    }

    public void setOtherUser(String otherUser) {
        this.otherUser = otherUser;
    }

    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }
}
