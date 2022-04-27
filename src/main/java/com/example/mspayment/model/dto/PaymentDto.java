package com.example.mspayment.model.dto;

import com.example.mspayment.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    private String cardNumber;

    private BigDecimal amount;

    private PaymentStatus status;

    private LocalDateTime createdAt;


}
