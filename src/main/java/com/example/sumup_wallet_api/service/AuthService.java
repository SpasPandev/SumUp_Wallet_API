package com.example.sumup_wallet_api.service;

import com.example.sumup_wallet_api.model.dto.RegisterRequestDto;
import com.example.sumup_wallet_api.model.entity.User;
import com.example.sumup_wallet_api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> registerUser(RegisterRequestDto registerRequestDto) {

        if (userRepository.existsByUsername(registerRequestDto.getUsername())) {

            return new ResponseEntity<>("Username already exists. Please choose a different one.",
                    HttpStatus.CONFLICT);
        }

        User user = User.builder()
                .username(registerRequestDto.getUsername())
                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                .firstName(registerRequestDto.getFirstName())
                .lastName(registerRequestDto.getLastName())
                .wallets(new HashSet<>()).build();

        userRepository.save(user);

        return new ResponseEntity<>("Register Successful", HttpStatus.CREATED);
    }
}
