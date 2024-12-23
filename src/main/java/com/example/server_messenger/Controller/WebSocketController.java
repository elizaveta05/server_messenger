package com.example.server_messenger.Controller;

import com.example.server_messenger.Model.Chats;
import com.example.server_messenger.Model.DTO.ChatMessageDTO;
import com.example.server_messenger.Model.DTO.ChatsDTO;
import com.example.server_messenger.Model.DTO.MessagesDTO;
import com.example.server_messenger.Model.Messages;
import com.example.server_messenger.Model.UsersProfile;
import com.example.server_messenger.Repository.ChatsRepository;
import com.example.server_messenger.Repository.MessagesRepository;
import com.example.server_messenger.Service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class WebSocketController {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);
    private static final Set<String> onlineUsers = ConcurrentHashMap.newKeySet();// Хранение онлайн-пользователей

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatsController chatsController;

    @Autowired
    private UsersController usersController;

    private NotificationService notificationService;

    // Обработка сообщений от клиента
    @MessageMapping("/sendMessage")
    public void createChatAndSendMessage(ChatMessageDTO messageDTO) {
        String chatUserOwner = messageDTO.getChatUserOwner();
        String otherUser = messageDTO.getOtherUser();
        String messageText = messageDTO.getMessageText();
        Timestamp timeCreated = messageDTO.getTimeCreated();

        // Вызываем метод контроллера для создания чата и сохранения сообщения
        ResponseEntity<?> response = chatsController.createChatAndSendMessage(chatUserOwner, otherUser, messageText, timeCreated);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() instanceof MessagesDTO) {
            // Преобразование ответа в DTO сообщения
            MessagesDTO responseDTO = (MessagesDTO) response.getBody();

            logger.info("Сообщение успешно создано: {}", responseDTO);

            if (onlineUsers.contains(otherUser)) {
                messagingTemplate.convertAndSend("/queue/chat/" + otherUser, responseDTO);
            } else {
                logger.info("Пользователь {} не в сети. Отправляем уведомление.", otherUser);

                UsersProfile usersRecipient = usersController.getProfileUser(otherUser).getBody();
                UsersProfile userSend = usersController.getProfileUser(chatUserOwner).getBody();

                if (usersRecipient != null && userSend != null && usersRecipient.getUserId() != null) {
                    notificationService.sendNotification(
                            usersRecipient.getUserId(),
                            "Новое сообщение от " + userSend.getLogin(),
                            messageText
                    );
                } else {
                    logger.error("Ошибка: Не удалось получить профиль пользователя.");
                }
            }
        } else {
            logger.error("Ошибка при создании чата или отправке сообщения: {}", response.getBody());
        }
    }

    /**
     * Уведомление о подключении пользователя.
     */
    public void userConnected(String username) {
        onlineUsers.add(username);
        logger.info("Пользователь {} подключился.", username);
    }

    /**
     * Уведомление о отключении пользователя.
     */
    public void userDisconnected(String username) {
        onlineUsers.remove(username);
        logger.info("Пользователь {} отключился.", username);
    }
}
