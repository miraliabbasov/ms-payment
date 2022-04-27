package com.example.mspayment.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String name;

    private String surname;

    private String email;

    private BigDecimal balance;

    private String apiKey;

}
