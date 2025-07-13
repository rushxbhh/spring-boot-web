package com.springbootweb.spring.boot.web.filters;

import com.springbootweb.spring.boot.web.entities.EmployeeEntity;
import com.springbootweb.spring.boot.web.services.EmployeeService;
import com.springbootweb.spring.boot.web.services.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private  final EmployeeService employeeService;
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private  final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
   try {
       final String authHeader = request.getHeader("Authorization");
       if (authHeader == null || !authHeader.startsWith("bearer")) {
           filterChain.doFilter(request, response);
           return;
       }
       String token = authHeader.split("bearer")[1];
       Long userId = jwtService.getUserIdfromToken(token);

       if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
           EmployeeEntity user = employeeService.getUserById(userId);
           UsernamePasswordAuthenticationToken authenticationToken =
                   new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
           authenticationToken.setDetails(
                   new WebAuthenticationDetailsSource().buildDetails(request));

           SecurityContextHolder.getContext().setAuthentication(authenticationToken);
       }
       filterChain.doFilter(request, response);
   } catch (Exception e) {
       handlerExceptionResolver.resolveException(request, response ,null , e);
   }
    }

}
