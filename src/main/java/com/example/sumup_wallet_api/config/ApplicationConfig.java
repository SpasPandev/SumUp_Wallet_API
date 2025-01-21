package com.example.sumup_wallet_api.config;

import com.example.sumup_wallet_api.model.dto.UserDto;
import com.example.sumup_wallet_api.model.dto.WalletDto;
import com.example.sumup_wallet_api.model.entity.User;
import com.example.sumup_wallet_api.model.entity.Wallet;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper(){

        ModelMapper modelMapper = new ModelMapper();

        modelMapper
                .typeMap(Wallet.class, WalletDto.class)
                .addMappings(mapper ->
                        mapper.map(Wallet::getUser, WalletDto::setUserDto))
        ;

        return modelMapper;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }
}
