package com.example.sumup_wallet_api.controller;

import com.example.sumup_wallet_api.model.dto.RegisterRequestDto;
import com.example.sumup_wallet_api.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDto registerRequestDto) {

        return authService.registerUser(registerRequestDto);

//        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
//        Wallet wallet = new Wallet();
//        wallet.setBalance(0.0);
//        wallet.setUser(user);
//        user.setWallet(wallet);
//        userRepository.save(user);

//        return ResponseEntity.ok("User registered successfully");
    }
}
