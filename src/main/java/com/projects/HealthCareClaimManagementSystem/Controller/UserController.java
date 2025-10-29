package com.projects.HealthCareClaimManagementSystem.Controller;

import com.projects.HealthCareClaimManagementSystem.Dto.LoginDto;
import com.projects.HealthCareClaimManagementSystem.Dto.UserDto;
import com.projects.HealthCareClaimManagementSystem.Entitiy.UserEntity;
import com.projects.HealthCareClaimManagementSystem.Service.UserService;
import com.projects.HealthCareClaimManagementSystem.Utility.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<CustomResponse<UserDto>> register(@RequestBody UserEntity user){
            UserDto userDto = userService.register(user);
            return ResponseEntity.ok(CustomResponse.success("User Registered Successfully", userDto));
    }

    @GetMapping("/getAll")
    public ResponseEntity<CustomResponse<List<UserDto>>> getAllUsers(){
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(CustomResponse.success("Users fetched successfully", users));
    }
    @PostMapping("/login")
    public ResponseEntity<CustomResponse<LoginDto>> login(@RequestBody UserEntity users){
        LoginDto loginDto = userService.verify(users);
        return ResponseEntity.ok(CustomResponse.success("Login successful", loginDto));
    }
}
