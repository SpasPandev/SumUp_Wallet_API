package com.example.sumup_wallet_api.service;

import com.example.sumup_wallet_api.exception.WalletNotFoundException;
import com.example.sumup_wallet_api.model.dto.CreateWalletDto;
import com.example.sumup_wallet_api.model.dto.WalletDto;
import com.example.sumup_wallet_api.model.entity.User;
import com.example.sumup_wallet_api.model.entity.Wallet;
import com.example.sumup_wallet_api.repository.WalletRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

            return new ResponseEntity<>("Wallet with this name already exists", HttpStatus.BAD_REQUEST);
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No wallets found for the user");
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
                    .body("You do not have permission to access this wallet.");
        }

        return ResponseEntity.ok(modelMapper.map(wallet, WalletDto.class));
    }
}
