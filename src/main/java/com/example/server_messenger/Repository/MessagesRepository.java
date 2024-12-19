package com.example.server_messenger.Repository;

import com.example.server_messenger.Model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MessagesRepository extends JpaRepository<Messages, Integer> {

    @Query(value = "SELECT * FROM messages WHERE chat_id = :chatId ORDER BY time_stamp DESC, message_id DESC LIMIT 1", nativeQuery = true)
    Messages findLastMessageInChat(@Param("chatId") Integer chatId);

    @Query(value = "SELECT COUNT(m) FROM messages m WHERE m.chat_id = :chatId", nativeQuery = true)
    int countMessagesByChatId(@Param("chatId") Integer chatId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM messages WHERE chat_id = :chatId", nativeQuery = true)
    void deleteByChatId(@Param("chatId") Integer chatId);

    @Query(value = "SELECT * FROM messages WHERE chat_id = :chatId ORDER BY time_stamp ASC", nativeQuery = true)
    List<Messages> findMessagesByChatId(@Param("chatId") Integer chatId);

}
