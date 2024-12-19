package com.example.server_messenger.Service;

import com.example.server_messenger.Model.Chats;
import com.example.server_messenger.Model.DTO.ChatsDTO;
import com.example.server_messenger.Repository.ChatsRepository;
import com.example.server_messenger.Repository.MessagesRepository;
import com.example.server_messenger.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ChatsService {

    private static final Logger logger = LoggerFactory.getLogger(UsersService.class);

    private final ChatsRepository chatsRepository;
    private final MessagesRepository messagesRepository;
    private final MessageService messagesService;

    public ChatsService(ChatsRepository chatsRepository, MessagesRepository messagesRepository, MessageService messagesService) {
        this.chatsRepository = chatsRepository;
        this.messagesRepository = messagesRepository;
        this.messagesService = messagesService;
    }

    // Метод для поиска чатов между двумя пользователями
    public List<Chats> findChatsBetweenUsers(String user1, String user2) {
        return chatsRepository.findChatsBetweenUsers(user1, user2);
    }

    // Метод для поиска чата между владельцем и собеседником
    public Chats findChatForUser(String user1, String user2) {
        return chatsRepository.findChat(user1, user2);
    }

    // Метод для создания чата с возвратом идентификатора
    @Transactional
    public Integer createChat(String chatUserOwner, String otherUser, Timestamp timeCreated) {
        Chats chat = new Chats();
        chat.setChat_user_owner(chatUserOwner);
        chat.setOther_user(otherUser);
        chat.setTimeCreated(timeCreated);
        Chats savedChat = chatsRepository.save(chat);
        return savedChat.getChatId(); // Возвращаем ID созданного чата
    }

    // Метод для поиска чатов одного пользователя
    public List<ChatsDTO> findChatForUser(String userId) {
        List<Chats> chats = chatsRepository.findByChatUserOwner(userId);
        // Преобразуем сущности Chats в DTO
        return chats.stream()
                .map(chat -> new ChatsDTO(chat.getChatId(), chat.getChat_user_owner(), chat.getOther_user()))
                .toList();
    }

    /**
     * Проверяет, является ли пользователь владельцем чата.
     */
    public boolean checkChat(String userId, Integer chatId) {
        return chatsRepository.existsByChatUserOwnerAndChatId(userId, chatId);
    }

    /**
     * Проверяет, есть ли сообщения в указанном чате.
     */
    public boolean hasMessagesInChat(Integer chatId) {
        return messagesRepository.countMessagesByChatId(chatId) > 0;
    }

    /**
     * Удаляет чат по его ID.
     */
    public boolean deleteChat(Integer chatId) {
        try {
            chatsRepository.deleteById(chatId);
            return true;
        } catch (Exception ex) {
            logger.error("Ошибка при удалении чата {}: {}", chatId, ex.getMessage());
            return false;
        }
    }

}

