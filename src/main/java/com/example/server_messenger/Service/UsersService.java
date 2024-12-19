package com.example.server_messenger.Service;

import com.example.server_messenger.Controller.UsersController;
import com.example.server_messenger.Model.DTO.UserInfo;
import com.example.server_messenger.Model.UsersProfile;
import com.example.server_messenger.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    // Метод проверки номера телефона на наличие в базе
    public boolean getPhoneNumber(String phoneNumber)
    {
        // Ищем пользователя по номеру телефона
        Optional<UsersProfile> user = userRepository.findByPhoneNumber(phoneNumber);

        // Возвращаем true, если номер телефона есть в бд, иначе false
        return user.isPresent();
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

    // Метод обновления данных профиля пользователя
    public UsersProfile updateUserProfile(String userId, UsersProfile updatedUserProfile) {
        Optional<UsersProfile> existingUserOptional = userRepository.findById(userId);
        if (existingUserOptional.isPresent()) {
            UsersProfile existingUser = existingUserOptional.get();

            // Обновляем поля существующего пользователя
            existingUser.setImage_url(updatedUserProfile.getImage_url());
            existingUser.setLogin(updatedUserProfile.getLogin());
            existingUser.setPhoneNumber(updatedUserProfile.getPhoneNumber());

            // Сохраняем обновленного пользователя
            return userRepository.save(existingUser);
        } else {
            logger.warn("Пользователь с ID {} не найден для обновления", userId);
            return null;
        }
    }

    // Метод получения информации о пользователях по их ID
    public Map<String, UserInfo> getUsersInfoByIds(List<String> userIds) {
        Map<String, UserInfo> userInfoMap = new HashMap<>();

        try {
            // Получаем всех пользователей с указанными ID
            List<UsersProfile> users = userRepository.findAllById(userIds);

            // Преобразуем список пользователей в карту
            for (UsersProfile user : users) {
                UserInfo userInfo = new UserInfo();
                userInfo.setUser_id(user.getUserId());
                userInfo.setLogin(user.getLogin());
                userInfo.setImageUrl(user.getImage_url());

                userInfoMap.put(user.getUserId(), userInfo);
            }

        } catch (Exception ex) {
            logger.error("Ошибка при получении информации о пользователях: {}", ex.getMessage());
        }

        return userInfoMap;
    }

}
