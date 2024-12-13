package com.example.server_messenger.Service;

import com.example.server_messenger.Model.Chats;
import com.example.server_messenger.Repository.ChatsRepository;
import com.example.server_messenger.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatsService {
    private static final Logger logger = LoggerFactory.getLogger(UsersService.class);

    @Autowired
    private ChatsRepository chatsRepository;

    // Метод получения чатов пользователя
    public List<Chats> getChatsByUser(String userId) {
        logger.info("Получение чатов для пользователя с ID: {}", userId);
        List<Chats> userChats = chatsRepository.findChatsByUserId(userId);

        if (userChats != null && !userChats.isEmpty()) {
            logger.info("Чаты пользователя получены успешно.");
        } else {
            logger.warn("Чаты пользователя с ID {} не найдены.", userId);
        }

        return userChats;
    }

    // Метод получения id чата
    public Chats getChatByUsers(String user1_id, String user2_id) {
        logger.info("Получение чата для пользователей: {} и {}", user1_id, user2_id);
        Chats chat = chatsRepository.findChatByUser1IdAndUser2Id(user1_id, user2_id);

        if (chat != null) {
            logger.info("Чат найден: ID {}", chat.getChatId());
        } else {
            logger.warn("Чат не найден для пользователей: {} и {}", user1_id, user2_id);
        }

        return chat;
    }

}

