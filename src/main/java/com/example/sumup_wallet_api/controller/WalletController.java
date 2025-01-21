package com.example.sumup_wallet_api.controller;

import com.example.sumup_wallet_api.model.dto.CreateWalletDto;
import com.example.sumup_wallet_api.model.dto.TransactionDto;
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

    @GetMapping("/{id}/balance")
    public ResponseEntity<?> viewBalance(@PathVariable Long id) {

        return walletService.viewBalance(id);
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<?> deposit(@PathVariable Long id, @RequestBody TransactionDto transactionDto) {

        return walletService.deposit(id, transactionDto);
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable Long id, @RequestBody TransactionDto transactionDto) {

        return walletService.withdraw(id, transactionDto);
    }

}
