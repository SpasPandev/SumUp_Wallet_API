package com.example.sumup_wallet_api.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class TransactionDto {

    private BigDecimal amount;
}
