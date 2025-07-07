package com.springbootweb.spring.boot.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {

    private  Long id;
    private String accessToken;
    private String refreshToken;
}


