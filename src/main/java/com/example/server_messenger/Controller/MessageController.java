package com.example.server_messenger.Controller;

import com.example.server_messenger.Model.Messages;
import com.example.server_messenger.Service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    // Endpoint получения сообщений чата
    @GetMapping("/getMessageList")
    public ResponseEntity<List<Messages>> getMessageList(@RequestParam Integer chat_id) {
        logger.info("Запрос на получение сообщений в чате: {}", chat_id);
        List<Messages> messages = messageService.getMessagesByChatId(chat_id);
        return messages != null && !messages.isEmpty() ? ResponseEntity.ok(messages) : ResponseEntity.notFound().build();
    }

    // Endpoint для сохранения сообщения
    @PostMapping("/saveMessage")
    public ResponseEntity<Messages> saveMessage(@RequestBody Messages message) {
        Messages savedMessage = messageService.saveMessage(message);
        return ResponseEntity.ok(savedMessage);
    }

}