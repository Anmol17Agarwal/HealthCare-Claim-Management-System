package com.projects.HealthCareClaimManagementSystem.Mapper;

import com.projects.HealthCareClaimManagementSystem.Dto.RoleDto;
import com.projects.HealthCareClaimManagementSystem.Dto.UserDto;
import com.projects.HealthCareClaimManagementSystem.Entitiy.RoleEntity;
import com.projects.HealthCareClaimManagementSystem.Entitiy.UserEntity;

import java.util.stream.Collectors;

public class UserMapper {

    public static UserEntity toEntity(UserDto dto) {
        return UserEntity.builder()
                .username(dto.getUsername())
                .fullName(dto.getFullName())
                .userEmail(dto.getUserEmail())
                .roles(dto.getRoles().stream()
                        .map(role -> RoleEntity.builder().name(role.getName()).build())
                        .collect(Collectors.toSet()))
                .build();
    }

    public static UserDto toDTO(UserEntity entity) {
        return UserDto.builder()
                .userId(entity.getUserId())
                .username(entity.getUsername())
                .fullName(entity.getFullName())
                .userEmail(entity.getUserEmail())
                .roles(entity.getRoles().stream()
                        .map(role -> RoleDto.builder().name(role.getName()).build())
                        .collect(Collectors.toSet()))
                .build();
    }
}
