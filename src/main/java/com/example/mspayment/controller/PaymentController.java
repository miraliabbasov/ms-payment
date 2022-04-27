package com.example.mspayment.controller;

import com.example.mspayment.client.dto.UserDto;
import com.example.mspayment.model.dto.CreatPaymentDto;
import com.example.mspayment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/payment")
public class PaymentController {

    private final PaymentService paymentService;


    @PostMapping("/card")
    public void decreasePayment(@RequestBody CreatPaymentDto dto){
     paymentService.decreaseAmount(dto.getCardNumber(),dto.getAmount());
    }


    @PostMapping("/balance")
    public BigDecimal decreaseWithBalance(@RequestHeader  String apiKey , @RequestParam BigDecimal amount){
        return paymentService.decreaseAmountWithBalance(apiKey,amount);
    }

    @PostMapping("/increase-balance")
    public UserDto increaseBalanceOfUser(@RequestParam String cardNumber,@RequestParam BigDecimal amount,@RequestParam String apiKey){
        return paymentService.increaseBalanceOfUser(cardNumber, amount, apiKey);
    }


}
