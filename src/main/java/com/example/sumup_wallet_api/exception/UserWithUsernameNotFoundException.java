package com.example.sumup_wallet_api.exception;

public class UserWithUsernameNotFoundException extends RuntimeException {

    public UserWithUsernameNotFoundException(String username) {

        super("User with username: " + username + " not found");
    }
}
