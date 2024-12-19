package com.example.server_messenger.Service;

import com.example.server_messenger.Model.DTO.MessagesDTO;
import com.example.server_messenger.Model.Messages;
import com.example.server_messenger.Repository.MessagesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private MessagesRepository messagesRepository;

    public MessageService(MessagesRepository messagesRepository) {
        this.messagesRepository = messagesRepository;
    }


    // Метод для сохранения сообщения
    public void saveMessage(MessagesDTO messageDTO) {
        Messages message = new Messages();
        message.setChatId(messageDTO.getChatId());
        message.setUserSend(messageDTO.getUserSend());
        message.setMessageText(messageDTO.getMessageText());
        message.setTimeStamp(messageDTO.getTimeStamp());

        Messages savedMessage = messagesRepository.save(message);
        logger.info("Сообщение сохранено с ID {}", savedMessage.getMessageId());
    }

    // Метод для получения последнего сообщения в чате
    public Messages findLastMessageInChat(Integer chatId) {
        try {
            Messages lastMessage = messagesRepository.findLastMessageInChat(chatId);

            if (lastMessage != null) {
                logger.info("Последнее сообщение в чате {} найдено: {}", chatId, lastMessage.getMessageText());
                return lastMessage;
            } else {
                logger.warn("В чате {} сообщений не найдено", chatId);
                return null;
            }
        } catch (Exception ex) {
            logger.error("Ошибка при поиске последнего сообщения в чате {}: {}", chatId, ex.getMessage());
            return null;
        }
    }

    // Метод удаления всех сообщений в чате при удаления чата
    @Transactional
    public boolean deleteMessagesByChatId(Integer chatId) {
        try {
            messagesRepository.deleteByChatId(chatId);
            return true;
        } catch (Exception ex) {
            // Логируем ошибку
            Logger logger = LoggerFactory.getLogger(MessageService.class);
            logger.error("Ошибка при удалении сообщений из чата {}: {}", chatId, ex.getMessage());
            return false;
        }
    }

    // Метод, получающий историю сообщений в чате
    public List<Messages> findMessageHistory(Integer chatId) {
        try {
            List<Messages> messages = messagesRepository.findMessagesByChatId(chatId);
            if (!messages.isEmpty()) {
                logger.info("Найдено {} сообщений в чате {}", messages.size(), chatId);
                return messages;
            } else {
                logger.warn("В чате {} сообщений не найдено", chatId);
                return List.of(); // Возвращаем пустой список
            }
        } catch (Exception ex) {
            logger.error("Ошибка при получении истории сообщений для чата {}: {}", chatId, ex.getMessage());
            return List.of(); // Возвращаем пустой список в случае ошибки
        }
    }

}
