package com.example.sumup_wallet_api.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class User extends BaseEntity {

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Please provide username")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "Please provide password")
    private String password;

    @Column(nullable = false)
    @NotBlank(message = "Please provide firstName")
    private String firstName;

    @Column(nullable = false)
    @NotBlank(message = "Please provide lastName")
    private String lastName;

    @OneToMany(mappedBy = "user")
    private Set<Wallet> wallets;

}
