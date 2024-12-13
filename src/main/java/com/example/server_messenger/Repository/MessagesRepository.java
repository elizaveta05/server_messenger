package com.example.server_messenger.Repository;

import com.example.server_messenger.Model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessagesRepository extends JpaRepository<Messages, Integer> {
    // Метод для поиска сообщений по chat_id
    List<Messages> findByChatId(Integer chatId);
}
