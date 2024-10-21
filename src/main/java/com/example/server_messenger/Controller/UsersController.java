package com.example.server_messenger.Controller;

import com.example.server_messenger.Model.UsersProfile;
import com.example.server_messenger.Service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UsersService usersService;

    @PostMapping(value = "/createUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createUser(
            @RequestPart("userId") String userId,
            @RequestPart("login") String login,
            @RequestPart("phoneNumber") String phoneNumber,
            @RequestPart(value = "image_url", required = false) String imageUrl) {

        logger.info("Вызван метод контроллера по добавлению пользователя с данными и изображением");

        // Проверка обязательных параметров
        if (userId == null || userId.isEmpty()) {
            return ResponseEntity.badRequest().body("Параметр 'userId' обязателен.");
        }
        if (login == null || login.isEmpty()) {
            return ResponseEntity.badRequest().body("Параметр 'login' обязателен.");
        }
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return ResponseEntity.badRequest().body("Параметр 'phoneNumber' обязателен.");
        }

        // Создаем профиль пользователя
        UsersProfile newUser = new UsersProfile();
        newUser.setUserId(userId);
        newUser.setLogin(login);
        newUser.setPhoneNumber(phoneNumber);

        // Установка URL изображения, если он существует
        if (imageUrl != null) {
            newUser.setImage_url(imageUrl);
        } else {
            newUser.setImage_url(null);
        }

        // Сохранение пользователя в базе данных
        UsersProfile createdUser = usersService.createUser(newUser);
        logger.info("Создан новый пользователь: {}", createdUser);
        return ResponseEntity.ok(createdUser);
    }

    // Метод для получения всех логинов уже существующих пользователей
    @GetMapping("/getUsersLogin")
    public ResponseEntity<Object> getUsersLogin() {
        logger.info("Вызван метод контроллера для получения логинов существующих пользователей.");

        List<String> userLogins = usersService.getUsersLogins();

        return ResponseEntity.ok(userLogins);  // Возвращаем список логинов с кодом 200 OK
    }

    // Метод получения данных профиля для конкретного пользователя
    @GetMapping("/getProfileUser/{userId}")
    public ResponseEntity<UsersProfile> getProfileUser(@PathVariable String userId) {
        logger.info("Вызван метод для получения данных профиля для пользователя - {}", userId);

        UsersProfile userProfile = usersService.getProfileUser(userId);

        if (userProfile != null) {
            return ResponseEntity.ok(userProfile);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Метод удаления пользователя из БД
    @DeleteMapping("/deleteProfileUser/{userId}")
    public ResponseEntity<String> deleteProfileUser(@PathVariable String userId) {
        logger.info("Вызван метод удаления профиля пользователя - {}", userId);

        boolean isDeleted = usersService.deleteProfileUserFromDB(userId);

        if (isDeleted) {
            return ResponseEntity.ok("Профиль пользователя успешно удален.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Профиль пользователя не найден.");
        }
    }

    // Метод обновления данных профиля пользователя
    @PutMapping("/updateUserProfile/{userId}")
    public ResponseEntity<UsersProfile> updateUserProfile(@PathVariable String userId, @RequestBody UsersProfile updatedUserProfile) {
        logger.info("Вызван метод обновления профиля пользователя - {}", userId);

        UsersProfile updatedUser = usersService.updateUserProfile(userId, updatedUserProfile);

        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
