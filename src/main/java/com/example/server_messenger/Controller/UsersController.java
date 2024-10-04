package com.example.server_messenger.Controller;

import com.example.server_messenger.Model.UsersProfile;
import com.example.server_messenger.Service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private UsersService usersService;

    @PostMapping("/createUser")
    public ResponseEntity<UsersProfile> createUser(@RequestBody UsersProfile newUser) {
        logger.info("Вызван метод контроллера по добавлению пользователя с данными: {}", newUser);

        UsersProfile createdUser = usersService.createUser(newUser);

        logger.info("Создан новый пользователь: {}", createdUser);
        return ResponseEntity.ok(createdUser);  // Возвращаем созданного пользователя с кодом 200 OK
    }
}
