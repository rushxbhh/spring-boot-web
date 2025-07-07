package com.springbootweb.spring.boot.web.advices;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponse<?>> HandleResourceNotFound(NoSuchElementException exception) {
        ApiError apiError = ApiError.builder().message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .timestamp(LocalDateTime.now()).build();
        return buildresponseerror(apiError);
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> HandleInternalServerError(Exception exception) {
        ApiError apiError = ApiError.builder().message(exception.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(LocalDateTime.now()).build();
        return  buildresponseerror(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> HandleInputValidationError(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult()
                .getAllErrors()
                .stream().
                map(error -> error.getDefaultMessage())
                .collect(Collectors.toUnmodifiableList());

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("input validation errors")
                .suberrors(errors)
                .build();
        return  buildresponseerror(apiError);
    }
    private ResponseEntity<ApiResponse<?>> buildresponseerror(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponse<>(apiError) , apiError.getStatus());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<?>> handleAuthenticatonException(AuthenticationException e) {
        ApiError apiError = ApiError.builder().message(e.getMessage())
                .status(HttpStatus.UNAUTHORIZED)
                .timestamp(LocalDateTime.now()).build();
        return  buildresponseerror(apiError);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse<?>> handleJwtException(JwtException e) {
        ApiError apiError = ApiError.builder().message(e.getMessage())
                .status(HttpStatus.UNAUTHORIZED)
                .timestamp(LocalDateTime.now()).build();
        return  buildresponseerror(apiError);
    }


}
