package com.springbootweb.spring.boot.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupResponseDTO {
    private Long employeeId;
    private String username;
    private String name;
    private String message;
}

