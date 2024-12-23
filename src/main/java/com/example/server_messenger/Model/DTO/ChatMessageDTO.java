package com.example.server_messenger.Model.DTO;

import java.sql.Timestamp;

public class ChatMessageDTO {
    private String chatUserOwner;
    private String otherUser;
    private String messageText;
    private Timestamp timeCreated;

    // Геттеры и сеттеры
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

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }
}
