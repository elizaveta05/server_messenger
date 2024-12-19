package com.example.server_messenger.Controller;

import com.example.server_messenger.Model.Chats;
import com.example.server_messenger.Model.DTO.*;
import com.example.server_messenger.Model.Messages;
import com.example.server_messenger.Service.ChatsService;
import com.example.server_messenger.Service.MessageService;
import com.example.server_messenger.Service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chats")
public class ChatsController {
    private static final Logger logger = LoggerFactory.getLogger(ChatsController.class);

    @Autowired
    private final ChatsService chatsService;
    private final UsersService usersService;
    private final MessageService messageService;

    public ChatsController(ChatsService chatsService, UsersService usersService, MessageService messageService) {
        this.chatsService = chatsService;
        this.usersService = usersService;
        this.messageService = messageService;
    }

    @PostMapping("/createChatAndSendMessage")
    public ResponseEntity<?> createChatAndSendMessage(@RequestParam String chatUserOwner,
                                                      @RequestParam String otherUser,
                                                      @RequestParam String messageText,
                                                      @RequestParam Timestamp timeCreated) {
        if (chatUserOwner == null || otherUser == null || messageText == null || timeCreated == null) {
            logger.error("Некорректные данные: один или несколько параметров null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input data.");
        }

        // Проверка существующих чатов
        List<Chats> existingChats = chatsService.findChatsBetweenUsers(chatUserOwner, otherUser);

        Integer chatId1 = null;
        Integer chatId2 = null;

        if (existingChats.size() == 0) {
            // Создаем два чата
            chatId1 = chatsService.createChat(chatUserOwner, otherUser, timeCreated);
            chatId2 = chatsService.createChat(otherUser, chatUserOwner, timeCreated);
            logger.info("Созданы два чата с ID: {} и {}", chatId1, chatId2);
        } else if (existingChats.size() == 1) {
            // Если один из чатов отсутствует
            Chats existingChat = existingChats.get(0);
            if (existingChat.getChat_user_owner().equals(chatUserOwner)) {
                chatId1 = existingChat.getChatId();
                chatId2 = chatsService.createChat(otherUser, chatUserOwner, timeCreated);
                logger.info("Создан недостающий чат с ID: {}", chatId2);
            } else {
                chatId2 = existingChat.getChatId();
                chatId1 = chatsService.createChat(chatUserOwner, otherUser, timeCreated);
                logger.info("Создан недостающий чат с ID: {}", chatId1);
            }
        } else {
            // Если оба чата существуют
            chatId1 = existingChats.get(0).getChatId();
            chatId2 = existingChats.get(1).getChatId();
            logger.info("Чаты уже существуют с ID: {} и {}", chatId1, chatId2);
        }

        // Сохранение сообщения в обоих чатах
        MessagesDTO message1 = new MessagesDTO();
        message1.setChatId(chatId1);
        message1.setUserSend(chatUserOwner);
        message1.setMessageText(messageText);
        message1.setTimeStamp(String.valueOf(timeCreated));

        MessagesDTO message2 = new MessagesDTO();
        message2.setChatId(chatId2);
        message2.setUserSend(chatUserOwner);
        message2.setMessageText(messageText);
        message2.setTimeStamp(String.valueOf(timeCreated));

        messageService.saveMessage(message1);
        messageService.saveMessage(message2);

        logger.info("Сообщение сохранено в чатах с ID: {} и {}", chatId1, chatId2);

        return ResponseEntity.ok("Чаты и сообщения успешно созданы.");
    }

