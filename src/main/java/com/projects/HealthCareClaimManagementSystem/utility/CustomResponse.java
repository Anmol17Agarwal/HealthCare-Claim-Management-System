package com.projects.HealthCareClaimManagementSystem.utility;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public static <T> CustomResponse<T> success(String message, T data) {
        return new CustomResponse<>(true, message, data, LocalDateTime.now());
    }

    public static <T> CustomResponse<T> failure(String message) {
        return new CustomResponse<>(false, message, null, LocalDateTime.now());
    }
}

