package com.example.sumup_wallet_api.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Wallet extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
