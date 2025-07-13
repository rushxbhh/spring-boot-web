package com.springbootweb.spring.boot.web.configs;

import com.springbootweb.spring.boot.web.enums.Role;
import com.springbootweb.spring.boot.web.filters.JwtAuthFilter;
import com.springbootweb.spring.boot.web.handler.Oauth2SucessHandler;
import com.springbootweb.spring.boot.web.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private  final JwtAuthFilter jwtAuthFilter;
    private  final EmployeeService employeeService;
    private final Oauth2SucessHandler oauth2SucessHandler;
    private static final String[] publicRoutes = {"/departments/**", "/auth/**","/logout/**" ,
            "/signup/**","/login/**" };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(publicRoutes).permitAll()
                        .requestMatchers("/employees/**" , "/attendances/**"
                                ,"/salaries/**" , "/refresh").hasRole(Role.ADMIN.name())
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .userDetailsService(employeeService)
                       .addFilterBefore(jwtAuthFilter , UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2configure -> oauth2configure
                        .failureUrl("/login?error=true")
                .successHandler(oauth2SucessHandler)).formLogin(form -> form.disable())
                .logout(logout -> logout.disable());

        return httpSecurity.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
