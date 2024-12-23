package com.example.server_messenger.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Класс WebSocketConfig конфигурирует WebSocket для приложения.
 * Аннотация @Configuration указывает, что это класс конфигурации Spring.
 */
@Configuration
@EnableWebSocketMessageBroker // Включаем поддержку WebSocket с использованием STOMP (Simple Text Oriented Messaging Protocol)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Метод configureMessageBroker настраивает брокер сообщений, который отвечает за маршрутизацию сообщений.
     * @param config объект MessageBrokerRegistry, используемый для настройки брокера сообщений.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Включаем встроенный (простой) брокер сообщений, который будет обрабатывать сообщения для указанных префиксов.
        // "/topic" - общий канал для широковещательных сообщений (broadcast).
        // "/queue" - канал для персональных очередей (например, отправка сообщений конкретному пользователю).
        config.enableSimpleBroker("/topic", "/queue");

        // Устанавливаем префикс "/app" для сообщений, отправляемых с клиента на сервер.
        // Клиенту нужно будет отправлять сообщения на адреса, начинающиеся с "/app".
        config.setApplicationDestinationPrefixes("/app");
    }

    /**
     * Метод registerStompEndpoints регистрирует конечные точки WebSocket, с которыми клиенты могут устанавливать соединение.
     * @param registry объект StompEndpointRegistry для регистрации конечных точек.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Регистрируем конечную точку "/ws", к которой клиенты могут подключаться для установления WebSocket соединения.
        // setAllowedOrigins("*") разрешает подключения с любого домена (для разработки). В продакшене лучше ограничить список доменов.
        registry.addEndpoint("/ws").setAllowedOrigins("*");
    }
}
