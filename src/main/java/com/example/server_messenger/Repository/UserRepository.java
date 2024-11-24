package com.example.server_messenger.Repository;

import com.example.server_messenger.Model.UsersProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UsersProfile, String> {

    // Метод для поиска пользователя по номеру телефона
    Optional<UsersProfile> findByPhoneNumber(String phoneNumber);
    List<UsersProfile> findByLoginContainingIgnoreCase(String login);
}
