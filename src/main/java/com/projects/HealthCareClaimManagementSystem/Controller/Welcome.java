package com.projects.HealthCareClaimManagementSystem.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Welcome {
    @GetMapping("/")
    public String welcome() {
        return "hello world";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String userPage() {
        return "I am a user";
    }


    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminPage() {
        return "I am a admin";
    }
}
