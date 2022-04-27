package com.example.mspayment.service;

import com.example.mspayment.client.UserClient;
import com.example.mspayment.client.dto.UserDto;
import com.example.mspayment.dao.entity.CardsEntity;
import com.example.mspayment.dao.entity.PaymentEntity;
import com.example.mspayment.dao.entity.PaymentOfBalanceEntity;
import com.example.mspayment.dao.repository.BalanceRepository;
import com.example.mspayment.dao.repository.CardsRepository;
import com.example.mspayment.dao.repository.PaymentRepository;
import com.example.mspayment.model.dto.PaymentDto;
import com.example.mspayment.model.enums.Constants;
import com.example.mspayment.model.enums.PaymentStatus;
import com.example.mspayment.model.enums.Status;
import com.example.mspayment.model.exception.NotFoundException;
import com.example.mspayment.model.map.PaymentMap;

import static com.example.mspayment.model.enums.ExceptionConstants.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j

public class PaymentService {

    private final UserClient client;
    private final CardsRepository cardsRepository;
    private final PaymentRepository paymentRepository;
    private final BalanceRepository balanceRepository;

    public PaymentDto decreaseAmount(String cardNumber, BigDecimal amount) {

        var cardDetail = getCardDetails(cardNumber);

        var check = cardDetail.getBalance().compareTo(amount);

        if (cardDetail.getStatus().toString().equals(Status.ACTIVE.toString())) {
            if (check == 1 || check == 0) {
                cardDetail.setBalance(cardDetail.getBalance().subtract(amount));
            } else {

                var unpaymentEntity = creatPayment(cardNumber, amount, Constants.DECREASE, PaymentStatus.UNSUCCESS);
                paymentRepository.save(unpaymentEntity);
                log.error("BALANCE_IS_NOT_ENOUGH card number:  {}", cardNumber);
                throw new NotFoundException(String.format(BALANCED_NOT_ENOUGH_MESSAGE, cardNumber), BALANCED_NOT_ENOUGH_CODE);
            }

        } else {
            log.error("CARD_IS_BLOCKED:  {}", cardNumber);
            throw new NotFoundException(String.format(CARD_IS_BLOCKED_MESSAGE, cardNumber), CARD_IS_BLOCKED_CODE);
        }

        cardsRepository.save(cardDetail);

        var paymentEntity = creatPayment(cardNumber, amount, Constants.DECREASE, PaymentStatus.SUCCESS);

        paymentRepository.save(paymentEntity);

        return PaymentMap.INSTANCE.entityToDto(paymentEntity);
    }


    public BigDecimal decreaseAmountWithBalance(String apiKey, BigDecimal amount) {
        log.info("Start decrease with balance");
        var entity = createPaymentOfBalance(apiKey, amount);

        var clientDto = client.getUserByApiKey(apiKey);
        var check = clientDto.getBalance().compareTo(amount);
        if (check == 1 || check == 0) {
            entity.setStatus(PaymentStatus.SUCCESS);
            entity.setConstants(Constants.DECREASE);
            balanceRepository.save(entity);
            client.getUserAndBalanceByApiKey(apiKey,amount);
            clientDto.setBalance(clientDto.getBalance().subtract(amount));
        } else {
            entity.setStatus(PaymentStatus.UNSUCCESS);
            entity.setConstants(Constants.DECREASE);
            balanceRepository.save(entity);
            log.error("BALANCE_IS_NOT_ENOUGH card number:  {}", clientDto.getApiKey());
            throw new NotFoundException(String.format(BALANCED_NOT_ENOUGH_BALANCE_MESSAGE, clientDto.getApiKey()), BALANCED_NOT_ENOUGH_BALANCE_CODE);
        }

        return amount;
    }


    public UserDto increaseBalanceOfUser(String cardNumber, BigDecimal amount, String apiKey){

        var dto = decreaseAmount(cardNumber,amount);

        var clientUser =client.getUserByApiKey(apiKey);
        clientUser.setBalance(clientUser.getBalance().add(amount));

        var entity = createPaymentOfBalance(apiKey, amount);
        entity.setStatus(PaymentStatus.SUCCESS);
        entity.setConstants(Constants.INCREASE);
        balanceRepository.save(entity);

        return clientUser;
    }


    private CardsEntity getCardDetails(String cardNumber) {
        var cards = cardsRepository.findByCardNumber(cardNumber)
                .orElseThrow(
                        () -> new NotFoundException(String.format(CARD_NUMBER_NOT_FOUND_MESSAGE, cardNumber), CARD_NUMBER_NOT_FOUND_CODE)
                );

        return cards;
    }

    private PaymentEntity creatPayment(String cardNumber, BigDecimal amount, Constants constants, PaymentStatus status) {

        return PaymentEntity.builder()
                .cardNumber(cardNumber)
                .amount(amount)
                .constants(constants)
                .status(status)
                .build();
    }

    private PaymentOfBalanceEntity createPaymentOfBalance(String apiKey, BigDecimal amount) {
        return PaymentOfBalanceEntity.builder()
                .apiKey(apiKey)
                .amount(amount)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
