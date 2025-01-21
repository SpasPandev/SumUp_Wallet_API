package com.example.sumup_wallet_api.service;

import com.example.sumup_wallet_api.exception.UserWithUsernameNotFoundException;
import com.example.sumup_wallet_api.model.entity.User;
import com.example.sumup_wallet_api.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = findUserByUsername(username);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    public User findUserByUsername(String username) {

        return userRepository.findByUsername(username).orElseThrow(()
                -> new UserWithUsernameNotFoundException(username));
    }
}
