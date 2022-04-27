package com.example.mspayment.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatPaymentDto {

    private String cardNumber;

    private BigDecimal amount;
}
