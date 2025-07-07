package com.springbootweb.spring.boot.web.services;

import com.springbootweb.spring.boot.web.dto.LoginDTO;
import com.springbootweb.spring.boot.web.dto.LoginResponseDTO;
import com.springbootweb.spring.boot.web.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public LoginResponseDTO login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()
                )
        );

        User user = (User) authentication.getPrincipal();

        String accesstoken = jwtService.generateAccessToken(user);
        String refreshtoken = jwtService.generateRefreshToken(user);

        return new LoginResponseDTO(user.getId(), accesstoken ,refreshtoken);
    }


    public LoginResponseDTO refreshToken(String refreshToken) {

        Long userId = jwtService.getUserIdfromToken(refreshToken);
        User user = userService.getUserById(userId);

        String accessToken = jwtService.generateAccessToken(user);
        return new LoginResponseDTO(user.getId() , accessToken ,refreshToken);
    }
}
