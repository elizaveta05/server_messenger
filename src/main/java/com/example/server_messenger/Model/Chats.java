package com.example.server_messenger.Model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "chats")
public class Chats {

    //В базе будет создано по 2 записи с одного чата

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chats_id; //Идентификатор чата
    private String chat_user_owner; //Владелец чата
    private String other_user; //Другой участник чата
    private Timestamp time_created; //Время создания чата

    // Геттеры и сеттеры
    public Integer getChatId() {
        return chats_id;
    }

    public void setChatId(Integer chats_id) {
        this.chats_id = chats_id;
    }

    public String getChat_user_owner() {
        return chat_user_owner;
    }

    public void setChat_user_owner(String chat_user_owner) {
        this.chat_user_owner = chat_user_owner;
    }

    public String getOther_user() {
        return other_user;
    }

    public void setOther_user(String other_user) {
        this.other_user = other_user;
    }

    public Timestamp getTimeCreated() {
        return time_created;
    }

    public void setTimeCreated(Timestamp time_created) {
        this.time_created = time_created;
    }
}
