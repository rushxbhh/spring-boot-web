package com.springbootweb.spring.boot.web.contollers;

import com.springbootweb.spring.boot.web.advices.ApiResponse;
import com.springbootweb.spring.boot.web.dto.*;
import com.springbootweb.spring.boot.web.services.AuthService;
import com.springbootweb.spring.boot.web.services.EmployeeService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import  jakarta.servlet.http.HttpServletResponse;
import  jakarta.servlet.http.Cookie;

import java.util.Arrays;


@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private  final EmployeeService employeeService;
    private  final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signup(@RequestBody SignUpDTO signUpDTO){
        SignupResponseDTO userDTO =employeeService.signup(signUpDTO);
        return ResponseEntity.ok(userDTO);
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response ) {
        LoginResponseDTO loginResponseDTO = authService.login(loginDTO);
        Cookie cookie = new Cookie("refreshToken" , loginResponseDTO.getRefreshToken());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        ApiResponse<LoginResponseDTO> apiResponse = new ApiResponse<>(loginResponseDTO);
        return  ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> refresh(HttpServletRequest request) {
        String refreshToken = Arrays.stream(request.getCookies()).
                filter(cookie -> "refreshToken" .equals(cookie.getName()))
                .findFirst().map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("refresh token found inside the cookie"));
        LoginResponseDTO loginResponseDTO =authService.refreshToken(refreshToken);
        ApiResponse<LoginResponseDTO> apiResponse = new ApiResponse<>(loginResponseDTO);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(@RequestBody LogoutRequestDTO logoutRequestDTO) {
        String refreshToken = logoutRequestDTO.getRefreshToken();
        String message = authService.logout(refreshToken);
        ApiResponse<String> apiResponse = new ApiResponse<>(message);
        return  ResponseEntity.ok(apiResponse);
    }

}
