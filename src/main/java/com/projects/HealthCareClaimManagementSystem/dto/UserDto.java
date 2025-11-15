package com.projects.HealthCareClaimManagementSystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    @JsonProperty("user_id")
    private long userId;

    @JsonProperty("username")
    @NotBlank(message = "Username is required")
    private String username;

//    @JsonProperty("password")
//    @NotBlank(message = "Password is required")
//    @Size(min = 8, message = "Password should be at least 8 characters long")
//    private String password;

    @JsonProperty("full_name")
    @NotBlank(message = "Full name is required")
    private String fullName;

    @JsonProperty("user_email")
    @NotBlank(message = "User email is required")
    private String userEmail;

    @JsonProperty("roles")
    private Set<RoleDto> roles;
}