    @GetMapping("/getAllChatsForUser")
    public ResponseEntity<?> getAllChatsForUser(@RequestParam String userId) {
        if (userId == null || userId.isEmpty()) {
            logger.error("Некорректные данные: userId равен null или пуст");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input data.");
        }

        // Находим все чаты, которыми владеет пользователь
        List<ChatsDTO> chats = chatsService.findChatForUser(userId);

        if (chats.isEmpty()) {
            logger.info("Чаты для пользователя {} не найдены", userId);
            return ResponseEntity.status(HttpStatus.OK).body("No chats found for the user.");
        }

        // Сбор идентификаторов других пользователей (с кем есть чат)
        List<String> otherUserIds = chats.stream()
                .map(chat -> chat.getChatUserOwner().equals(userId) ? chat.getOtherUser() : chat.getChatUserOwner())
                .distinct()
                .toList();

        // Получаем данные о других пользователях
        Map<String, UserInfo> userInfoMap = usersService.getUsersInfoByIds(otherUserIds);

        // Сбор данных о последних сообщениях
        List<RecentChats> recentChats = chats.stream().map(chat -> {
            RecentChats recentChat = new RecentChats();
            String otherUserId = chat.getOtherUser();

            // Настройка данных о другом пользователе
            UserInfo otherUserInfo = userInfoMap.get(otherUserId);
            if (otherUserInfo != null) {
                recentChat.setUserId(otherUserInfo.getUser_id());
                recentChat.setLogin(otherUserInfo.getLogin());
                recentChat.setImageUrl(otherUserInfo.getImageUrl());
            }

            // Получение последнего сообщения
            Messages lastMessage = messageService.findLastMessageInChat(chat.getChatId());
            if (lastMessage != null) {
                recentChat.setUserSend(lastMessage.getUserSend());
                recentChat.setMessageText(lastMessage.getMessageText());
                recentChat.setTimeStamp(lastMessage.getTimeStamp());
            }

            recentChat.setChatId(chat.getChatId());
            return recentChat;
        }).toList();

        logger.info("Список чатов для пользователя {} успешно собран", userId);
        return ResponseEntity.status(HttpStatus.OK).body(recentChats);
    }

    @Transactional
    @DeleteMapping("/deleteChat")
    public ResponseEntity<?> deleteChatByUser(@RequestParam String userId, @RequestParam Integer chatId) {
        logger.info("Вызван метод удаления чата {} у пользователя {}", chatId, userId);

        // Проверяем наличие чата с таким владельцем
        boolean chatIsPresent = chatsService.checkChat(userId, chatId);
        if (!chatIsPresent) {
            logger.error("Чат с указанным владельцем не найден. Удаление невозможно.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Чат не найден или пользователь не является владельцем.");
        }

        // Проверяем, есть ли сообщения в чате
        boolean hasMessages = chatsService.hasMessagesInChat(chatId);
        if (hasMessages) {
            logger.error("Чат содержит сообщения. .");
        }

        boolean messagesDeleted = messageService.deleteMessagesByChatId(chatId);
        if (!messagesDeleted) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при удалении сообщений.");
        }

        // Удаляем чат
        boolean chatDeleted = chatsService.deleteChat(chatId);
        if (!chatDeleted) {
            logger.error("Не удалось удалить чат.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при удалении чата.");
        }

        logger.info("Чат {} успешно удален пользователем {}", chatId, userId);
        return ResponseEntity.ok("Чат успешно удален.");
    }

    @GetMapping("/getChatId")
    public ResponseEntity<?> getChatId(@RequestParam("chatUserOwner") String chatUserOwner,
                                       @RequestParam("otherUser") String otherUser) {
        if (chatUserOwner == null || otherUser == null) {
            logger.error("Некорректные данные: один или оба параметра null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Некорректные данные");
        }

        Chats chat = chatsService.findChatForUser(chatUserOwner, otherUser);
        if (chat == null) {
            logger.info("Чат между пользователем {} и собеседником {} не найден", chatUserOwner, otherUser);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Чат не найден");
        }

        logger.info("Чат между пользователем {} и собеседником {} найден: chatId = {}", chatUserOwner, otherUser, chat.getChatId());
        return ResponseEntity.ok(chat.getChatId());
    }


}
