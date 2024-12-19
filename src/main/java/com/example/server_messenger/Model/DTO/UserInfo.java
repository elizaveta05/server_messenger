package com.example.server_messenger.Model.DTO;

public class UserInfo {
    private String user_id;
    private String login;
    private String imageUrl;
    // Геттер для поля login
    public String getUser_id() {
        return user_id;
    }

    // Сеттер для поля login
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    // Геттер для поля login
    public String getLogin() {
        return login;
    }

    // Сеттер для поля login
    public void setLogin(String login) {
        this.login = login;
    }

    // Геттер для поля imageUrl
    public String getImageUrl() {
        return imageUrl;
    }

    // Сеттер для поля imageUrl
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
