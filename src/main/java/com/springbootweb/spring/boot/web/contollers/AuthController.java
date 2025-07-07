package com.springbootweb.spring.boot.web.contollers;

import com.springbootweb.spring.boot.web.advices.ApiResponse;
import com.springbootweb.spring.boot.web.dto.LoginDTO;
import com.springbootweb.spring.boot.web.dto.LoginResponseDTO;
import com.springbootweb.spring.boot.web.dto.SignUpDTO;
import com.springbootweb.spring.boot.web.dto.UserDTO;
import com.springbootweb.spring.boot.web.services.AuthService;
import com.springbootweb.spring.boot.web.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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

    private  final UserService userService;
    private  final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody SignUpDTO signUpDTO){
        UserDTO userDTO = userService.signup(signUpDTO);
        return ResponseEntity.ok(userDTO);
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response ) {
        LoginResponseDTO loginResponseDTO = authService.login(loginDTO);
        Cookie cookie = new Cookie("refreshToken" , loginResponseDTO.getRefreshToken());
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        ApiResponse<LoginResponseDTO> apiResponse = new ApiResponse<>(loginResponseDTO);
        return ResponseEntity.ok(apiResponse);
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

}
