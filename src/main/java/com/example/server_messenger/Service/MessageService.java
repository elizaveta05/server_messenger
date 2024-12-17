package com.example.server_messenger.Service;

import com.example.server_messenger.Model.DTO.MessagesDTO;
import com.example.server_messenger.Model.Messages;
import com.example.server_messenger.Repository.MessagesRepository;
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
            // Получаем последнее сообщение по времени из чата
            Messages lastMessage = messagesRepository.findTopByChatIdOrderByTimeStampDesc(chatId);

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
}
