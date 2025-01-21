package com.example.sumup_wallet_api.service;

import com.example.sumup_wallet_api.exception.UserWithUsernameNotFoundException;
import com.example.sumup_wallet_api.model.dto.AuthRequestDto;
import com.example.sumup_wallet_api.model.dto.AuthResponseDto;
import com.example.sumup_wallet_api.model.dto.RegisterRequestDto;
import com.example.sumup_wallet_api.model.entity.User;
import com.example.sumup_wallet_api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
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

    public ResponseEntity<?> loginUser(AuthRequestDto authRequestDto) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(),
                            authRequestDto.getPassword()));
        }
        catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        User user = null;
        try {
            user = userRepository.findByUsername(authRequestDto.getUsername()).orElseThrow(()
                    -> new UserWithUsernameNotFoundException(authRequestDto.getUsername()));
        }
        catch (UserWithUsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

        String jwt = jwtService.generateToken(user.getUsername());

        return ResponseEntity.ok(new AuthResponseDto(jwt));
    }
}
