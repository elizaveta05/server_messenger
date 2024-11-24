package com.example.server_messenger.Controller;

import com.example.server_messenger.Model.UsersProfile;
import com.example.server_messenger.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search")
public class SearchUsersController {
    private static final Logger logger = LoggerFactory.getLogger(SearchUsersController.class);

    @Autowired
    private UserRepository userRepository; // Репозиторий для доступа к базе данных пользователей

    //Метод, который принимает от пользователя его списки контактов и ищет существующуют ли такие пользователи у нас
    @PostMapping("/contacts")
    public List<UsersProfile> searchContacts(@RequestBody List<String> contacts) {
        //Возвращаем только те контакты, которые имеют профиль в нашем приложении
        return contacts.stream()
                .map(userRepository::findByPhoneNumber) // Ищем пользователя по номеру телефона
                .filter(Optional::isPresent)           // Фильтруем тех, кто найден
                .map(Optional::get)                    // Извлекаем объект из Optional
                .collect(Collectors.toList());
    }

    @GetMapping("/by-login")
    public List<UsersProfile> searchByLogin(@RequestParam String query) {
        logger.info("Поиск пользователей по логину: {}", query);
        return userRepository.findByLoginContainingIgnoreCase(query);
    }
}
