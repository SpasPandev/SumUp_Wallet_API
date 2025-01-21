package com.example.sumup_wallet_api.model.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRequestDto {

    private String username;
    private String password;

}
