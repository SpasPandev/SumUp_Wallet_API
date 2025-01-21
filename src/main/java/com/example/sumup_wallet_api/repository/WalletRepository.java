package com.example.sumup_wallet_api.repository;

import com.example.sumup_wallet_api.model.entity.User;
import com.example.sumup_wallet_api.model.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    boolean existsByNameAndUser(String walletName, User user);

    Set<Wallet> findAllByUser(User user);
}
