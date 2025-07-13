//package com.springbootweb.spring.boot.web.entities;
//
//import com.springbootweb.spring.boot.web.enums.Role;
//import jakarta.persistence.*;
//import lombok.*;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Getter
//@Setter
//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
//public class UserEntity implements UserDetails {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(unique = true)
//   private  String username;
//   private  String password;
//   private  String name;
//
//   @ElementCollection(fetch = FetchType.EAGER)
//   @Enumerated(EnumType.STRING)
//   private Set<Role> roles;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of( new SimpleGrantedAuthority("ROLE_" + this.roles))
//                .stream().collect(Collectors.toSet());
//    }
//
//    @Override
//    public String getPassword() {
//        return this.password;
//    }
//
//    @Override
//    public String getUsername() {
//        return this.username;
//    }
//}
