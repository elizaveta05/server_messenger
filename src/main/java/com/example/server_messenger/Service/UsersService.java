package com.example.server_messenger.Service;

import com.example.server_messenger.Controller.UsersController;
import com.example.server_messenger.Model.UsersProfile;
import com.example.server_messenger.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersService {

    private static final Logger logger = LoggerFactory.getLogger(UsersService.class);

    @Autowired
    private UserRepository userRepository;

    //Метод добавления пользователя в БД
    public UsersProfile createUser(UsersProfile user) {
        return userRepository.save(user);  // Сохраняем пользователя в базе данных
    }

    // Метод для получения всех логинов пользователей
    public List<String> getUsersLogins() {
        return userRepository.findAll()
                .stream()
                .map(UsersProfile::getLogin)
                .collect(Collectors.toList());
    }

    // Метод получения данных профиля конкретного пользователя
    public UsersProfile getProfileUser(String userId) {
        return userRepository.findById(userId).orElse(null);
    }

    //Метод удаления пользователя из БД
    public boolean deleteProfileUserFromDB(String userId){
        try {
            userRepository.deleteById(userId);
            return true; // Успешно удалено
        } catch (Exception ex) {
            logger.error("Ошибка при удалении профиля пользователя - {}: {}", userId, ex.getMessage());
            return false; // Ошибка удаление
        }
    }
}