package com.example.server_messenger.Model.DTO;

public class UserInfo {
    private String login;
    private String imageUrl;

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
