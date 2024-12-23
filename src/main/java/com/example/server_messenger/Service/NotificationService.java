package com.example.server_messenger.Service;

import com.example.server_messenger.Controller.WebSocketController;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public void sendNotification(String token, String title, String body) {
        try {
            // Создание сообщения
            Message message = Message.builder()
                    .setToken(token)
                    .putData("title", title)
                    .putData("body", body)
                    .build();

            // Отправка уведомления через Firebase
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Уведомление отправлено: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
