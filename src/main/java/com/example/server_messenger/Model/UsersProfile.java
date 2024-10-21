package com.example.server_messenger.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "users_profile")
public class UsersProfile {
    @Id
    private String userId;
    private String login;
    private String phoneNumber;
    private String image_url;


    // Геттеры и сеттеры
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImage_url(){
        return image_url;
    }

    public void setImage_url(String image_url){
        this.image_url=image_url;
    }
}
