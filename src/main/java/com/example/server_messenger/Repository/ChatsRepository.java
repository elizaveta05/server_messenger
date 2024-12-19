package com.example.server_messenger.Repository;

import com.example.server_messenger.Model.Chats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatsRepository extends JpaRepository<Chats, Integer> {

    @Query("SELECT c FROM Chats c WHERE (c.chat_user_owner = :user1 AND c.other_user = :user2) " +
            "OR (c.chat_user_owner = :user2 AND c.other_user = :user1)")
    List<Chats> findChatsBetweenUsers(@Param("user1") String user1, @Param("user2") String user2);

    @Query("SELECT c FROM Chats c WHERE c.chat_user_owner = :chatUserOwner")
    List<Chats> findByChatUserOwner(@Param("chatUserOwner") String chatUserOwner);

    @Query("SELECT COUNT(c) > 0 FROM Chats c WHERE c.chat_user_owner = :chatUserOwner AND c.chats_id = :chatId")
    boolean existsByChatUserOwnerAndChatId(@Param("chatUserOwner") String chatUserOwner, @Param("chatId") Integer chatId);

    @Query("SELECT c FROM Chats c WHERE c.chat_user_owner = :chatUserOwner AND c.other_user = :otherUser")
    Chats findChat(@Param("chatUserOwner") String chatUserOwner, @Param("otherUser") String otherUser);

}
