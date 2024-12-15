package com.example.server_messenger.Repository;

import com.example.server_messenger.Model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessagesRepository extends JpaRepository<Messages, Integer> {
    // Метод для получения последнего сообщения в чате
    Messages findTopByChatIdOrderByTimeCreatedDesc(Integer chatId);
}
