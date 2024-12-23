package com.example.server_messenger;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Scanner;

public class WebSocketTestClient {

    private static final String URL = "ws://localhost:8080/ws"; // URL сервера

    public static void main(String[] args) {
        // Создаем WebSocket-клиент и STOMP-клиент
        StandardWebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        // Ввод токена пользователя
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите токен пользователя:");
        String userToken = scanner.nextLine();

        // Создаем обработчик сессии с токеном
        StompSessionHandler sessionHandler = new CustomStompSessionHandler(userToken);

        // Подключаемся к серверу
        try {
            StompSession session = stompClient.connect(URL, new WebSocketHttpHeaders(), sessionHandler).get();

            // Подписка на личные сообщения
            session.subscribe("/user/queue/chat", new CustomStompFrameHandler());
            System.out.println("Подключено к WebSocket-серверу: " + URL);

            // Ввод и отправка сообщений
            System.out.println("Введите отправителя сообщения:");
            String chatUserOwner = userToken; // Используем токен как идентификатор отправителя

            while (true) {
                System.out.println("Введите получателя сообщения:");
                String otherUser = scanner.nextLine();

                System.out.println("Введите текст сообщения:");
                String messageText = scanner.nextLine();

                // Создаем заголовки для отправки сообщения
                StompHeaders headers = new StompHeaders();
                headers.setDestination("/app/sendMessage");

                // Создаем и отправляем сообщение
                MessagePayload payload = new MessagePayload(chatUserOwner, otherUser, messageText);
                session.send(headers, payload);
                System.out.println("Сообщение отправлено.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Обработчик сообщений от сервера
    private static class CustomStompFrameHandler implements org.springframework.messaging.simp.stomp.StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders headers) {
            return String.class;
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            System.out.println("Новое сообщение: " + payload.toString());
        }
    }

    // Обработчик сессии
    private static class CustomStompSessionHandler extends StompSessionHandlerAdapter {
        private String token; // токен пользователя (ID)

        public CustomStompSessionHandler(String token) {
            this.token = token;
        }

        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            System.out.println("Успешное подключение к WebSocket-серверу.");

            // Уведомляем сервер о подключении пользователя
            session.send("/app/userConnected", token);
            System.out.println("Уведомление о подключении отправлено для пользователя: " + token);
        }

        @Override
        public void handleTransportError(StompSession session, Throwable exception) {
            System.err.println("Ошибка транспортного уровня WebSocket: " + exception.getMessage());

            // Уведомляем сервер о отключении пользователя
            session.send("/app/userDisconnected", token);
            System.out.println("Уведомление об отключении отправлено для пользователя: " + token);
        }

        @Override
        public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
            System.err.println("Ошибка WebSocket: " + exception.getMessage());
        }
    }


    // Класс полезной нагрузки сообщения
    private static class MessagePayload {
        private String chatUserOwner;
        private String otherUser;
        private String messageText;
        private Timestamp timeCreated; // Новое поле

        public MessagePayload(String chatUserOwner, String otherUser, String messageText) {
            this.chatUserOwner = chatUserOwner;
            this.otherUser = otherUser;
            this.messageText = messageText;
            this.timeCreated = new Timestamp(System.currentTimeMillis()); // Инициализация текущей временной меткой
        }

        // Геттеры и сеттеры для всех полей
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
}