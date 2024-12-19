package com.example.server_messenger.Controller;

import com.example.server_messenger.Model.Messages;
import com.example.server_messenger.Service.ChatsService;
import com.example.server_messenger.Service.MessageService;
import com.example.server_messenger.Service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    @Autowired
    private final ChatsService chatsService;
    private final UsersService usersService;
    private final MessageService messageService;

    public MessageController(ChatsService chatsService, UsersService usersService, MessageService messageService) {
        this.chatsService = chatsService;
        this.usersService = usersService;
        this.messageService = messageService;
    }

    @GetMapping("/getMessageHistory")
    public ResponseEntity<?> getMessageHistory(@RequestParam String userId, @RequestParam Integer chatId) {
        if (userId == null || chatId == null) {
            logger.error("Некорректные данные: один или несколько параметров null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Некорректные данные");
        }

        // Проверяем, является ли пользователь владельцем чата
        boolean chatIsPresent = chatsService.checkChat(userId, chatId);
        if (!chatIsPresent) {
            logger.error("Чат с указанным владельцем не найден.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Чат не найден");
        }

        // Получаем историю сообщений
        List<Messages> messageHistory = messageService.findMessageHistory(chatId);
        if (messageHistory.isEmpty()) {
            logger.info("В чате {} сообщений не найдено", chatId);
            return ResponseEntity.ok(0); // Возвращаем 0, если сообщений нет
        }

        logger.info("Возвращаем {} сообщений для чата {}", messageHistory.size(), chatId);
        return ResponseEntity.ok(messageHistory);
    }


}