package com.example.sumup_wallet_api.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String username;

    private String firstName;

    private String lastName;

}
