package com.projects.HealthCareClaimManagementSystem.Dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class AuthenticatedUserDto extends UserDto {
    private String refreshToken;
    private LocalDateTime refreshTokenExpiry;
    private String accessToken;

    public AuthenticatedUserDto(long user_id, String user_email, String full_name,
                                Set<RoleDto> roles, String refreshToken,
                                LocalDateTime refreshTokenExpiry) {
        super(user_id,user_email,full_name,user_email,roles);
        this.refreshToken = refreshToken;
        this.refreshTokenExpiry = refreshTokenExpiry;
    }
}
