package com.springbootweb.spring.boot.web.dto;

import com.springbootweb.spring.boot.web.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDTO {

    private  String username;
    private  String name;
    private  String password;
    private Set<Role> roles;
}
