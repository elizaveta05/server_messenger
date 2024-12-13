package com.example.server_messenger.Repository;

import com.example.server_messenger.Model.Chats;
import com.example.server_messenger.Model.UsersProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatsRepository extends JpaRepository<ChatsRepository, Integer> {
    // Метод для поиска чатов пользователя по userId
    List<Chats> findChatsByUserId(String userId);

    //Метод получения id чата по id пользователей
    Chats findChatByUser1IdAndUser2Id(String user1_id, String user2_id);

}
