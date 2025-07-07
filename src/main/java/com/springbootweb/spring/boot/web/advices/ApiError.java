package com.springbootweb.spring.boot.web.advices;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ApiError {

    private String message;
    private HttpStatus status;
    private LocalDateTime timestamp;
    private List<String> suberrors;
}
