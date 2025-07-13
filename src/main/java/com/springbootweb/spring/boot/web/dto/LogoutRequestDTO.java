package com.springbootweb.spring.boot.web.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LogoutRequestDTO {

    private  String refreshToken;
}
