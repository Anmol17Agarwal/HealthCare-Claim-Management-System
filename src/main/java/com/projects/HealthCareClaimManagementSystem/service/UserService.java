package com.projects.HealthCareClaimManagementSystem.service;

import com.projects.HealthCareClaimManagementSystem.dto.AuthenticatedUserDto;
import com.projects.HealthCareClaimManagementSystem.dto.LoginDto;
import com.projects.HealthCareClaimManagementSystem.dto.RoleDto;
import com.projects.HealthCareClaimManagementSystem.dto.UserDto;
import com.projects.HealthCareClaimManagementSystem.entitiy.RoleEntity;
import com.projects.HealthCareClaimManagementSystem.entitiy.UserEntity;
import com.projects.HealthCareClaimManagementSystem.entitiy.UserRoles;
import com.projects.HealthCareClaimManagementSystem.exception.CustomException;
import com.projects.HealthCareClaimManagementSystem.exception.DuplicateResourceException;
import com.projects.HealthCareClaimManagementSystem.exception.ResourceNotFoundException;
import com.projects.HealthCareClaimManagementSystem.mapper.UserMapper;
import com.projects.HealthCareClaimManagementSystem.repository.RoleRepository;
import com.projects.HealthCareClaimManagementSystem.repository.UserRepository;
import com.projects.HealthCareClaimManagementSystem.utility.JwtTokenUtil;
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
    public UserDto register(UserEntity user) {
        try {
            if (userRepository.existsByUsername(user.getUsername())) {
                throw new DuplicateResourceException("Username already exists!");
            }
            if (userRepository.existsByUserEmail(user.getUserEmail())) {
                throw new DuplicateResourceException("Email already exists!");
            }
            Set<RoleEntity> resolvedRoles;

            if (user.getRoles() == null || user.getRoles().isEmpty()) {
                RoleEntity defaultRole = roleRepository.findByName(UserRoles.ROLE_CODER.name())
                        .orElseThrow(() -> new ResourceNotFoundException("Default role not found!"));
                resolvedRoles = Set.of(defaultRole);
            } else {
                resolvedRoles = user.getRoles().stream()
                        .map(role -> roleRepository.findByName(role.getName())
                                .orElseThrow(() -> new ResourceNotFoundException("Invalid role: " + role.getName())))
                        .collect(Collectors.toSet());
            }

            user.setRoles(resolvedRoles);
            user.setPassword(encoder.encode(user.getPassword()));
            user.setRefreshToken(jwtTokenUtil.generateToken(user.getUsername()));
            return UserMapper.toDTO(userRepository.save(user));
        }catch (DuplicateResourceException | ResourceNotFoundException e){
            throw e;
        }catch (Exception e){
            throw new CustomException("Unexpected error while registering "+e.getMessage());
        }
    }

    public List<UserDto> getAllUsers() {
        try {
            List<UserEntity> user = userRepository.findAll();
            List<UserDto> userDtos = user.stream()
                    .map(UserMapper::toDTO)
                    .collect(Collectors.toList());
            return userDtos;
        } catch (Exception e) {
            throw new CustomException("Error retrieving users: " + e.getMessage());
        }
    }

    public LoginDto verify(UserEntity users) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            users.getUsername(),
                            users.getPassword()
                    )
            );
            if (authentication.isAuthenticated()) {
                UserEntity user = userRepository.findByUsername(users.getUsername())
                        .orElseThrow(() -> new ResourceNotFoundException("User not found."));
                String accessToken =  jwtTokenUtil.generateToken(users.getUsername());
                AuthenticatedUserDto userDto =convertToAuthenticatedUserDto(user);
                return new LoginDto(userDto, accessToken);
            } else {
                throw new CustomException("Invalid username or password.");
            }
        } catch (DuplicateResourceException | ResourceNotFoundException e) {
            throw e;
        }catch (Exception e){
            throw new CustomException("Unexpected error during login: " + e.getMessage());
        }
    }

    public AuthenticatedUserDto convertToAuthenticatedUserDto(UserEntity user) {
        Set<RoleDto> roles = user.getRoles().stream()
                .map(role->new RoleDto(role.getName()))
                .collect(Collectors.toSet());

        return new AuthenticatedUserDto(
                user.getUserId(),
                user.getUserEmail(),
                user.getFullName(),
                roles,
                user.getRefreshToken(),
                user.getRefreshTokenExpiry()
        );
    }
}
