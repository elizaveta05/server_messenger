package com.example.server_messenger.Service;

import com.example.server_messenger.Model.Messages;
import com.example.server_messenger.Repository.MessagesRepository; // Импортируйте ваш репозиторий
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private MessagesRepository messagesRepository;

    // Метод для получения сообщений по chat_id
    public List<Messages> getMessagesByChatId(Integer chatId) {
        logger.info("Получение сообщений для чата с ID: {}", chatId);
        List<Messages> messages = messagesRepository.findByChatId(chatId);

        if (messages != null && !messages.isEmpty()) {
            logger.info("Найдено {} сообщений для чата с ID {}", messages.size(), chatId);
        } else {
            logger.warn("Сообщения не найдены для чата с ID {}", chatId);
        }
        return messages;
    }

    // Метод для сохранения сообщения
    public Messages saveMessage(Messages message) {
        Messages savedMessage = messagesRepository.save(message);
        logger.info("Сообщение сохранено с ID {}", savedMessage.getMessageId());
        return savedMessage;
    }

}
