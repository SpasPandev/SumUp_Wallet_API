package com.example.sumup_wallet_api.service;

import com.example.sumup_wallet_api.exception.WalletNotFoundException;
import com.example.sumup_wallet_api.model.dto.CreateWalletDto;
import com.example.sumup_wallet_api.model.dto.TransactionDto;
import com.example.sumup_wallet_api.model.dto.WalletDto;
import com.example.sumup_wallet_api.model.entity.User;
import com.example.sumup_wallet_api.model.entity.Wallet;
import com.example.sumup_wallet_api.repository.WalletRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public WalletService(WalletRepository walletRepository, UserService userService, ModelMapper modelMapper) {
        this.walletRepository = walletRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<?> createWallet(CreateWalletDto createWalletDto) {

        String currentUserUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User currentUser = userService.findUserByUsername(currentUserUsername);

        boolean walletExists = walletRepository.existsByNameAndUser(createWalletDto.getName(), currentUser);

        if (walletExists) {

            return new ResponseEntity<>(
                    Map.of("message", "Wallet with this name already exists"),
                    HttpStatus.BAD_REQUEST);
        }

        Wallet wallet = Wallet.builder()
                .balance(new BigDecimal(0))
                .name(createWalletDto.getName())
                .user(currentUser).build();

        walletRepository.save(wallet);

        WalletDto walletDto = modelMapper.map(wallet, WalletDto.class);

        Map<String, Object> response = Map.of(
                "message", "Wallet created successfully",
                "wallet", walletDto
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<?> getAllWallets() {

        User currentUser = findCurrentUser();

        List<WalletDto> wallets = walletRepository.findAllByUser(currentUser)
                .stream()
                .map(wallet -> modelMapper.map(wallet, WalletDto.class))
                .toList();

        if (wallets.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "No wallets found for the user"));
        }

        return ResponseEntity.ok(wallets);
    }

    private User findCurrentUser() {

        String currentUserUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        return userService.findUserByUsername(currentUserUsername);
    }

    public ResponseEntity<?> getWallet(Long id) {

        User currentUser = findCurrentUser();

        Wallet wallet = walletRepository.findById(id).orElseThrow(WalletNotFoundException::new);

        if (!wallet.getUser().equals(currentUser)) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "You do not have permission to access this wallet."));
        }

        return ResponseEntity.ok(modelMapper.map(wallet, WalletDto.class));
    }

    public ResponseEntity<?> viewBalance(Long id) {

        User currentUser = findCurrentUser();

        Wallet wallet = walletRepository.findById(id).orElseThrow(WalletNotFoundException::new);

        if (!wallet.getUser().equals(currentUser)) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "You do not have permission to access this wallet."));
        }

        return ResponseEntity.ok(Map.of(
                "walletId", wallet.getId(),
                "balance", wallet.getBalance()));
    }

    @Transactional
    public ResponseEntity<?> deposit(Long walletId, TransactionDto transactionDto) {

        if (transactionDto.getAmount() == null || transactionDto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Deposit amount must be greater than zero."));
        }

        User currentUser = findCurrentUser();

        Wallet wallet = walletRepository.findById(walletId).orElseThrow(WalletNotFoundException::new);

        if (!wallet.getUser().equals(currentUser)) {

            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("message", "You do not have permission to access this wallet."));
        }

        wallet.setBalance(wallet.getBalance().add(transactionDto.getAmount()));

        walletRepository.save(wallet);

        return ResponseEntity.ok(Map.of(
                "walletId", walletId,
                "newBalance", wallet.getBalance(),
                "message", "Deposit successful"
        ));
    }
}
