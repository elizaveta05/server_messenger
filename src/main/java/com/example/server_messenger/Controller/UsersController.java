package com.example.server_messenger.Controller;

import com.example.server_messenger.Model.UsersModel;

@Controller
public class UsersController {

    @GetMapping("/registration/{phoneNumber}")
    public UsersModel getUserById(@PathVariable String phoneNumber) {
      
    }
}
