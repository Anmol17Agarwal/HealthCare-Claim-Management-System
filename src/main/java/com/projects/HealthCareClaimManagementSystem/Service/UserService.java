package com.projects.HealthCareClaimManagementSystem.Service;

import com.projects.HealthCareClaimManagementSystem.Entitiy.RoleEntity;
import com.projects.HealthCareClaimManagementSystem.Entitiy.UserEntity;
import com.projects.HealthCareClaimManagementSystem.Entitiy.UserRoles;
import com.projects.HealthCareClaimManagementSystem.Repository.RoleRepository;
import com.projects.HealthCareClaimManagementSystem.Repository.UserRepository;
import com.projects.HealthCareClaimManagementSystem.Utility.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AuthenticationManager authManager;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    public UserEntity register(UserEntity user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists!");
        }
        if (userRepository.existsByUserEmail(user.getUserEmail())) {
            throw new RuntimeException("Email already exists!");
        }
        Set<RoleEntity> resolvedRoles;

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            RoleEntity defaultRole = roleRepository.findByName(UserRoles.ROLE_CODER.name())
                    .orElseThrow(() -> new RuntimeException("Default role not found!"));
            resolvedRoles = Set.of(defaultRole);
        } else {
            resolvedRoles = user.getRoles().stream()
                    .map(role -> roleRepository.findByName(role.getName())
                            .orElseThrow(() -> new RuntimeException("Invalid role: " + role.getName())))
                    .collect(Collectors.toSet());
        }

        user.setRoles(resolvedRoles);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRefreshToken(jwtTokenUtil.generateToken(user.getUsername()));
        return userRepository.save(user);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public String verify(UserEntity users) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            users.getUsername(),
                            users.getPassword()
                    )
            );

            if (authentication.isAuthenticated()) {
                return jwtTokenUtil.generateToken(users.getUsername());
            } else {
                return "Failure";
            }

        } catch (Exception e) {
            return "Failure";
        }
    }
}
