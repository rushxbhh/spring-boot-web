//package com.springbootweb.spring.boot.web.services;
//
//import com.springbootweb.spring.boot.web.dto.SignUpDTO;
//import com.springbootweb.spring.boot.web.dto.UserDTO;
//import com.springbootweb.spring.boot.web.entities.UserEntity;
//import com.springbootweb.spring.boot.web.repositories.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class UserService implements UserDetailsService {
//
//    private  final UserRepository userRepository;
//    private  final ModelMapper modelMapper;
//    private final PasswordEncoder passwordEncoder;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findByUsername(username)
//                .orElseThrow(() -> new BadCredentialsException("no such user found"));
//    }
//
//    public UserEntity getUserById(Long userId) {
//        return userRepository.findById(userId)
//                .orElseThrow(() -> new BadCredentialsException("no such user found"));
//    }

//    public UserDTO signup(SignUpDTO signUpDTO){
//        Optional<UserEntity> user = userRepository.findByUsername(signUpDTO.getUsername());
//        if(user.isPresent()) {
//            throw  new BadCredentialsException(" user with this username already exixts");
//        }
//        UserEntity toBeCreatedUser = modelMapper.map(signUpDTO , UserEntity.class);
//        toBeCreatedUser.setPassword(passwordEncoder.encode(toBeCreatedUser.getPassword()));
//        UserEntity savedUser = userRepository.save(toBeCreatedUser);
//        return modelMapper.map(savedUser , UserDTO.class);
//    }
//}
