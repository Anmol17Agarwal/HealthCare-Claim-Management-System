package com.projects.HealthCareClaimManagementSystem.Exception;

import com.projects.HealthCareClaimManagementSystem.Utility.CustomResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Custom-defined exceptions
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomResponse<String>> handleCustomException(CustomException ex) {
        log.error("Custom Exception: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CustomResponse.failure(ex.getMessage()));
    }

    // When an entity (e.g., claim or patient) is not found
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomResponse<String>> handleEntityNotFound(EntityNotFoundException ex) {
        log.error("Entity Not Found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(CustomResponse.failure("Resource not found: " + ex.getMessage()));
    }

    // For validation errors (e.g., missing or invalid fields)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomResponse<String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));
        log.error("Validation Error: {}", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CustomResponse.failure("Validation failed: " + errors));
    }

    // Catch-all for any unhandled exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomResponse<String>> handleGeneralException(Exception ex) {
        log.error("Unexpected Error: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(CustomResponse.failure("An unexpected error occurred: " + ex.getMessage()));
    }
}
