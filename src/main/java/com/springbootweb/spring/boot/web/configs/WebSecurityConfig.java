package com.springbootweb.spring.boot.web.configs;

import com.springbootweb.spring.boot.web.filters.JwtAuthFilter;
import com.springbootweb.spring.boot.web.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private  final JwtAuthFilter jwtAuthFilter;
    private  final UserService userService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable()) // ✅ csrf disabled
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("employees/**" , "/departments/**", "/auth/**", "/signup/**","/login/**" , "/refresh").permitAll()
                    //    .requestMatchers("/employees/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .userDetailsService(userService)
                       .addFilterBefore(jwtAuthFilter , UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2configure -> oauth2configure
                        .failureUrl("/login?error=true"));


        return httpSecurity.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
