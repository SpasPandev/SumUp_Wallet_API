package com.example.sumup_wallet_api.model.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletDto {

    private String name;
    private BigDecimal balance;
    private UserDto userDto;
}
