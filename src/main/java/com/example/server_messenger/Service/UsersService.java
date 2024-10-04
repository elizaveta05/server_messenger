package com.example.server_messenger.Service;

import com.example.server_messenger.Model.UsersProfile;
import com.example.server_messenger.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UserRepository userRepository;

    public UsersProfile createUser(UsersProfile user) {

        return userRepository.save(user);  // Сохраняем пользователя в базе данных
    }
}
