package com.example.server_messenger.Controller;

import com.example.server_messenger.Model.Chats;
import com.example.server_messenger.Service.ChatsService;
import com.example.server_messenger.Service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chats")
public class ChatsController {
    private static final Logger logger = LoggerFactory.getLogger(ChatsController.class);
    @Autowired
    private ChatsService chatsService;

    // Метод получения чатов пользователя
    @GetMapping("/getChatsByUser")
    public ResponseEntity<List<Chats>> getUsersChat(@RequestParam String userId) {
        logger.info("Вызван метод контроллера для получения чатов пользователя - {}", userId);

        List<Chats> userChats = chatsService.getChatsByUser(userId);

        if (userChats != null && !userChats.isEmpty()) {
            return ResponseEntity.ok(userChats);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Метод получения id чата
    @GetMapping("/getChatByUsers")
    public ResponseEntity<Integer> getChat(@RequestParam String user1_id, @RequestParam String user2_id) {
        logger.info("Запрос на получение чата для пользователей: {} и {}", user1_id, user2_id);

        Integer chat_id = chatsService.getChatByUsers(user1_id, user2_id).getChatId();

        if (chat_id <= 0) {
            return ResponseEntity.ok(chat_id);
        } else {
            return ResponseEntity.notFound().build();
        }
    }





}
