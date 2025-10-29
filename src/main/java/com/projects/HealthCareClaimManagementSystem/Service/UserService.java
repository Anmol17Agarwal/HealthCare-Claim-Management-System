package com.projects.HealthCareClaimManagementSystem.Service;

import com.projects.HealthCareClaimManagementSystem.Dto.AuthenticatedUserDto;
import com.projects.HealthCareClaimManagementSystem.Dto.LoginDto;
import com.projects.HealthCareClaimManagementSystem.Dto.RoleDto;
import com.projects.HealthCareClaimManagementSystem.Dto.UserDto;
import com.projects.HealthCareClaimManagementSystem.Entitiy.RoleEntity;
import com.projects.HealthCareClaimManagementSystem.Entitiy.UserEntity;
import com.projects.HealthCareClaimManagementSystem.Entitiy.UserRoles;
import com.projects.HealthCareClaimManagementSystem.Exception.CustomException;
import com.projects.HealthCareClaimManagementSystem.Exception.DuplicateResourceException;
import com.projects.HealthCareClaimManagementSystem.Exception.ResourceNotFoundException;
import com.projects.HealthCareClaimManagementSystem.Mapper.UserMapper;
import com.projects.HealthCareClaimManagementSystem.Repository.RoleRepository;
import com.projects.HealthCareClaimManagementSystem.Repository.UserRepository;
import com.projects.HealthCareClaimManagementSystem.Utility.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
                user.getUser_id(),
                user.getUserEmail(),
                user.getFullName(),
                roles,
                user.getRefreshToken(),
                user.getRefreshTokenExpiry()
        );
    }
}
