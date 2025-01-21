package com.example.sumup_wallet_api.controller;

import com.example.sumup_wallet_api.model.dto.CreateWalletDto;
import com.example.sumup_wallet_api.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping
    public ResponseEntity<?> createWallet(@Valid @RequestBody CreateWalletDto createWalletDto) {

        return walletService.createWallet(createWalletDto);
    }

    @GetMapping
    public ResponseEntity<?> getAllWallets() {

        return walletService.getAllWallets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWallet(@PathVariable Long id) {

        return walletService.getWallet(id);
    }

}
