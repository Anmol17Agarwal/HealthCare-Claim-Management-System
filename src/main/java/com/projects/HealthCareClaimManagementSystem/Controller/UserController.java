package com.projects.HealthCareClaimManagementSystem.Controller;

import com.projects.HealthCareClaimManagementSystem.Entitiy.UserEntity;
import com.projects.HealthCareClaimManagementSystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public UserEntity register(@RequestBody UserEntity user){
        userService.register(user);
        return user;
    }

    @PostMapping("/login")
    public String login(@RequestBody UserEntity users){
        return userService.verify(users);

    }
}
