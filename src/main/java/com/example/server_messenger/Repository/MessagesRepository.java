package com.example.server_messenger.Repository;

import com.example.server_messenger.Model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessagesRepository extends JpaRepository<Messages, Integer> {
    // Метод для получения последнего сообщения в чате
    @Query("SELECT m FROM Messages m WHERE m.chat_id = :chatId ORDER BY m.time_stamp DESC")
    Messages findTopByChatIdOrderByTimeStampDesc(@Param("chatId") Integer chatId);


}
