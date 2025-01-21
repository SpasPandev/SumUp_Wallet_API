package com.example.sumup_wallet_api.exception;

public class WalletNotFoundException extends RuntimeException {

    public WalletNotFoundException() {

        super("Wallet not found");

    }
}
