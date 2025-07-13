package com.springbootweb.spring.boot.web.services;

import com.springbootweb.spring.boot.web.dto.LoginDTO;
import com.springbootweb.spring.boot.web.dto.LoginResponseDTO;
import com.springbootweb.spring.boot.web.entities.EmployeeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmployeeService EmployeeService;
    private final SessionService sessionService;

    public LoginResponseDTO login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsername(),
                        loginDTO.getPassword()
                )
        );

        EmployeeEntity user = (EmployeeEntity) authentication.getPrincipal();

        String accesstoken = jwtService.generateAccessToken(user);
        String refreshtoken = jwtService.generateRefreshToken(user);
        sessionService.generateNewSession(user ,refreshtoken);

        return new LoginResponseDTO(user.getEmployeeid(), accesstoken ,refreshtoken);
    }


    public LoginResponseDTO refreshToken(String refreshToken) {

        Long userId = jwtService.getUserIdfromToken(refreshToken);
        sessionService.validateSession(refreshToken);
        EmployeeEntity user = EmployeeService.getUserById(userId);

        String accessToken = jwtService.generateAccessToken(user);
        return new LoginResponseDTO(user.getEmployeeid() , accessToken ,refreshToken);
    }

    public String logout(String refreshToken) {
        try {
            sessionService.logout(refreshToken);
            return " user logged out successfully";
        }
        catch (SessionAuthenticationException e)
        {
            return  "user already logged out";
        }
    }
}
